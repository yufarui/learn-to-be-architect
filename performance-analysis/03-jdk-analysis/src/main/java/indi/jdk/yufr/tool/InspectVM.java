package indi.jdk.yufr.tool;

import indi.jdk.yufr.Resource;

import java.io.IOException;

/**
 * @date: 2021/2/2 14:24
 * @author: farui.yu
 */
public class InspectVM {

    // 观察 常量 所在地址
    private static final Resource constantResource = new Resource(0, "resource-0");
    // 观察 静态变量 所在地址
    private static Resource staticInitResource = new Resource(1, "resource-1");
    // 观察 类初始化结束后静态变量赋值 所在地址
    private static Resource staticResource;

    // -Xms15m -Xmx15m -XX:MetaspaceSize=15m -XX:MaxMetaspaceSize=15m -XX:+UseParallelGC -verbose:gc -showversion
    public static void main(String[] args) {
        staticResource = new Resource(2, "resource-2");
        try {
            System.in.read();
        } catch (IOException ioException) {
        }
    }
}
