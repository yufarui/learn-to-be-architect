package indi.jdk.yufr.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2021/1/28 16:28
 * @author: farui.yu
 */
public class OOMOfHeap {

    // -Xms20m -Xmx20m -verbose:gc
    public static void main(String[] args) {
        List<OOMOfHeap> list = new ArrayList<>();
        while (true) {
            list.add(new OOMOfHeap());
        }
    }
}
