package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ItemView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “拍品”的分页列表内容，Restful接口返回结果的封装，是对{@link ItemView}的再一次封装。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemViewPaginationList extends PaginationList {

    @JsonProperty(value = "items")
    private List<ItemView> itemViews = new ArrayList<>();

    public List<ItemView> getItemViews() {
        return itemViews;
    }

    public void setItemViews(List<ItemView> itemViews) {
        this.itemViews = itemViews;
    }
}
