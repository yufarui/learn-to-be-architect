package indi.concurrency.yufr.jmm;

/**
 * @date: 2021/4/12 16:47
 * @author: farui.yu
 */
public class VolatileHBTest {

    volatile static boolean flag = true;

    public static void main(String[] args) {

        Thread thread0 = new Thread(() -> {
            System.out.println();
            while (flag) {

            }
            System.out.println("flag update");
        });

        Thread thread1 = new Thread(() -> {
            flag = false;
        });

        thread0.start();
        thread1.start();
    }

}
