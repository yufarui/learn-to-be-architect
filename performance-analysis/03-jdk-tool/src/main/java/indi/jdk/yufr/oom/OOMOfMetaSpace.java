package indi.jdk.yufr.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * [GC (Allocation Failure)  7885K->2253K(19968K), 0.0010610 secs]
 *
 * @date: 2021/1/28 16:40
 * @author: farui.yu
 */
public class OOMOfMetaSpace {
    static class OOMObject {
    }

    // -Xms20m -Xmx20m -XX:MetaspaceSize=20m -XX:MaxMetaspaceSize=20m -verbose:gc -showversion
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, null));
            enhancer.create();
        }
    }
}
