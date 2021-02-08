package indi.jdk.yufr.gc;

/**
 * @date: 2021/2/8 15:20
 * @author: farui.yu
 */
public class HandlePromotion {

    private static final int _1MB = 1024 * 1024;

    /**
     * jdk6 update 24之后,默认存在担保机制
     * 空间分配担保测试
     * VM args: -Xmx20m -Xms20m -Xmn10m -XX:+PrintGCDetails -XX:+UseSerialGC
     */
    public static void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;

        allocation1 = new byte[_1MB * 2];
        allocation2 = new byte[_1MB * 2];
        allocation3 = new byte[_1MB * 2];
        allocation1 = null;

        // 年轻代 默认为 10*0.8,达到8m 触发minor-gc, allocation1被回收
        // allocation 2,3 对象过大,直接进入到老年代
        // allocation4 还留在 eden
        allocation4 = new byte[_1MB * 2];

        allocation5 = new byte[_1MB * 2];
        allocation6 = new byte[_1MB * 2];

        allocation4 = null;
        allocation5 = null;
        allocation6 = null;

        // eden 再次触发8m 阈值,触发minor-gc, jdk默认触发担保
        // 当前老年区 存在 allocation 2,3,之前平均值(实际就一次)为回收4m
        // 老年区 担保失败=>触发了一次full-gc
        allocation7 = new byte[_1MB * 2];
    }

    public static void main(String[] args) {
        testHandlePromotion();
    }
}
