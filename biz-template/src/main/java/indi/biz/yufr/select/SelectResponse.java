package indi.biz.yufr.select;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 下拉框返回结果
 *
 * @date: 2020/9/18 13:47
 * @author: farui.yu
 */
@Data
public class SelectResponse<T> {

    /**
     * 主键为 SelectOption中key值
     * map是有顺序,其顺序按照SelectOption的key值正序排序
     */
    private Map<String, SelectOption<T>> selectOptions;

    public T getData(String key) {
        SelectOption<T> selectedOption = getSelectedOption(key);
        if (selectedOption == null) {
            return null;
        }
        return  selectedOption.getData();
    }

    public SelectOption<T> getSelectedOption(String key) {

        if (StringUtils.isEmpty(key)) {
            return null;
        }

        if (CollectionUtils.isEmpty(selectOptions)) {
            return null;
        }

        return selectOptions.get(key);
    }

}
