package indi.netty.yufr.question;


import indi.netty.yufr.question.codec.PacketDecoder;
import indi.netty.yufr.question.codec.PacketEncoder;
import indi.netty.yufr.question.handler.IMIdleStateHandler;
import indi.netty.yufr.question.handler.TaskResponseHandler;
import indi.netty.yufr.question.protocol.TaskRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class QuestionNettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new TaskResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                startTaskTransfer(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private static void startTaskTransfer(Channel channel) {

        new Thread(() -> {
            while (channel.isActive()) {
                TaskRequestPacket taskRequestPacket = new TaskRequestPacket();

                byte[] info = new byte[1024 * 1024 * 1024];
                taskRequestPacket.setInfo(info);

                System.out.println("开始传输数据,数据长度为" + info.length);

                channel.writeAndFlush(taskRequestPacket);

                ByteBuf buf = Unpooled.copiedBuffer(info);
                channel.writeAndFlush(buf);

                ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                int random = threadLocalRandom.nextInt(1000, 2000);

                try {
                    Thread.sleep(random);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}
