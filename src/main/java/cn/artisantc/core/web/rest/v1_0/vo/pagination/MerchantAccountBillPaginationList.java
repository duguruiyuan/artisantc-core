package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MerchantAccountBillView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “商家账户的账单”的分页列表内容，Restful接口返回结果的封装，是对{@link MerchantAccountBillView}的再一次封装。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantAccountBillPaginationList extends PaginationList {

    @JsonProperty(value = "bills")
    private List<MerchantAccountBillView> billViews = new ArrayList<>();

    public List<MerchantAccountBillView> getBillViews() {
        return billViews;
    }

    public void setBillViews(List<MerchantAccountBillView> billViews) {
        this.billViews = billViews;
    }
}
