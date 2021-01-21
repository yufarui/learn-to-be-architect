package indi.biz.yufr.select;

import org.apache.commons.lang3.StringUtils;

/**
 * 下拉框的字典项类型
 *
 * @date: 2020/9/21 18:56
 * @author: farui.yu
 */
public enum DictionarySelectEnum {

    PREFERENCE_PROP("preference_prop", "偏好属性"),
    ATTRIBUTE_DEF("attribute_def", "属性定义"),
    ;

    private String type;
    private String desc;

    DictionarySelectEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static DictionarySelectEnum searchType(String type) {

        if (StringUtils.isBlank(type)) {
            return null;
        }

        DictionarySelectEnum[] values = DictionarySelectEnum.values();
        for (int i = 0; i < values.length; i++) {
            DictionarySelectEnum value = values[i];
            if (value.type.equals(type)) {
                return value;
            }
        }

        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
