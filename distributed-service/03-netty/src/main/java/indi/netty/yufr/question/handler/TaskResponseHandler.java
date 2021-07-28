package indi.netty.yufr.question.handler;

import indi.netty.yufr.question.protocol.TaskResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @date: 2021/7/28 10:02
 * @author: farui.yu
 */
@ChannelHandler.Sharable
public class TaskResponseHandler extends SimpleChannelInboundHandler<TaskResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TaskResponsePacket packet) throws Exception {

        System.out.println("服务器已响应请求,并返回结果" + packet);
    }

}
