package indi.biz.yufr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.biz.yufr.base.PageUtils;
import indi.biz.yufr.entity.UserPreference;
import indi.biz.yufr.mapper.UserPreferenceMapper;
import indi.biz.yufr.service.IPreferencePrototypeService;
import indi.biz.yufr.service.IUserPreferenceService;
import indi.biz.yufr.service.dto.PreferencePrototypeDTO;
import indi.biz.yufr.service.dto.UserPreferenceDTO;
import indi.biz.yufr.service.dto.search.UserPreferenceCriteria;
import indi.biz.yufr.service.mapper.UserPreferenceConvert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户与偏好的关联表 服务实现类
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Service
public class UserPreferenceServiceImpl extends ServiceImpl<UserPreferenceMapper, UserPreference> implements IUserPreferenceService {

    @Autowired
    private UserPreferenceConvert userPreferenceConvert;
    @Autowired
    private PageUtils pageUtils;
    @Autowired
    private IPreferencePrototypeService preferencePrototypeService;

    @Override
    public UserPreferenceDTO getDTOById(Serializable id) {
        return userPreferenceConvert
                .toDto(getById(id));
    }

    @Override
    public void saveDTO(UserPreferenceDTO dto) {
        UserPreference entity = userPreferenceConvert.toEntity(dto);
        save(entity);
    }

    @Override
    public void updateDTOById(UserPreferenceDTO userPreferenceDTO) {
        UserPreference entity = userPreferenceConvert.toEntity(userPreferenceDTO);
        updateById(entity);
    }

    @Override
    public void removeDTOById(Serializable id) {
        removeById(id);
    }

    @Override
    public List<UserPreferenceDTO> getDTOList(UserPreferenceCriteria search) {
        List<UserPreference> list = list(buildQueryWrapper(search));
        List<UserPreferenceDTO> userPropList = userPreferenceConvert.toDto(list);

        List<UserPreferenceDTO> allProps = new ArrayList<>(userPropList);

        List<PreferencePrototypeDTO> defaultProp = preferencePrototypeService.getDTOList(null);

        Map<String, UserPreference> map = new HashMap<>();
        list.forEach((userPreference -> {
            map.put(userPreference.getPreferenceCode(), userPreference);
        }));

        defaultProp.forEach(prop -> {
            if (!map.containsKey(prop.getCode())) {
                UserPreferenceDTO tmp = new UserPreferenceDTO();
                tmp.setPreferenceCode(prop.getCode());
                tmp.setPreferenceValue(prop.getDefaultValue());
                tmp.setPreferencePrototypeDTO(prop);
                allProps.add(tmp);
            }
        });

        return allProps;
    }

    @Override
    public IPage<UserPreferenceDTO> getDTOPage(UserPreferenceCriteria search) {

        IPage<UserPreference> page = page(pageUtils.page(), buildQueryWrapper(search));

        return page.convert(userPreferenceConvert::toDto);
    }

    @Override
    public void updateBatchDTOByUserIdAndCode(List<UserPreferenceDTO> dto) {
        List<UserPreference> userPreferenceList = userPreferenceConvert.toEntity(dto);

        if (CollectionUtils.isEmpty(userPreferenceList)) {
            return;
        }

        for (UserPreference entity : userPreferenceList) {
            updateByUserIdAndCode(entity);
        }
    }


    @Override
    public void saveBatchDTO(List<UserPreferenceDTO> dto) {
        List<UserPreference> userPreferenceList = userPreferenceConvert.toEntity(dto);
        if (CollectionUtils.isEmpty(userPreferenceList)) {
            return;
        }
        saveBatch(userPreferenceList);
    }

    @Override
    public void updateDTOByUserIdAndCode(UserPreferenceDTO dto) {
        UserPreference entity = userPreferenceConvert.toEntity(dto);

        updateByUserIdAndCode(entity);
    }

    private void updateByUserIdAndCode(UserPreference entity) {
        UpdateWrapper<UserPreference> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(UserPreference::getPreferenceValue, entity.getPreferenceValue())
                .eq(UserPreference::getUserId, entity.getUserId())
                .eq(UserPreference::getPreferenceCode, entity.getPreferenceCode())
        ;
        update(entity, updateWrapper);
    }

    private QueryWrapper<UserPreference> buildQueryWrapper(UserPreferenceCriteria search) {

        if (search == null) {
            return Wrappers.emptyWrapper();
        }

        QueryWrapper<UserPreference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(!StringUtils.isEmpty(search.getUserId()), UserPreference::getUserId, search.getUserId())
                .eq(!StringUtils.isEmpty(search.getPreferenceCode()), UserPreference::getPreferenceCode, search.getPreferenceCode())
        ;
        return queryWrapper;
    }

}