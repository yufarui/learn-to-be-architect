package indi.jdk.yufr.gc;

import indi.jdk.yufr.User;

/**
 * 创建对象多大,直接进入Old区
 *
 * @date: 2021/1/27 14:38
 * @author: farui.yu
 */
public class MajorGc {
    // VMOptions
    // -Xms1g -Xmx1g -Xmn300m +UseG1GC -verbose:gc -XX:+PrintFlagsFinal
    public static void main(String[] args) {
        while (true) {
            User.allocBigObject();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }
}
