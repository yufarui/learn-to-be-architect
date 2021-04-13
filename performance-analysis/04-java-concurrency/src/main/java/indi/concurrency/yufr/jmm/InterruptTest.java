package indi.concurrency.yufr.jmm;

/**
 * @date: 2021/4/12 15:45
 * @author: farui.yu
 */
public class InterruptTest {
    static int a = 5;

    public static void main(String[] args) throws InterruptedException {
        happensBefore();
    }

    private static void happensBefore() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (true) {
                if (i < a) {
                    System.out.println(i++);
                }

                // 捕获中断状态,happens-before 保证了a修改后的可见性
                if (Thread.currentThread().isInterrupted()) {
                    continue;
                }

                if (i == 10) {
                    break;
                }
            }
        });

        thread.start();
        Thread.sleep(1000);
        System.out.println("1000 ms after");
        a = 10;
        thread.interrupt();
        thread.join();

        System.out.println("end");
        Thread.sleep(1000);
    }


    // Thread.currentThread().isInterrupted()
    // Thread.interrupted() 清除中断标识
    // Thread.sleep(),Thread.join() 同样 也会清除中断标识
    public void interruptFlag() throws InterruptedException {
        Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }
                    try {
                        Thread.sleep(2000); // 睡眠时中断会清除中断标志位
                    } catch (InterruptedException e) {
                        // 如果少了下面这句，这个线程虽然在外面中断，但是只要中断睡眠中的进程
                        // 就会清除中断标志位，仍然处于无限循环，会竞争CPU资源
                        Thread.currentThread().interrupt(); // 再次中断置上中断标记
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(200);
        t1.interrupt();
    }
}
