package indi.jdk.yufr.gc;

/**
 * <p>
 * 当一个对象可被回收时，如果需要执行该对象的 finalize() 方法，那么就有可能在该方法中让对象重新被引用，从而实现自救。
 * 自救只能进行一次，如果回收的对象之前调用了 finalize() 方法自救，后面回收时不会调用 finalize() 方法。
 * <p>
 * finalize() 类似 C++ 的析构函数，用来做关闭外部资源等工作。但是 try-finally 等方式可以做的更好，并且该方法运行代价高昂，
 * 不确定性大，无法保证各个对象的调用顺序，因此最好不要使用。
 *
 * @date: 2021/2/1 14:42
 * @author: farui.yu
 */
public class FinalizeEscapeGc {

    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        //自救，把自己(this关键字)赋值给一个类变量
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();
        //对象第一次进行自救
        SAVE_HOOK = null;
        System.gc();
        //因为finalize()方法优先级很低，暂停0.5s等待
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }

        //与上面的代码相同，但是自救失败，因为finalize()方法只能被系统自动调用一次
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }
    }
}
