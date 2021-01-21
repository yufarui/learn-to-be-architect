package indi.biz.yufr.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class PageUtils {
    // 页面
    private static final String PAGE_STR = "page";
    private static final Integer DEFAULT_PAGE = 1;
    // 页面数据数量
    private static final String SIZE_STR = "size";
    private static final Integer DEFAULT_SIZE = 10;
    // 排序 是否正序排列，默认 true
    private static final String SORT_STR = "sort";
    private static final Boolean DEFAULT_SORT = true;
    private static final String SORT_NAME_STR = "sortName";

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取分页数据
     */
    public Page page() {

        Page page = new Page();
        page.setCurrent(getPage());
        page.setSize(getSize());
        List<OrderItem> orderItems = getOrders();
        if (orderItems != null && orderItems.size() > 0) {
            page.setOrders(orderItems);
        }

        return page;
    }

    /**
     * 排序
     */
    private List<OrderItem> getOrders() {

        // 排序字段
        String sortName = getSortName();
        if (StringUtils.isBlank(sortName)) {
            return null;
        }

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(sortName);
        orderItem.setAsc(getSort());

        orderItems.add(orderItem);

        return orderItems;
    }

    /**
     * 获取page参数
     */
    private Integer getPage() {
        String page = request.getParameter(PAGE_STR);
        return StringUtils.isBlank(page) ? DEFAULT_PAGE : Integer.valueOf(page);
    }

    /**
     * 获取size参数
     */
    private Integer getSize() {
        String page = request.getParameter(SIZE_STR);
        return StringUtils.isBlank(page) ? DEFAULT_SIZE : Integer.valueOf(page);
    }

    /**
     * 获取sortName参数
     */
    private String getSortName() {
        return request.getParameter(SORT_NAME_STR);
    }

    /**
     * 获取sort参数 是否正序排列，默认 true
     */
    private boolean getSort() {

        String str = request.getParameter(SORT_STR);

        return StringUtils.isBlank(str) || Boolean.parseBoolean(str);
    }

}
