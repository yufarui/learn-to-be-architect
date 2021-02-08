package indi.jdk.yufr.gc;

/**
 * @date: 2021/2/8 14:13
 * @author: farui.yu
 */
public class TargetSurvivorRatioGc {

    private static final int _1MB = 1024 * 1024;

    /**
     * 动态对象年龄判定测试
     * -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:TargetSurvivorRatio=50
     * VM args: -Xmx20m -Xms20m -Xmn10m -XX:+PrintGCDetails -XX:+PrintTenuringDistribution -XX:+UseSerialGC
     */
    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        // allocation1和allocation2和大于Survivor一半

        // 通过 TargetSurvivorRatio 设定 survivor中剩余空间比例,默认50%
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB / 4];

        allocation3 = new byte[_1MB * 4];

        // 再次放入4m对象导致Allocation Failure,从 allocation 1,2通过minorGc进入survivor
        // allocation4 进入到老年代
        allocation4 = new byte[_1MB * 4];

        // 可以只进行一次 minor-gc 观察allocation 1,2的位置
        allocation4 = null;
        // 再次触发gc, allocation 1,2经过minorGc,TargetSurvivorRatio的判定,进入到了老年代
        allocation4 = new byte[_1MB * 4];
    }
}
