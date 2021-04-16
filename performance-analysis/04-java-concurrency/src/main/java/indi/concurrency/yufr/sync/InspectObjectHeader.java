package indi.concurrency.yufr.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @date: 2021/4/13 10:55
 * @author: farui.yu
 */
public class InspectObjectHeader {

    // -XX:+PrintFlagsFinal
    // -XX:+UseCompressedOops
    // -XX:+UseBiasedLocking
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();

        System.out.println("hex hashCode " + monitor);
        System.out.println("threadId " + Thread.currentThread().getId());

        System.out.println(ClassLayout.parseInstance(monitor).toPrintable());

        synchronized (monitor) {
            while (monitor.a < 100){
                monitor.a++;
                Thread.sleep(100);
            }
            System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
        }


        Thread thread0 = new Thread(() -> {
            synchronized (monitor) {
                monitor.a++;
                System.out.println("thread0 " + ClassLayout.parseInstance(monitor).toPrintable());
            }
        });

        Thread thread1 = new Thread(() -> {
            synchronized (monitor) {
                monitor.a++;
                System.out.println("thread1 " + ClassLayout.parseInstance(monitor).toPrintable());
            }
        });

        thread0.start();
        thread1.start();

        thread0.join();
        thread1.join();

        // 锁一旦升级 无法降级
        // non -> biased-lock -> thin-lock -> fat-lock
        // 实际上 此测试没有触发偏向锁,直接升级到 轻量级锁
        System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
    }
}
