package indi.jdk.yufr;

import java.util.Arrays;

/**
 * @date: 2021/1/25 14:11
 * @author: farui.yu
 */
public class Resource {

    private int id;
    private String name;
    private byte[] cache;
    private Object son;

    public Resource() {
    }

    public Resource(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Resource(int size) {
        this.cache = new byte[size];
    }

    public Resource(Object son) {
        this.son = son;
    }

    public static void allocSmallObject() {
        new Resource(10 * 1024);
    }

    public static void allocBigObject() {
        new Resource(1024 * 1024);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
