package indi.netty.yufr.chat.server;

import indi.netty.yufr.chat.codec.PacketDecoder;
import indi.netty.yufr.chat.codec.PacketEncoder;
import indi.netty.yufr.chat.codec.Spliter;
import indi.netty.yufr.chat.handler.IMIdleStateHandler;
import indi.netty.yufr.chat.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @date: 2021/6/29 11:16
 * @author: farui.yu
 */
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        // 空闲检测
//                        ch.pipeline().addLast(new IMIdleStateHandler());

                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        // 登录请求处理器
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        // 单聊消息请求处理器
                        ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
                        // 创建群请求处理器
                        ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
                        // 加群请求处理器
                        ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
                        // 退群请求处理器
                        ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
                        // 获取群成员请求处理器
                        ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
                        // 登出请求处理器
                        ch.pipeline().addLast(LogoutRequestHandler.INSTANCE);

//                        ch.pipeline().addLast(IMHandler.INSTANCE);
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });


        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                        + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
