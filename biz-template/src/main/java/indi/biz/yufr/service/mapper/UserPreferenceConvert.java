package indi.biz.yufr.service.mapper;

import indi.biz.yufr.base.EntityConvert;
import indi.biz.yufr.entity.UserPreference;
import indi.biz.yufr.service.dto.UserPreferenceDTO;
import indi.biz.yufr.util.UserPreferenceConvertUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

/**
 * <p>
 * 用户与偏好的关联表 转换类
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = {},
        imports = UserPreferenceConvertUtil.class
)
public interface UserPreferenceConvert extends EntityConvert<UserPreferenceDTO, UserPreference> {

    @Mappings({
            @Mapping(target = "preferenceCode",
                    expression = "java( UserPreferenceConvertUtil.convertPreferenceCode(dto) )"),
    })
    UserPreference toEntity(UserPreferenceDTO dto);

    @Mappings({
            @Mapping(target = "preferencePrototypeDTO",
                    expression = "java( UserPreferenceConvertUtil.convert(entity.getPreferenceCode()) )"),
            @Mapping(target = "preferenceCode", ignore = true)
    })
    UserPreferenceDTO toDto(UserPreference entity);
}