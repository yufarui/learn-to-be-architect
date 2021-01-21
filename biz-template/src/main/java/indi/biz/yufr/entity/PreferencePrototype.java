package indi.biz.yufr.entity;

import indi.biz.yufr.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 偏好设置表
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PreferencePrototype extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 偏好的code,方便以json展示偏好
     */
    private String code;

    /**
     * 偏好的名称
     */
    private String displayName;

    /**
     * 偏好的默认值
     */
    private String defaultValue;
}
