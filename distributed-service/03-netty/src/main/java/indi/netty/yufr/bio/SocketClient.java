package indi.netty.yufr.bio;

import java.io.IOException;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 9000);

        //向服务端发送数据
        //尝试不发送数据,则服务端对应线程会一直堵塞
        socket.getOutputStream().write("HelloServer".getBytes());
        socket.getOutputStream().flush();
        System.out.println("向服务端发送数据结束");

        byte[] bytes = new byte[1024];
        //接收服务端回传的数据
        socket.getInputStream().read(bytes);
        System.out.println("接收到服务端的数据：" + new String(bytes));
        socket.close();

        Thread.sleep(10000);
    }
}