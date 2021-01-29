package indi.jdk.yufr.gc;

import indi.jdk.yufr.User;

/**
 * @date: 2021/1/27 9:41
 * @author: farui.yu
 */
public class MinorGc {

    // VMOptions
    // -Xms1g -Xmx1g -Xmn300m -verbose:gc -XX:+PrintFlagsFinal
    public static void main(String[] args) {
        while (true) {
            User.allocObject();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }
}
