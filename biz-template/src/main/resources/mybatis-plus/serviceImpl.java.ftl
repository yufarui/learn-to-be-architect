package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import ${cfg.base}.service.dto.${entity}DTO;
import ${cfg.base}.service.dto.search.${entity}Criteria;
import ${cfg.base}.service.mapper.${entity}Convert;
import ${cfg.base}.base.PageUtils;
import ${cfg.base}.base.ServiceErrorType;
import ${cfg.base}.base.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.io.Serializable;

/**
* <p>
* ${table.comment!} 服务实现类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    private ${entity}Convert ${entity?uncap_first}Convert;
    @Autowired
    private PageUtils pageUtils;

    @Override
    public ${entity}DTO getDTOById(Serializable id) {
        return ${entity?uncap_first}Convert
                .toDto(getById(id));
    }

    @Override
    public void saveDTO(${entity}DTO dto) {
        ${entity} entity = ${entity?uncap_first}Convert.toEntity(dto);
        boolean result = save(entity);
        if (!result) {
            throw new ServiceException(ServiceErrorType.SYSTEM_ERROR, "创建${entity?uncap_first}失败");
        }
    }

    @Override
    public void updateDTOById(${entity}DTO ${entity?uncap_first}DTO) {
        ${entity} entity = ${entity?uncap_first}Convert.toEntity(${entity?uncap_first}DTO);
        boolean result = updateById(entity);
        if (!result) {
            throw new ServiceException(ServiceErrorType.SYSTEM_ERROR, "更新${entity?uncap_first}失败");
        }
    }

    @Override
    public void removeDTOById(Serializable id) {
        boolean result = removeById(id);
        if (!result) {
            throw new ServiceException(ServiceErrorType.SYSTEM_ERROR, "删除${entity?uncap_first}失败");
        }
    }

    @Override
    public List<${entity}DTO> getDTOList(${entity}Criteria search) {
        List<${entity}> list = list(buildQueryWrapper(search));
        return ${entity?uncap_first}Convert.toDto(list);
    }

    @Override
    public IPage<${entity}DTO> getDTOPage(${entity}Criteria search) {

        IPage<${entity}> page = page(pageUtils.page(), buildQueryWrapper(search));

        return page.convert(${entity?uncap_first}Convert::toDto);
    }

    private QueryWrapper<${entity}> buildQueryWrapper(${entity}Criteria search) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
<#list table.fields as field>
    <#if !entityLombokModel>
        <#if field.propertyType == "Boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
        <#if field.propertyType == "String">
                .eq(!StringUtils.isEmpty(search.${getprefix}${field.capitalName}()), ${entity}::${getprefix}${field.capitalName}, search.${getprefix}${field.capitalName}())
        <#else>
                .eq(search.${getprefix}${search.capitalName}() != null, ${entity}::${getprefix}${field.capitalName}, search.${getprefix}${field.capitalName}())
        </#if>
    <#else>
        <#if field.propertyType == "String">
                .eq(!StringUtils.isEmpty(search.get${field.capitalName}()), ${entity}::get${field.capitalName}, search.get${field.capitalName}())
        <#else>
                .eq(search.get${field.capitalName}() != null, ${entity}::get${field.capitalName}, search.get${field.capitalName}())
        </#if>
    </#if>
</#list>;
        return queryWrapper;
    }

}