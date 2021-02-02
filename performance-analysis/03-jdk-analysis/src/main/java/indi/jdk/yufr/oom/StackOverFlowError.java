package indi.jdk.yufr.oom;

/**
 * @date: 2021/1/28 14:04
 * @author: farui.yu
 */
public class StackOverFlowError {

    private int stackLength = 1;

    // -Xss521k
    public static void main(String[] args) {

        StackOverFlowError sof = new StackOverFlowError();
        try {
            sof.recursion();
        } catch (StackOverflowError e) {
            System.out.println("stackLength:" + sof.stackLength);
            throw e;
        }
    }

    public int recursion() {
        this.stackLength++;
        return recursion();
    }
}
