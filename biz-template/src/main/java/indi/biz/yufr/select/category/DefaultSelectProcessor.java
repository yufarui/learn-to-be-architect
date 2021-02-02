package indi.biz.yufr.select.category;

import indi.biz.yufr.entity.PreferencePrototype;
import indi.biz.yufr.select.DictionarySelectEnum;
import indi.biz.yufr.select.SelectOption;
import indi.biz.yufr.select.SelectOptionRequest;
import indi.biz.yufr.select.SelectResponse;
import indi.biz.yufr.select.service.SelectProcessor;
import indi.biz.yufr.select.service.SelectProcessorContext;
import indi.biz.yufr.service.IPreferencePrototypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @date: 2021/1/7 10:14
 * @author: farui.yu
 */
@Component
public class DefaultSelectProcessor implements SelectProcessor {

    @Autowired
    private IPreferencePrototypeService preferencePrototypeService;

    @Override
    public SelectResponse<?> process(SelectOptionRequest request) {
        DictionarySelectEnum selectEnum = DictionarySelectEnum.searchType(request.getType());
        switch (Objects.requireNonNull(selectEnum)) {
            case PREFERENCE_PROP:
                return handlerPreferenceProp();
        }

        return null;
    }

    private SelectResponse<PreferencePrototype> handlerPreferenceProp() {

        List<PreferencePrototype> entityList = preferencePrototypeService.list();

        return SelectProcessorContext.handlerSelection(entityList, this::createSelectOption);
    }

    private SelectOption<PreferencePrototype> createSelectOption(PreferencePrototype entity) {

        SelectOption<PreferencePrototype> selectOption = new SelectOption<>();
        selectOption.setKey(entity.getCode());
        selectOption.setValue(entity.getDisplayName());
        selectOption.setData(entity);

        return selectOption;
    }

}
