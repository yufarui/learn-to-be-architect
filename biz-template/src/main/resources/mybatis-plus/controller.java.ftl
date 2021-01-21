package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${cfg.base}.service.dto.${entity}DTO;
import ${cfg.base}.service.dto.search.${entity}Criteria;
import ${cfg.base}.base.ServiceErrorType;
import ${cfg.base}.base.ServiceException;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
* <p>
* ${table.comment} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
@RestController
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
public class ${table.controllerName} {

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @GetMapping("/{id}")
    public ResponseEntity<${entity}DTO> get${entity}(@PathVariable Serializable id) {

        ${entity}DTO dto = ${table.serviceName?uncap_first}.getDTOById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Void> create${entity}(@Valid @RequestBody ${entity}DTO dto) {

        if (dto.getId() != null) {
            throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键存在,无法创建数据");
        }

        ${table.serviceName?uncap_first}.saveDTO(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update${entity}(@Valid @RequestBody ${entity}DTO dto) {

        if (dto.getId() == null) {
            throw new ServiceException(ServiceErrorType.BAD_REQUEST, "主键不存在,无法更新数据");
        }
        ${table.serviceName?uncap_first}.updateDTOById(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove${entity}(@PathVariable Serializable id) {
        ${table.serviceName?uncap_first}.removeDTOById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<${entity}DTO>> get${entity}List(${entity}Criteria search) {

        List<${entity}DTO> dtoList = ${table.serviceName?uncap_first}.getDTOList(search);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/page")
    public ResponseEntity<IPage<${entity}DTO>> get${entity}Page(${entity}Criteria search) {

        IPage<${entity}DTO> dtoPage = ${table.serviceName?uncap_first}.getDTOPage(search);
        return ResponseEntity.ok().body(dtoPage);
    }

}