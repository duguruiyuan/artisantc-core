package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ItemMarginOrderView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “拍品保证金的支付订单”的分页列表内容，Restful接口返回结果的封装，是对{@link ItemMarginOrderView}的再一次封装。
 * Created by xinjie.li on 2016/12/5.
 *
 * @author xinjie.li
 * @since 1.1
 */
public class ItemMarginOrderViewPaginationList extends PaginationList {

    @JsonProperty(value = "itemMarginOrders")
    private List<ItemMarginOrderView> itemMarginOrderViews = new ArrayList<>();

    public List<ItemMarginOrderView> getItemMarginOrderViews() {
        return itemMarginOrderViews;
    }

    public void setItemMarginOrderViews(List<ItemMarginOrderView> itemMarginOrderViews) {
        this.itemMarginOrderViews = itemMarginOrderViews;
    }
}
