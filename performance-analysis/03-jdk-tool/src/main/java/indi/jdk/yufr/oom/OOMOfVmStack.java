package indi.jdk.yufr.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行代码千万注意；
 * 因为在Windows平台上的虚拟机中，Java的线程是映射到操作系统的内核线程上的，因此代码可能导致系统假死。
 * <p>
 * 创建第4000个线程时，触发了oom
 * [GC (Allocation Failure) -- 19449K->19449K(19968K), 0.0167447 secs]
 * [Full GC (Ergonomics)  19449K->19449K(19968K), 0.0690711 secs]
 * [GC (Allocation Failure) -- 19449K->19449K(19968K), 0.0167741 secs]
 * [Full GC (Allocation Failure)  19449K->19449K(19968K), 0.0856816 secs]
 * [GC (Allocation Failure) -- 19449K->19449K(19968K), 0.0169637 secs]
 * [Full GC (Ergonomics)  19449K->19449K(19968K), 0.0892667 secs]
 * [GC (Allocation Failure) -- 19449K->19449K(19968K), 0.0170972 secs]
 * [Full GC (Allocation Failure)  19449K->19449K(19968K), 0.0714965 secs]
 * java.lang.OutOfMemoryError: Java heap space[GC (Allocation Failure) -- 19450K->19450K(19968K), 0.0178893 secs]
 *
 * @date: 2021/1/28 14:22
 * @author: farui.yu
 */
public class OOMOfVmStack {

    // -Xms15m -Xmx15m -Xss1m -verbose:gc
    public static void main(String[] args) {
        byte[] bytes = new byte[8 * 1024 * 1024];
        System.out.println("开始创建线程");
        OOMOfVmStack oom = new OOMOfVmStack();
        oom.stackLeakByThread();
    }

    private void alive() {

        while (true) {

        }
    }

    public void stackLeakByThread() {
        int size = 0;
        List<Object> arr = new ArrayList<>();
        while (size < 100000) {
            Thread thread = new Thread(this::alive);
            arr.add(thread);
//            thread.start();
            System.out.println(++size);
        }
    }
}
