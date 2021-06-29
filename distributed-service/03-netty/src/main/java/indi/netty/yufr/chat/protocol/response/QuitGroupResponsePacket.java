package indi.netty.yufr.chat.protocol.response;

import lombok.Data;
import indi.netty.yufr.chat.protocol.Packet;

import static indi.netty.yufr.chat.protocol.command.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
