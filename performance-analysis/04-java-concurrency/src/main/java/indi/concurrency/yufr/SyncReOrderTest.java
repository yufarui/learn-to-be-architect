package indi.concurrency.yufr;

public class SyncReOrderTest {

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

        private boolean flag = false;
        private int value = 0;

        // synchronized 内部依然会出现重排序
        private void writer() {
            synchronized (this) {
                // load-store
                flag = true;
                value = 2;
                // store-store
            }
        }

        private void reader() {
            if (!flag && value == 2) {
                System.out.println(System.currentTimeMillis());
                System.out.println("发生了重排序, client.value=" + value);
            }
        }
    }

}
