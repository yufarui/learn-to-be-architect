package indi.biz.yufr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import indi.biz.yufr.base.ServiceErrorType;
import indi.biz.yufr.base.ServiceException;
import indi.biz.yufr.service.IPreferencePrototypeService;
import indi.biz.yufr.service.dto.PreferencePrototypeDTO;
import indi.biz.yufr.service.dto.search.PreferencePrototypeCriteria;
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
* 偏好设置表 前端控制器
* </p>
*
* @author yufr
* @since 2021-01-06
*/
@RestController
@RequestMapping("/preference-prototype")
public class PreferencePrototypeController {

    @Autowired
    private IPreferencePrototypeService iPreferencePrototypeService;
    @Autowired
    private SpringValidatorAdapter springValidatorAdapter;

    @GetMapping("/{id}")
    public ResponseEntity<PreferencePrototypeDTO> getPreferencePrototype(@PathVariable Serializable id) {

        PreferencePrototypeDTO dto = iPreferencePrototypeService.getDTOById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createPreferencePrototype(@Valid @RequestBody PreferencePrototypeDTO dto) {

        if (dto.getId() != null) {
            throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键存在,无法创建数据");
        }

        iPreferencePrototypeService.saveDTO(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePreferencePrototype(@Valid @RequestBody PreferencePrototypeDTO dto) {

        if (dto.getId() == null) {
            throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键不存在,无法更新数据");
        }
        iPreferencePrototypeService.updateDTOById(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePreferencePrototype(@PathVariable Serializable id) {
        iPreferencePrototypeService.removeDTOById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<PreferencePrototypeDTO>> getPreferencePrototypeList(PreferencePrototypeCriteria search) {

        List<PreferencePrototypeDTO> dtoList = iPreferencePrototypeService.getDTOList(search);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/page")
    public ResponseEntity<IPage<PreferencePrototypeDTO>> getPreferencePrototypePage(PreferencePrototypeCriteria search) {

        IPage<PreferencePrototypeDTO> dtoPage = iPreferencePrototypeService.getDTOPage(search);
        return ResponseEntity.ok().body(dtoPage);
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> createPreferencePrototypeBatch(@RequestBody List<PreferencePrototypeDTO> dtos) throws BindException {

        if (!CollectionUtils.isEmpty(dtos)) {
            for (PreferencePrototypeDTO dto : dtos) {

                if (dto.getId() == null) {
                    throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键不存在,无法更新数据");
                }

                BindException bindException = new BindException(dto, "preferencePrototype");
                springValidatorAdapter.validate(dto, bindException);
                throw bindException;
            }
        }

        iPreferencePrototypeService.saveBatchDTO(dtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/batch")
    public ResponseEntity<Void> updateBatchByUserIdAndCode(@RequestBody List<PreferencePrototypeDTO> dtos) throws BindException {

        if (!CollectionUtils.isEmpty(dtos)) {
            for (PreferencePrototypeDTO dto : dtos) {
                BindException bindException = new BindException(dto, "preferencePrototype");
                springValidatorAdapter.validate(dto, bindException);
                throw bindException;
            }
        }

        iPreferencePrototypeService.updateBatchDTOById(dtos);
        return ResponseEntity.ok().build();
    }

}