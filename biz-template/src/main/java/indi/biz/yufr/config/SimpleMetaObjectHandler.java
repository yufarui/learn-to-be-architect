package indi.biz.yufr.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @date: 2021/1/5 10:59
 * @author: farui.yu
 */
@Slf4j
@Component
public class SimpleMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start insert fill ....");

        String username = "system";

        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "lastModifiedTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createdBy", String.class, username);
        this.strictInsertFill(metaObject, "lastModifiedBy", String.class, username);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start update fill ....");
        this.strictUpdateFill(metaObject, "lastModifiedTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "lastModifiedBy", String.class, "system");
    }
}
