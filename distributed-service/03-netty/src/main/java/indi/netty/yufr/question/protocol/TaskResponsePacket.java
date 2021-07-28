package indi.netty.yufr.question.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date: 2021/7/28 10:04
 * @author: farui.yu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponsePacket extends Packet {

    private String msg;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.TASK_RESPONSE;
    }
}
