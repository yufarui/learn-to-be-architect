import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

public class ZkGetDataSync implements Watcher {
    private ZooKeeper zk;

    public ZkGetDataSync(ZooKeeper zk) {
        this.zk = zk;
    }

    public void process(WatchedEvent event) {
        Stat stat = new Stat();
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (event.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println(new String(zk.getData(event.getPath(), true, stat)));
                    System.out.println(stat.getCzxid() + "," +
                            stat.getMzxid() + "," +
                            stat.getVersion());
                } catch (Exception e) {
                }
            }
        }
    }
}
