package indi.biz.yufr.validator;

import java.util.Date;

/**
 * @date: 2020/10/26 11:11
 * @author: farui.yu
 */
@StartAndEndDate
public interface StartAndEndSearch {

    Date getStartDate();

    Date getEndDate();
}
