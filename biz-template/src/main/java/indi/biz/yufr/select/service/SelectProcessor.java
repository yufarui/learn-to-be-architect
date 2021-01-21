package indi.biz.yufr.select.service;


import indi.biz.yufr.select.SelectOptionRequest;
import indi.biz.yufr.select.SelectResponse;

/**
 * @date: 2020/9/22 8:52
 * @author: farui.yu
 */
public interface SelectProcessor {

    SelectResponse<?> process(SelectOptionRequest request);
}
