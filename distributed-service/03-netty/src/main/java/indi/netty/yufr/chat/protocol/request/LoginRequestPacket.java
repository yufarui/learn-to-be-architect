package indi.netty.yufr.chat.protocol.request;

import lombok.Data;
import indi.netty.yufr.chat.protocol.Packet;

import static indi.netty.yufr.chat.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
