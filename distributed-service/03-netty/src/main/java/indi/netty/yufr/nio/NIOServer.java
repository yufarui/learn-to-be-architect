package indi.netty.yufr.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NIOServer {

    private static int size = Runtime.getRuntime().availableProcessors();
    private static ExecutorService workGroupExecutor = new ThreadPoolExecutor(
            2 * size, 2 * size + 1,
            0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024),
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("nio.server.worker");
                return thread;
            });

    public static void main(String[] args) throws IOException {
        // 创建一个在本地端口进行监听的服务Socket通道.并设置为非阻塞方式
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //必须配置为非阻塞才能往selector上注册，否则会报错，selector模式本身就是非阻塞模式
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(9000));
        // 创建一个选择器selector
        Selector selector = Selector.open();
        // 把ServerSocketChannel注册到selector上，并且selector对客户端accept连接操作感兴趣
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            System.out.println("等待事件发生。。");
            // 轮询监听channel里的key，select是阻塞的，accept()也是阻塞的
            int select = selector.select();

            // 有客户端请求，被轮询监听到
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                //删除本次已处理的key，防止下次select重复处理
                it.remove();
                if (key.isAcceptable()) {
                    acceptHandle(key);
                } else {
                    rwHandle(key);
                }

            }
        }
    }

    private static void acceptHandle(SelectionKey key) throws IOException {
        System.out.println("有客户端连接事件发生了。。");
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        //NIO非阻塞体现：此处accept方法是阻塞的，但是这里因为是发生了连接事件，所以这个方法会马上执行完，不会阻塞
        //处理完连接请求不会继续等待客户端的数据发送
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);
        //通过Selector监听Channel时对读事件感兴趣
        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private static void rwHandle(SelectionKey key) throws IOException {
        if (key.isReadable()) {
            System.out.println("有客户端数据可读事件发生了。。");
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //NIO非阻塞体现:首先read方法不会阻塞，其次这种事件响应模型，当调用到read方法时肯定是发生了客户端发送数据的事件
            int len = sc.read(buffer);
            if (len != -1) {
                System.out.println("读取到客户端发送的数据：" + new String(buffer.array(), 0, len));
            }

            key.interestOps(SelectionKey.OP_WRITE);
        } else if (key.isWritable()) {
            System.out.println("write事件发生了。。");
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
            sc.write(bufferToWrite);
            key.interestOps(SelectionKey.OP_READ);
        }
    }
}