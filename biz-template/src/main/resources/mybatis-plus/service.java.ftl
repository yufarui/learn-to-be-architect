package ${package.Service};

import ${package.Entity}.${entity};
import ${cfg.base}.service.dto.${entity}DTO;
import ${superServiceClassPackage};
import ${cfg.base}.service.dto.search.${entity}Criteria;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.io.Serializable;

/**
* <p>
* ${table.comment!} 服务类
* </p>
*
* @author ${author}
* @since ${date}
*/
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    ${entity}DTO getDTOById(Serializable id);

    void saveDTO(${entity}DTO dto);

    void updateDTOById(${entity}DTO dto);

    void removeDTOById(Serializable id);

    List<${entity}DTO> getDTOList(${entity}Criteria search);

    IPage<${entity}DTO> getDTOPage(${entity}Criteria search);
}