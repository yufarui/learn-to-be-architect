package indi.jdk.yufr;

/**
 * @date: 2021/1/25 14:11
 * @author: farui.yu
 */
public class User {

    private byte[] cache;

    public User(int size) {
        this.cache = new byte[size];
    }

    // 对象创建,仅线程可见,对象无法逃逸
    public static void allocSmallObject() {
        new User(10 * 1024);
    }

    public static void allocObject() {
        new User(100 * 1024);
    }

    // 创建大对象,逃逸,并进入old区
    public static void allocBigObject() {
        new User(1024 * 1024);
    }

}
