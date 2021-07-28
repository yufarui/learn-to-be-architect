package indi.netty.yufr.question.handler;

import indi.netty.yufr.question.protocol.TaskRequestPacket;
import indi.netty.yufr.question.protocol.TaskResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端接受task-request,并进行回应;
 *
 * @date: 2021/7/28 10:02
 * @author: farui.yu
 */
@ChannelHandler.Sharable
public class TaskRequestHandler extends SimpleChannelInboundHandler<TaskRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TaskRequestPacket packet) throws Exception {

        System.out.println("接受到数据,数据长度为" + packet.getInfo().length);
        TaskResponsePacket taskResponsePacket = new TaskResponsePacket();
        taskResponsePacket.setVersion(packet.getVersion());
        taskResponsePacket.setMsg("ok");

        if (isValid(packet)) {
            taskResponsePacket.setSuccess(true);
        } else {
            taskResponsePacket.setSuccess(false);
            taskResponsePacket.setMsg("数据内容过短");
        }

        ctx.channel().writeAndFlush(taskResponsePacket);
    }

    private boolean isValid(TaskRequestPacket packet) {
        if (packet.getInfo().length >= 1024) {
            return true;
        }
        return false;
    }

}
