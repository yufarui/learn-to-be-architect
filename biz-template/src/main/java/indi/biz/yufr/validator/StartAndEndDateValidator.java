package indi.biz.yufr.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @date: 2020/10/26 10:22
 * @author: farui.yu
 */
public class StartAndEndDateValidator implements ConstraintValidator<StartAndEndDate, StartAndEndSearch> {

    @Override
    public boolean isValid(StartAndEndSearch search, ConstraintValidatorContext context) {

        Date start = search.getStartDate();
        Date end = search.getEndDate();

        if (start == null || end == null) {
            return true;
        }

        return start.getTime() < end.getTime();
    }
}
