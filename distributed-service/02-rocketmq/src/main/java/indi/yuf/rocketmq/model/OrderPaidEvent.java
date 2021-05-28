package indi.yuf.rocketmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @date: 2021/5/26 16:27
 * @author: farui.yu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaidEvent implements Serializable {

    private String orderId;

    private BigDecimal paidMoney;

    private String localDateTime;

}
