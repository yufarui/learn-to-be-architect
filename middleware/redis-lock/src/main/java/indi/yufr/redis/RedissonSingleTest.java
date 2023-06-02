package indi.yufr.redis;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.config.Config;

public class RedissonSingleTest {

    public static final Redisson redisson;

    // 针对order:1001进行加锁
    public static final String LOCK_NAME = "lock:order:1001";

    static {
        Config config = new Config();
        // 单机模式
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redisson = (Redisson) Redisson.create(config);
    }

    public static void main(String[] args) {
        bloomFilter();
    }

    private static void bloomFilter() {
        RBloomFilter<Object> productBloom = redisson.getBloomFilter("product_bloom");

        System.out.println(productBloom.tryInit(100, 0.03));
        System.out.println(productBloom.count());
        System.out.println(productBloom.tryInit(200, 0.03));
        System.out.println(productBloom.count());
    }

    public void lock() {

        RLock lock = redisson.getLock(LOCK_NAME);
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }
}
