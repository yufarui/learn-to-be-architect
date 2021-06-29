package indi.netty.yufr.chat.protocol.request;

import lombok.Data;
import indi.netty.yufr.chat.protocol.Packet;

import java.util.List;

import static indi.netty.yufr.chat.protocol.command.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
