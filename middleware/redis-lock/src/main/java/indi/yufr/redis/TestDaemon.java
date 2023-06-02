package indi.yufr.redis;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestDaemon {

    private static final ScheduledExecutorService scheduledSinglePool = Executors.newScheduledThreadPool(1);


    public static void main(String[] args) {
        TestDaemon testDaemon = new TestDaemon();
        testDaemon.lock();
    }
    public void lock() {

        System.out.println("开始主线程");

        Thread thread = new Thread(new DaemonTask());
        thread.setDaemon(true);
        thread.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
    }

    static class DaemonTask implements Runnable {

        @Override
        public void run() {
            System.out.println("主线程没结束 " + System.currentTimeMillis());
            scheduledSinglePool.schedule(this, 4000, TimeUnit.MILLISECONDS);
        }
    }
}
