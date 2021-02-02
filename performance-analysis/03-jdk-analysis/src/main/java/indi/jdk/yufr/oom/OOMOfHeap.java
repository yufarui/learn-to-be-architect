package indi.jdk.yufr.oom;

import indi.jdk.yufr.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2021/1/28 16:28
 * @author: farui.yu
 */
public class OOMOfHeap {

    private static int _1MB = 1024 * 1024;

    // -Xms15m -Xmx15m -verbose:gc
    public static void main(String[] args) {
        List<Resource> list = new ArrayList<>();
        while (true) {
            list.add(new Resource(_1MB));
        }
    }
}
