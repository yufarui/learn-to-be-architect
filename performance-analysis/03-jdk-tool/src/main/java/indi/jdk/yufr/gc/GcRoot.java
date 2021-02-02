package indi.jdk.yufr.gc;

import indi.jdk.yufr.Resource;

/**
 * @date: 2021/2/1 11:24
 * @author: farui.yu
 */
public class GcRoot {

    /**
     * GCRoots:栈帧,静态变量,常量
     * -Xms1024m -Xmx1024m -Xmn512m -showversion -XX:+PrintGCDetails
     */
    private static int _10MB = 10 * 1024 * 1024;
    private static final Resource constantResource = new Resource(_10MB);
    private static Resource staticResource = new Resource(2 * _10MB);

    public static void main(String[] args) {

        method01();
        System.gc();

        // 在主栈上创建对象
        Resource stackLocal = new Resource(4 * _10MB);
        stackLocal = null;
        System.gc();

        staticResource = null;
        System.gc();
    }

    public static void method01() {
        System.out.println("在栈帧中创建对象");
        Resource t = new Resource(8 * _10MB);
        System.gc();
        System.out.println("第一次GC完成");
    }
}
