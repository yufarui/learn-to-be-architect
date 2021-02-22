package indi.jdk.yufr.gc;

/**
 * @date: 2021/2/9 16:46
 * @author: farui.yu
 */
public class ThreeColorMarked {

    public static void main(String[] args) {

        // 生成的对象假定为 *a,*b,*c
        A a = new A();
        a.b = new B();
        a.c = null;
        a.b.c = new C();

        // 假定此时开始做并发标记,并且假设没有开启读写屏障
        C c = a.b.c; // 读
        // 扫描*b时,*b此时为灰色对象
        // a.b.c=null -> *b 与 *c引用断开
        // *c被标记为白色对象,a.c -> *a引用*c
        // 黑色节点指向白色节点,而黑色节点此时不会再次扫描
        // 致使*c还是白色节点
        a.b.c = null; // 写
        a.c = c; // 写
    }

    static class A {
        B b;
        C c;
    }

    static class B {
        C c;
    }

    static class C {

    }
}
