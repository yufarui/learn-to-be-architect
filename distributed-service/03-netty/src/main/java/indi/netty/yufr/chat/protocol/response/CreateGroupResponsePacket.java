package indi.netty.yufr.chat.protocol.response;

import lombok.Data;
import indi.netty.yufr.chat.protocol.Packet;

import java.util.List;

import static indi.netty.yufr.chat.protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_RESPONSE;
    }
}
