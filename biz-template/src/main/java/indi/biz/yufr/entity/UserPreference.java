package indi.biz.yufr.entity;

import indi.biz.yufr.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户与偏好的关联表
 * </p>
 *
 * @author yufr
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPreference extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    /**
     * 偏好的code
     */
    private String preferenceCode;

    /**
     * 用户设置的偏好值
     */
    private String preferenceValue;
}
