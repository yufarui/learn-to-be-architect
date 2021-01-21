package indi.biz.yufr.service.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class PreferencePrototypeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String displayName;
}