package indi.biz.yufr.util;

import indi.biz.yufr.entity.PreferencePrototype;
import indi.biz.yufr.select.DictionarySelectEnum;
import indi.biz.yufr.select.SelectOptionRequest;
import indi.biz.yufr.select.SelectResponse;
import indi.biz.yufr.select.service.SelectProcessorContext;
import indi.biz.yufr.service.dto.PreferencePrototypeDTO;
import indi.biz.yufr.service.dto.UserPreferenceDTO;
import indi.biz.yufr.service.mapper.PreferencePrototypeConvert;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

/**
 * @date: 2021/1/7 14:45
 * @author: farui.yu
 */
public class UserPreferenceConvertUtil {

    public static PreferencePrototypeDTO convert(String preferenceCode) {

        if (StringUtils.isBlank(preferenceCode)) {
            return null;
        }

        SelectOptionRequest request = SelectOptionRequest
                .builder()
                .type(DictionarySelectEnum.PREFERENCE_PROP.getType())
                .build();
        SelectResponse<PreferencePrototype> select = SelectProcessorContext.getSelect(request);

        return Mappers.getMapper(PreferencePrototypeConvert.class).toDto(select.getData(preferenceCode));
    }

    public static String convertPreferenceCode(UserPreferenceDTO dto) {

        if (dto == null) {
            return null;
        }

        if (dto.getPreferenceCode() != null) {
            return dto.getPreferenceCode();
        }

        PreferencePrototypeDTO prototypeDTO = dto.getPreferencePrototypeDTO();
        if (prototypeDTO == null) {
            return null;
        }
        return prototypeDTO.getCode();
    }
}
