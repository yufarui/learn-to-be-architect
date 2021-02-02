package indi.jdk.yufr.gc;

import indi.jdk.yufr.Resource;

/**
 * @date: 2021/1/27 9:41
 * @author: farui.yu
 */
public class MinorGc {

    // VMOptions
    // -Xms15m -Xmx15m -verbose:gc -XX:+PrintFlagsFinal -showversion
    public static void main(String[] args) {
        while (true) {
            Resource.allocSmallObject();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
