package indi.jdk.yufr.gc;

import indi.jdk.yufr.User;

/**
 * 对象无法逃逸,在栈内创建对象
 * @date: 2021/1/27 14:11
 * @author: farui.yu
 */
public class CanNotEscape {

    /*
     * 最大堆空间为15m 初始堆空间为15m 启用逃逸分析 打印ＧＣ日志 关闭TLAB 启用消除分配，允许对象打散分配到栈上
     * -Xms1g -Xmx1g -Xmn300m -XX:+DoEscapeAnalysis -verbose:gc -XX:-UseTLAB -XX:+EliminateAllocations
     *
     * -XX:+EliminateAllocations
     * Use escape analysis to eliminate allocations
     * 关闭逃逸分析 或者 关闭消除分配 都可触发大量gc
     */
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {

            while (true) {
                User.allocSmallObject();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        thread.start();
    }


}
