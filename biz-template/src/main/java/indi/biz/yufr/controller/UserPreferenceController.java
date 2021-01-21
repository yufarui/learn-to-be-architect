package indi.biz.yufr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import indi.biz.yufr.base.ServiceErrorType;
import indi.biz.yufr.base.ServiceException;
import indi.biz.yufr.service.IUserPreferenceService;
import indi.biz.yufr.service.dto.UserPreferenceDTO;
import indi.biz.yufr.service.dto.search.UserPreferenceCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户与偏好的关联表 前端控制器
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/user-preference")
public class UserPreferenceController {

    @Autowired
    private IUserPreferenceService iUserPreferenceService;
    @Autowired
    private SpringValidatorAdapter springValidatorAdapter;

    @GetMapping("/{id}")
    public ResponseEntity<UserPreferenceDTO> getUserPreference(@PathVariable Serializable id) {

        UserPreferenceDTO dto = iUserPreferenceService.getDTOById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createUserPreference(@Valid @RequestBody UserPreferenceDTO dto) {

        if (dto.getId() != null) {
            throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键存在,无法创建数据");
        }

        iUserPreferenceService.saveDTO(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUserPreference(@Valid @RequestBody UserPreferenceDTO dto) {

        if (dto.getId() == null) {
            throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键不存在,无法更新数据");
        }
        iUserPreferenceService.updateDTOById(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserPreference(@PathVariable Serializable id) {
        iUserPreferenceService.removeDTOById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserPreferenceDTO>> getUserPreferenceList(UserPreferenceCriteria search) {

        List<UserPreferenceDTO> dtoList = iUserPreferenceService.getDTOList(search);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/page")
    public ResponseEntity<IPage<UserPreferenceDTO>> getUserPreferencePage(UserPreferenceCriteria search) {

        IPage<UserPreferenceDTO> dtoPage = iUserPreferenceService.getDTOPage(search);
        return ResponseEntity.ok().body(dtoPage);
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> createUserPreferenceBatch(@Valid @RequestBody List<UserPreferenceDTO> dtos) throws BindException {

        if (!CollectionUtils.isEmpty(dtos)) {
            for (UserPreferenceDTO dto : dtos) {

                if (dto.getId() == null) {
                    throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键不存在,无法更新数据");
                }

                BindException bindException = new BindException(dto, "userPreference");
                springValidatorAdapter.validate(dto, bindException);
                throw bindException;
            }
        }

        iUserPreferenceService.saveBatchDTO(dtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/batch")
    public ResponseEntity<Void> updateBatchByUserIdAndCode(@Valid @RequestBody List<UserPreferenceDTO> dtos) throws BindException {

        if (!CollectionUtils.isEmpty(dtos)) {
            for (UserPreferenceDTO dto : dtos) {
                BindException bindException = new BindException(dto, "userPreference");
                springValidatorAdapter.validate(dto, bindException);
                throw bindException;
            }
        }

        iUserPreferenceService.updateBatchDTOByUserIdAndCode(dtos);
        return ResponseEntity.ok().build();
    }

}