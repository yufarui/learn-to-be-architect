package indi.biz.yufr.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 偏好设置表
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode
@ApiModel(value="PreferencePrototype数据对象", description="偏好设置表")
public class PreferencePrototypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String createdBy;

    private LocalDateTime createdTime;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedTime;

    @NotEmpty
    @ApiModelProperty(value = "偏好的code,方便以json展示偏好")
    private String code;

    @NotEmpty
    @ApiModelProperty(value = "偏好的名称")
    private String displayName;

    @ApiModelProperty(value = "偏好的默认值")
    private String defaultValue;

}
