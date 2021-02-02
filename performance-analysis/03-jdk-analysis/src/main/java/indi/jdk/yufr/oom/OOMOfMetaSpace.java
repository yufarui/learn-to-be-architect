package indi.jdk.yufr.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * [GC (Allocation Failure)  7885K->2253K(19968K), 0.0010610 secs]
 *
 * @date: 2021/1/28 16:40
 * @author: farui.yu
 */
public class OOMOfMetaSpace {
    static class OOMObject {
    }

    // -Xms15m -Xmx15m -XX:MetaspaceSize=15m -XX:MaxMetaspaceSize=15m -verbose:gc -showversion
    public static void main(String[] args) {
        List<Object> arr = new ArrayList<>();
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, null));
            Object o = enhancer.create();
            arr.add(o);
        }
    }
}
