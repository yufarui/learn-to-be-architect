package indi.biz.yufr.select;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下拉列表请求值
 *
 * @date: 2020/9/21 19:07
 * @author: farui.yu
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectOptionRequest {

    /**
     * 对应值为enum中的type值
     */
    private String type;

    /**
     * 特定查询key
     */
    private String specKey;

}
