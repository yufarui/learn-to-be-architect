package indi.biz.yufr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import indi.biz.yufr.entity.PreferencePrototype;
import indi.biz.yufr.service.dto.PreferencePrototypeDTO;
import indi.biz.yufr.service.dto.search.PreferencePrototypeCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 偏好设置表 服务类
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
public interface IPreferencePrototypeService extends IService<PreferencePrototype> {

    PreferencePrototypeDTO getDTOById(Serializable id);

    void saveDTO(PreferencePrototypeDTO dto);

    void updateDTOById(PreferencePrototypeDTO dto);

    void removeDTOById(Serializable id);

    List<PreferencePrototypeDTO> getDTOList(PreferencePrototypeCriteria search);

    IPage<PreferencePrototypeDTO> getDTOPage(PreferencePrototypeCriteria search);

    void saveBatchDTO(List<PreferencePrototypeDTO> dto);

    void updateBatchDTOById(List<PreferencePrototypeDTO> dto);

}