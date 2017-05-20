package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.RealNameView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “商家的保证金场”的分页列表内容，Restful接口返回结果的封装，是对{@link RealNameView}的再一次封装。
 * Created by xinjie.li on 2016/10/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RealNameViewPaginationList extends PaginationList {

    @JsonProperty(value = "realNames")
    private List<RealNameView> realNameViews = new ArrayList<>();

    public List<RealNameView> getRealNameViews() {
        return realNameViews;
    }

    public void setRealNameViews(List<RealNameView> realNameViews) {
        this.realNameViews = realNameViews;
    }
}
