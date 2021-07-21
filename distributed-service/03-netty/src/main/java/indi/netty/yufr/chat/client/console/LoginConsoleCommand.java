package indi.netty.yufr.chat.client.console;

import indi.netty.yufr.chat.session.Session;
import indi.netty.yufr.chat.util.SessionUtil;
import io.netty.channel.Channel;
import indi.netty.yufr.chat.protocol.request.LoginRequestPacket;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("输入用户名登录: ");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
        SessionUtil.bindSession(new Session(loginRequestPacket.getUserName(), loginRequestPacket.getUserName()), channel);
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
