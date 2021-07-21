package indi.netty.yufr.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import indi.netty.yufr.chat.protocol.request.HeartBeatRequestPacket;
import indi.netty.yufr.chat.protocol.response.HeartBeatResponsePacket;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final indi.netty.yufr.chat.server.handler.HeartBeatRequestHandler INSTANCE = new indi.netty.yufr.chat.server.handler.HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
