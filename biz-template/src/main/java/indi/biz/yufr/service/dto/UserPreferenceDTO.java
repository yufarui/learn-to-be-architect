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
 * 用户与偏好的关联表
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode
@ApiModel(value="UserPreference数据对象", description="用户与偏好的关联表")
public class UserPreferenceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String createdBy;

    private LocalDateTime createdTime;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedTime;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String preferenceCode;

    @ApiModelProperty(value = "偏好")
    private PreferencePrototypeDTO preferencePrototypeDTO;

    @NotEmpty
    @ApiModelProperty(value = "用户设置的偏好值")
    private String preferenceValue;

}
