package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ItemPaymentOrderView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “拍品支付订单”的分页列表内容，Restful接口返回结果的封装，是对{@link ItemPaymentOrderView}的再一次封装。
 * Created by xinjie.li on 2016/12/2.
 *
 * @author xinjie.li
 * @since 1.1
 */
public class ItemPaymentOrderViewPaginationList extends PaginationList {

    @JsonProperty(value = "itemPaymentOrders")
    private List<ItemPaymentOrderView> itemPaymentOrderViews = new ArrayList<>();// “拍品支付订单”的列表

    public List<ItemPaymentOrderView> getItemPaymentOrderViews() {
        return itemPaymentOrderViews;
    }

    public void setItemPaymentOrderViews(List<ItemPaymentOrderView> itemPaymentOrderViews) {
        this.itemPaymentOrderViews = itemPaymentOrderViews;
    }
}
