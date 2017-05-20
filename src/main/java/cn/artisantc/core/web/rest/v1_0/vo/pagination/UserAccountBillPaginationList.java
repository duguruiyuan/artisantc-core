package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.UserAccountBillView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “用户的个人账户的账单”的分页列表内容，Restful接口返回结果的封装，是对{@link UserAccountBillView}的再一次封装。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class UserAccountBillPaginationList extends PaginationList {

    @JsonProperty(value = "bills")
    private List<UserAccountBillView> billViews = new ArrayList<>();

    public List<UserAccountBillView> getBillViews() {
        return billViews;
    }

    public void setBillViews(List<UserAccountBillView> billViews) {
        this.billViews = billViews;
    }
}
