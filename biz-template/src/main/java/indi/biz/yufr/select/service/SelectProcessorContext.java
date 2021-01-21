package indi.biz.yufr.select.service;


import indi.biz.yufr.base.ServiceErrorType;
import indi.biz.yufr.base.ServiceException;
import indi.biz.yufr.config.SpringContext;
import indi.biz.yufr.select.DictionarySelectEnum;
import indi.biz.yufr.select.SelectOption;
import indi.biz.yufr.select.SelectOptionRequest;
import indi.biz.yufr.select.SelectResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * @date: 2020/9/22 8:55
 * @author: farui.yu
 */
public class SelectProcessorContext {

    public static <E> SelectResponse<E> getSelect(SelectOptionRequest request) {
        if (request == null) {
            return null;
        }
        return (SelectResponse<E>) chooseProcessor(request.getType()).process(request);
    }

    public static SelectProcessor chooseProcessor(String type) {

        SelectProcessor defaultSelectProcessor = SpringContext.getBean("defaultSelectProcessor", SelectProcessor.class);

        if (StringUtils.isBlank(type)) {
            return defaultSelectProcessor;
        }

        DictionarySelectEnum dictionarySelectEnum = DictionarySelectEnum.searchType(type);
        if (dictionarySelectEnum != null) {
            return defaultSelectProcessor;
        }

        throw new ServiceException(ServiceErrorType.SYSTEM_ERROR, "没有对应的下拉选项处理器");
    }

    public static <E> SelectResponse<E> handlerSelection(List<E> entityList, Function<E, SelectOption<E>> function) {

        SelectResponse<E> selectResponse = new SelectResponse<>();

        Map<String, SelectOption<E>> selectOptions = new TreeMap<>();
        selectResponse.setSelectOptions(selectOptions);

        if (CollectionUtils.isEmpty(entityList)) {
            return selectResponse;
        }

        entityList.forEach((entity) -> {
            SelectOption<E> selectOption = function.apply(entity);
            selectOptions.put(selectOption.getKey(), selectOption);
        });

        return selectResponse;
    }
}
