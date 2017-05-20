package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginOrderView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “商家保证金的支付订单”的分页列表内容，Restful接口返回结果的封装，是对{@link MerchantMarginOrderView}的再一次封装。
 * Created by xinjie.li on 2016/12/6.
 *
 * @author xinjie.li
 * @since 1.1
 */
public class MerchantMarginOrderViewPaginationList extends PaginationList {

    @JsonProperty(value = "merchantMarginOrders")
    private List<MerchantMarginOrderView> merchantMarginOrderViews = new ArrayList<>();

    public List<MerchantMarginOrderView> getMerchantMarginOrderViews() {
        return merchantMarginOrderViews;
    }

    public void setMerchantMarginOrderViews(List<MerchantMarginOrderView> merchantMarginOrderViews) {
        this.merchantMarginOrderViews = merchantMarginOrderViews;
    }
}
