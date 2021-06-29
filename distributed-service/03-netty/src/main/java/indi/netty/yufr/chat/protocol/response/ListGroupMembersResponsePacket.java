package indi.netty.yufr.chat.protocol.response;

import lombok.Data;
import indi.netty.yufr.chat.protocol.Packet;
import indi.netty.yufr.chat.session.Session;

import java.util.List;

import static indi.netty.yufr.chat.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
