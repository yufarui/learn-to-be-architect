package ${base}.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class MySqlIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        return null;
    }
}

