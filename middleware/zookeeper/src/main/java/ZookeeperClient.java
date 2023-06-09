import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ZookeeperClient {
    private static final String CONNECT_STR = "localhost:2181";

    public static void main(String[] args) throws Exception {

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try (ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STR,
                4000, event -> {
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()
                    && event.getType() == Watcher.Event.EventType.None) {
                //如果收到了服务端的响应事件，连接成功
                countDownLatch.countDown();
            }
        })) {
            System.out.println("连接中" + zooKeeper.getState());
            countDownLatch.await();
            System.out.println("连接成功" + zooKeeper.getState());

            Stat stat = zooKeeper.exists("/user", false);

            if (null == stat) {
                // 等价命令 create /user 'hero' world:anyone:cdrwa
                zooKeeper.create("/user", "hero".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
//            System.out.println(new String(zooKeeper.getData("/user", false, null)));

//            zooKeeper.getData("/user", true, new IDataCallback(), null);

            zooKeeper.addWatch("/user", event -> {
                Stat statx = new Stat();
                try {
                    System.out.println(new String(zooKeeper.getData(event.getPath(), true, statx)));
                } catch (Exception e) {

                }
                System.out.println(statx.getCzxid() + "," +
                        statx.getMzxid() + "," +
                        statx.getVersion());
            }, AddWatchMode.PERSISTENT);
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    static class IDataCallback implements AsyncCallback.DataCallback {
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            System.out.println(rc + ", " + path + ", " + new String(data));
            System.out.println(stat.getCzxid() + "," +
                    stat.getMzxid() + "," +
                    stat.getVersion());
        }
    }

}
