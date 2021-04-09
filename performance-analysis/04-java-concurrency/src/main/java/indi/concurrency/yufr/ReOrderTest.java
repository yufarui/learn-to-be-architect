package indi.concurrency.yufr;

/**
 * @date: 2021/4/6 19:07
 * @author: farui.yu
 */
public class ReOrderTest {

    // -server -Xcomp 在server模式下尝试以 jit-即时编译模式执行代码
    // -Xint -Xmixed 比这两个模式 ,出现重排序概率低
    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < 500000 * 8; i++) {
            final ReOrderClient client = new ReOrderClient();
            Thread t1 = new Thread(client::writer);

            Thread t2 = new Thread(client::reader);

            t1.start();
            t2.start();
        }

        System.out.println("the end");
    }

    private static class ReOrderClient {

        // normal_store then normal_store
        // 无屏障指令-可能重排
        // volatile_store then normal_store
        // 无屏障指令-可能重排
        // normal_store then volatile_store
        // store-store屏障指令-不会重排
        // volatile_store then volatile_store
        // store-store屏障指令-不会重排
        private volatile boolean flag = false;
        private volatile int value = 0;

        private void writer() {
            flag = true;
            value = 2;
        }

        private void reader() {
            if (!flag && value == 2) {
                System.out.println(System.currentTimeMillis());
                System.out.println("发生了重排序, client.value=" + value);
            }
        }
    }

}
