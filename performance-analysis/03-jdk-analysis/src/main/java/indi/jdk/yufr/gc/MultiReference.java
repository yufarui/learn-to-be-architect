package indi.jdk.yufr.gc;

import indi.jdk.yufr.Resource;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 软引用:
 * 描述那些还有用但是并非必需的对象。在系统将要发生内存溢出之前，
 * 将会把这些对象回收，如果还没有足够的内存，才会抛出内存异常。使用 SoftReference 类来创建软引用。
 * <p>
 * 弱引用:
 * 也是用来描述非必须对象的，但是它的强度比软引用更弱一些。
 * 被弱引用关联的对象一定会被回收，也就是说它只能存活到下一次垃圾回收发生之前。使用 WeakReference 类来实现弱引用。
 * <p>
 * 虚引用:
 * 又称为幽灵引用或者幻影引用。一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，也无法通过虚引用取得一个对象。
 * 为一个对象设置虚引用关联的唯一目的就是能在这个对象被回收时收到一个系统通知。
 * 使用 PhantomReference 来实现虚引用。
 *
 * @date: 2021/2/1 14:52
 * @author: farui.yu
 */
public class MultiReference {

    private static int _1MB = 1024 * 1024;

    // -Xms30m -Xmx30m -showversion -verbose:gc -XX:SoftRefLRUPolicyMSPerMB=100
    public static void main(String[] args) throws Exception {

        Map<String, Reference> map = new HashMap<>();
        putPhantomReference(map);
        System.gc();
        System.out.println("phantom:" + map.get("phantom").get());

        putWeakReference(map);
        System.gc();
        System.out.println("weak:" + map.get("weak").get());

        putSoftReference(map);
        // 触发gc后soft没有被清除
        System.gc();
        System.out.println("soft:" + map.get("soft").get());

        // 试着等待100 * 30 ms => 可以适当减少SoftRefLRUPolicyMSPerMB的值
        Thread.sleep(100 * 30);
        System.gc();
        // 对象已经存活了 SoftRefLRUPolicyMSPerMB * heap(内存数值),被lru策略回收
        System.out.println("soft:" + map.get("soft").get());

        putSoftReference(map);
        List<Resource> arr = new ArrayList<>();

        for (int i = 0; i < 22; i++) {
            arr.add(new Resource(_1MB));
        }
        // 触发oom 同样导致soft 被回收
        System.out.println("soft:" + map.get("soft").get());
    }

    private static void putSoftReference(Map<String, Reference> map) {
        Resource resource = new Resource(_1MB * 4);
        SoftReference<Resource> sr = new SoftReference<>(resource);
        map.put("soft", sr);
    }

    private static void putWeakReference(Map<String, Reference> map) {
        Resource resource = new Resource(_1MB * 4);
        WeakReference<Resource> wr = new WeakReference<>(resource);
        map.put("weak", wr);
    }

    private static void putPhantomReference(Map<String, Reference> map) {
        Resource resource = new Resource(_1MB * 4);
        PhantomReference<Resource> pr = new PhantomReference<>(resource, null);
        map.put("phantom", pr);
    }
}
