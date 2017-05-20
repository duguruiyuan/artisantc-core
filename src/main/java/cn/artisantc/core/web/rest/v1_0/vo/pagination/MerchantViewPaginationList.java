package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MerchantView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “商家”的分页列表内容，Restful接口返回结果的封装，是对{@link MerchantView}的再一次封装。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantViewPaginationList extends PaginationList {

    @JsonProperty(value = "merchants")
    private List<MerchantView> merchantViews = new ArrayList<>();

    public List<MerchantView> getMerchantViews() {
        return merchantViews;
    }

    public void setMerchantViews(List<MerchantView> merchantViews) {
        this.merchantViews = merchantViews;
    }
}
