package indi.netty.yufr.chat.protocol.response;

import lombok.Data;
import indi.netty.yufr.chat.protocol.Packet;
import indi.netty.yufr.chat.session.Session;

import static indi.netty.yufr.chat.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
