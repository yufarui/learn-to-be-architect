package indi.jdk.yufr.gc;

import indi.jdk.yufr.Resource;

/**
 * 创建对象多大,直接进入Old区
 *
 * @date: 2021/1/27 14:38
 * @author: farui.yu
 */
public class MajorGc {
    // VMOptions
    // -Xms15m -Xmx15m -verbose:gc -XX:+PrintFlagsFinal -showversion
    public static void main(String[] args) {
        while (true) {

            // 创建大对象,并进入old区
            // 计算方式: 15m * (1/3) * (8/10) = 4m
            Resource.allocBigObject();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}
