package indi.biz.yufr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.biz.yufr.entity.PreferencePrototype;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 偏好设置表 Mapper 接口
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@CacheNamespace(flushInterval = 10000L)
public interface PreferencePrototypeMapper extends BaseMapper<PreferencePrototype> {

}
