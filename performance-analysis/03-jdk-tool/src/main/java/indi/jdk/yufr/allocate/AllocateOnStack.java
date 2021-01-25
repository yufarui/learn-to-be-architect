package indi.jdk.yufr.allocate;

import indi.jdk.yufr.User;

/**
 * 此内容适用于 02-对象的创建与分配
 * <br/>
 *
 * @date: 2021/1/25 14:17
 * @author: farui.yu
 */
public class AllocateOnStack {

    /*
    * 最大堆空间为15m 初始堆空间为15m 启用逃逸分析 打印ＧＣ日志 关闭TLAB 启用消除分配，允许对象打散分配到栈上
    * -Xmx15m -Xms15m -XX:+DoEscapeAnalysis -verbose:gc -XX:-UseTLAB -XX:+EliminateAllocations
    *
    * -XX:+EliminateAllocations
    * Use escape analysis to eliminate allocations
    * 关闭逃逸分析 或者 关闭消除分配 都可触发大量gc
    */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static void alloc(int i) {
        User user = new User();
        user.setId(i);
        user.setName("yufr");
    }
}
