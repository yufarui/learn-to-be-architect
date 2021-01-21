package indi.biz.yufr.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 终止时间必须大于起始时间 <br/>
 * 注解用于endDate上
 *
 * @date: 2020/10/26 10:12
 * @author: farui.yu
 */
@Target({TYPE})
@Retention(RUNTIME)
@Repeatable(StartAndEndDate.List.class)
@Documented
@Constraint(validatedBy = {StartAndEndDateValidator.class})
@Inherited
public @interface StartAndEndDate {

    String message() default "{com.kingstar.exposure.validator.StartAndEndDate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE})
    @Retention(RUNTIME)
    @Documented
    @Inherited
    @interface List {

        StartAndEndDate[] value();
    }
}
