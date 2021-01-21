package indi.biz.yufr.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * @date: 2021/1/4 15:48
 * @author: farui.yu
 */
public class MySqlIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        return null;
    }
}

