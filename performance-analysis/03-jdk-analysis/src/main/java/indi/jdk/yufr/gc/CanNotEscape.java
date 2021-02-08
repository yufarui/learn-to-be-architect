package indi.jdk.yufr.gc;

import indi.jdk.yufr.Resource;

/**
 * 对象无法逃逸,在栈内创建对象
 *
 * @date: 2021/1/27 14:11
 * @author: farui.yu
 */
public class CanNotEscape {

    public static Object commonObject = new Object();

    /*
     * 最大堆空间为15m 初始堆空间为15m 启用逃逸分析 打印ＧＣ日志 关闭TLAB 启用消除分配，允许对象打散分配到栈上
     * -Xms15m -Xmx15m -XX:+DoEscapeAnalysis -verbose:gc -XX:-UseTLAB -XX:+EliminateAllocations
     *
     * -XX:+EliminateAllocations
     * Use escape analysis to eliminate allocations
     * 关闭逃逸分析 或者 关闭消除分配 都可触发大量gc
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Resource stackResource = new Resource();

        for (int i = 0; i < 1000000000; i++) {
            // 1.当前对象无法逃逸，只会在栈内创建
            new Resource();
            // 2.数组 作为对象 会强制在堆内开辟空间，若为数组赋值，则对象会逃逸
            // new Resource(1);
            // 3.字符串作为对象，会在堆内常量池建立缓存，故若字符串固定，则对象同意无法逃逸
//            new Resource(0, "name");
            // 4.若字符串不固定，对象还是强制在堆内开辟空间，对象逃逸
//            new Resource(i, "name" + i);
            // 5. 思考如下两种方案 是否逃逸
//            new Resource(new User());
//            new Resource(stackResource);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
