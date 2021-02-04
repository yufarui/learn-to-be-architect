package indi.jdk.yufr.gc;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

/**
 * 类的卸载
 *
 * @date: 2021/2/4 19:11
 * @author: farui.yu
 */
public class LoadAndUnLoadClass {

    public static void main(String[] args) throws Exception {

        String property = System.getProperty("user.dir");
        String dirPrefix = property
                + File.separator + "performance-analysis"
                + File.separator + "03-jdk-analysis"
                + File.separator + "lib"
                + File.separator;

        dirPrefix = dirPrefix.replace("\\", "/");

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class<?> testOfGroovyClass = groovyClassLoader.parseClass(new File(dirPrefix + "Test.java"));
        System.out.println(testOfGroovyClass.getName());

        System.out.println("####加载完成 testOfGroovyClass #####");

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:" + dirPrefix)});
        Class<?> testOfUrlClass = urlClassLoader.loadClass("indi.example.Test");
        System.out.println(testOfUrlClass.getName());

        System.out.println("####加载完成 testOfUrlClass #####");

        // 观察jar包中内容
        File file = new File(dirPrefix + "example.jar");
        JarFile jarFile = new JarFile(file);

        jarFile.stream()
                .filter(jarEntry -> jarEntry.getName().endsWith(".class"))
                .forEach(jarEntry -> {
                    System.out.println(jarEntry.getName());
                });

        URLClassLoader urlClassLoaderWithJar = new URLClassLoader(new URL[]{new URL("jar:file:" + dirPrefix + "example.jar!/")});
        Class<?> testOfJarClass = urlClassLoaderWithJar.loadClass("indi.example.Test");
        System.out.println(testOfJarClass.getName());
        System.out.println("####加载完成 testOfJarClass #####");

        // 指定classloader才能通过Class.forName找到Test.class
        Class.forName("indi.example.Test", false, urlClassLoaderWithJar);

        // 尝试将classLoader置null,class置null
        // 在jvisualvm中查看dump,观察类
        testOfUrlClass = null;
        urlClassLoader = null;
        
        System.in.read();
    }
}
