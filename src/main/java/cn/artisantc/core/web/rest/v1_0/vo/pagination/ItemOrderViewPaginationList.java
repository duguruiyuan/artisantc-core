package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “拍品订单(卖家)”的分页列表内容，Restful接口返回结果的封装，是对{@link ItemOrderView}的再一次封装。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderViewPaginationList extends PaginationList {

    @JsonProperty(value = "itemOrders")
    private List<ItemOrderView> itemOrderViews = new ArrayList<>();

    public List<ItemOrderView> getItemOrderViews() {
        return itemOrderViews;
    }

    public void setItemOrderViews(List<ItemOrderView> itemOrderViews) {
        this.itemOrderViews = itemOrderViews;
    }
}
