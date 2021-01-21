package indi.biz.yufr.service.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class UserPreferenceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String preferenceCode;

}