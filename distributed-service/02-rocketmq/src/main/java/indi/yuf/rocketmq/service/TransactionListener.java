package indi.yuf.rocketmq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * 推荐阅读
 * https://zhuanlan.zhihu.com/p/115553176
 *
 * @date: 2021/5/28 9:14
 * @author: farui.yu
 */
@RocketMQTransactionListener
@Slf4j
public class TransactionListener implements RocketMQLocalTransactionListener {

    /**
     * 执行本地事物的回滚
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        String type = (String) arg;
        String txId = msg.getHeaders().getId().toString();

        // txId 的幂等判定

        if (StringUtils.equals(type, "order")) {
            log.info("接受order-业务请求");
        }

        try {
            log.info("开始执行本地事物[{}]", txId);

            // 执行 本服务对应 数据库操作
            // 执行 txId 的记录
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {

        String txId = msg.getHeaders().getId().toString();
        log.info("开始回查本地事务[{}]状态", txId);
        // 查询 txId 记录 state
        // 若事物记录不存在 返回状态 UNKNOWN

        return RocketMQLocalTransactionState.COMMIT;
    }
}
