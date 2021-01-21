package ${cfg.base}.service.mapper;

import ${package.Entity}.${entity};
import ${cfg.base}.service.dto.${entity}DTO;
import ${cfg.base}.base.EntityConvert;

import org.mapstruct.*;

/**
* <p>
* ${table.comment!} 转换类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = {})
public interface ${entity}Convert extends EntityConvert<${entity}DTO, ${entity}> {

}