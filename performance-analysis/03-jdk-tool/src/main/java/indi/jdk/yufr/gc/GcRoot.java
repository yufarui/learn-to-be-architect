package indi.jdk.yufr.gc;

import indi.jdk.yufr.Resource;

/**
 * @date: 2021/2/1 11:24
 * @author: farui.yu
 */
public class GcRoot {

    /**
     * 测试常量引用对象作为GCRoots
     * -Xms1024m -Xmx1024m -Xmn512m -verbose:gc -showversion
     * <p>
     * 运行结果:
     * 在栈帧中创建对象
     * [GC (System.gc())  136233K->113456K(983040K), 0.0785660 secs]
     * [Full GC (System.gc())  113456K->113312K(983040K), 0.0422883 secs]
     * 第一次GC完成
     * [GC (System.gc())  121177K->113344K(983040K), 0.0018983 secs]
     * [Full GC (System.gc())  113344K->31382K(983040K), 0.0109605 secs]
     *
     * [GC (System.gc())  88071K->31382K(983040K), 0.0010654 secs]
     * [Full GC (System.gc())  31382K->31377K(983040K), 0.0197288 secs]
     *
     * [GC (System.gc())  31377K->31377K(983040K), 0.0009887 secs]
     * [Full GC (System.gc())  31377K->10897K(983040K), 0.0113364 secs]
     * <p>
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
