package indi.biz.yufr.service.mapper;

import indi.biz.yufr.service.dto.PreferencePrototypeDTO;
import indi.biz.yufr.base.EntityConvert;
import indi.biz.yufr.entity.PreferencePrototype;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * <p>
 * 偏好设置表 转换类
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = {})
public interface PreferencePrototypeConvert extends EntityConvert<PreferencePrototypeDTO, PreferencePrototype> {

}