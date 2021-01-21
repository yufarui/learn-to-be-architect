package indi.biz.yufr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.biz.yufr.base.PageUtils;
import indi.biz.yufr.entity.PreferencePrototype;
import indi.biz.yufr.mapper.PreferencePrototypeMapper;
import indi.biz.yufr.service.IPreferencePrototypeService;
import indi.biz.yufr.service.dto.PreferencePrototypeDTO;
import indi.biz.yufr.service.dto.search.PreferencePrototypeCriteria;
import indi.biz.yufr.service.mapper.PreferencePrototypeConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 偏好设置表 服务实现类
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Service
@Slf4j
public class PreferencePrototypeServiceImpl extends ServiceImpl<PreferencePrototypeMapper, PreferencePrototype> implements IPreferencePrototypeService {

    @Autowired
    private PreferencePrototypeConvert preferencePrototypeConvert;
    @Autowired
    private PageUtils pageUtils;

    @Override
    public PreferencePrototypeDTO getDTOById(Serializable id) {
        return preferencePrototypeConvert
                .toDto(getById(id));
    }

    @Override
    public void saveDTO(PreferencePrototypeDTO dto) {
        PreferencePrototype entity = preferencePrototypeConvert.toEntity(dto);
        save(entity);
    }

    @Override
    public void updateDTOById(PreferencePrototypeDTO preferencePrototypeDTO) {
        PreferencePrototype entity = preferencePrototypeConvert.toEntity(preferencePrototypeDTO);
        updateById(entity);
    }

    @Override
    public void removeDTOById(Serializable id) {
        removeById(id);
    }

    @Override
    public List<PreferencePrototypeDTO> getDTOList(PreferencePrototypeCriteria search) {
        List<PreferencePrototype> list = list(buildQueryWrapper(search));
        return preferencePrototypeConvert.toDto(list);
    }

    @Override
    public IPage<PreferencePrototypeDTO> getDTOPage(PreferencePrototypeCriteria search) {

        IPage<PreferencePrototype> page = page(pageUtils.page(), buildQueryWrapper(search));

        return page.convert(preferencePrototypeConvert::toDto);
    }

    @Override
    public void saveBatchDTO(List<PreferencePrototypeDTO> dto) {
        List<PreferencePrototype> entityList = preferencePrototypeConvert.toEntity(dto);
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        saveBatch(entityList);
    }

    @Override
    public void updateBatchDTOById(List<PreferencePrototypeDTO> dto) {
        List<PreferencePrototype> entityList = preferencePrototypeConvert.toEntity(dto);
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        updateBatchById(entityList);
    }

    private QueryWrapper<PreferencePrototype> buildQueryWrapper(PreferencePrototypeCriteria search) {
        if (search == null) {
            return Wrappers.emptyWrapper();
        }

        QueryWrapper<PreferencePrototype> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(!StringUtils.isEmpty(search.getCode()), PreferencePrototype::getCode, search.getCode())
                .like(!StringUtils.isEmpty(search.getDisplayName()), PreferencePrototype::getDisplayName, search.getDisplayName())
        ;
        return queryWrapper;
    }

}