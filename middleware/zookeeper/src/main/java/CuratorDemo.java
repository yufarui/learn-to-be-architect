import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {

    private static final String CONNECT_STR = "localhost:2181";

    public static void main(String[] args) throws Exception {
        //构建客户端实例
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STR)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("").build();
        //启动客户端
        curatorFramework.start();
        Stat stat = new Stat();
        //查询节点数据
        byte[] bytes = curatorFramework.getData().storingStatIn(stat)
                .forPath("/user");
        System.out.println(new String(bytes));

//        curatorFramework.setACL().withACL(null).forPath(null);

        curatorFramework.watchers().add().usingWatcher((CuratorWatcher) event -> {
            System.out.println("addWatch /user");
            System.out.println(event.getPath());
        }).forPath("/user");

        // 能反复注册
        curatorFramework.getData().usingWatcher((CuratorWatcher) event -> {
            System.out.println("get -w /user");
            System.out.println(event.getPath());
        }).forPath("/user");

        Thread.sleep(Integer.MAX_VALUE);
    }
}
