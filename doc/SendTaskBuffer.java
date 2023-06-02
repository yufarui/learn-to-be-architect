package com.job51.ehire.backend.activity.service;

import com.job51.ehire.backend.activity.model.po.ActivityApplyPO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SendTaskBuffer {

    // 一次feign调用期望的个数
    private int capacity = 10;

    private int count;
    private final List<ActivityApplyPO> buffer = new ArrayList<>(capacity);

    private final ReentrantLock bufferLock = new ReentrantLock();

    // 可以存放元素的信号
    private final Condition canWrite = bufferLock.newCondition();

    private final Condition canSend = bufferLock.newCondition();

    public void add2(ActivityApplyPO task) {

        try {
            bufferLock.lock();
            buffer.add(task);

            if (buffer.size() <= capacity) {
                return;
            }

            log.info("发起远程调用, 发送容量{}", buffer.size());
            buffer.clear();

        } finally {
            bufferLock.unlock();
        }
    }

    @SneakyThrows
    public void addTask(ActivityApplyPO task) {

        try {
            bufferLock.lock();

            if (buffer.size() >= capacity) {
                canWrite.await();
            }

            buffer.add(task);

            if (buffer.size() >= capacity) {
                log.info("准备发送数据");
                canSend.signal();
            }

        } finally {
            bufferLock.unlock();
        }
    }

    @SneakyThrows
    public void feignInvoke() {
        try {
            bufferLock.lock();
            if (buffer.size() < capacity) {
                if (!canSend.await(5, TimeUnit.SECONDS)) {
                    log.info("等待超时,发送小批量数据, 发送容量{}", buffer.size());
                }
            }

            if (buffer.size() == 0) {
                log.debug("没有可发送的数据");
                return;
            }

            // 调用feign
            log.info("发起远程调用, 发送容量{}", buffer.size());
            buffer.clear();

            canWrite.signal();
        } finally {
            bufferLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SendTaskBuffer after = new SendTaskBuffer();
        Thread t0 = new Thread(() -> {

            for (int i = 0; i < 35; i++) {
                after.addTask(new ActivityApplyPO());
            }
        });

        Thread t1 = new Thread(() -> {

            for (int i = 0; i < 4; i++) {
                after.feignInvoke();
            }
        });

        t0.start();
        t1.start();

        t0.join();
        t1.join();
    }

}
