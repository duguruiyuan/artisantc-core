package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “商家的保证金场”的分页列表内容，Restful接口返回结果的封装，是对{@link MerchantMarginView}的再一次封装。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantMarginViewPaginationList extends PaginationList {

    private String margin = "0";// 当前已经缴纳的保证金额度

    @JsonProperty(value = "merchantMargins")
    private List<MerchantMarginView> merchantMarginViews = new ArrayList<>();

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public List<MerchantMarginView> getMerchantMarginViews() {
        return merchantMarginViews;
    }

    public void setMerchantMarginViews(List<MerchantMarginView> merchantMarginViews) {
        this.merchantMarginViews = merchantMarginViews;
    }
}
