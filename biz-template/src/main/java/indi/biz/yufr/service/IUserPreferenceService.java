package indi.biz.yufr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import indi.biz.yufr.entity.UserPreference;
import indi.biz.yufr.service.dto.UserPreferenceDTO;
import indi.biz.yufr.service.dto.search.UserPreferenceCriteria;

import java.io.Serializable;
import java.util.List;

/**
* <p>
* 用户与偏好的关联表 服务类
* </p>
*
* @author yufr
* @since 2021-01-06
*/
public interface IUserPreferenceService extends IService<UserPreference> {

    UserPreferenceDTO getDTOById(Serializable id);

    void saveDTO(UserPreferenceDTO dto);

    void updateDTOById(UserPreferenceDTO dto);

    void removeDTOById(Serializable id);

    List<UserPreferenceDTO> getDTOList(UserPreferenceCriteria search);

    IPage<UserPreferenceDTO> getDTOPage(UserPreferenceCriteria search);

    void updateBatchDTOByUserIdAndCode(List<UserPreferenceDTO> dto);

    void saveBatchDTO(List<UserPreferenceDTO> dto);

    void updateDTOByUserIdAndCode(UserPreferenceDTO dto);

}