package indi.netty.yufr.chat.protocol.request;

import indi.netty.yufr.chat.protocol.Packet;
import lombok.Data;

import static indi.netty.yufr.chat.protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
