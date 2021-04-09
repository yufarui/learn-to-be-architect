/**
 * @date: 2021/3/29 14:57
 * @author: farui.yu
 */
public class Instruct {

    volatile int mm = 0;

    // javac Instruct.java
    // javap -c Instruct.class > instruct
    public int add() {
        int i = mm + 1;
        int j = 3;
        return i + j;
    }
}
