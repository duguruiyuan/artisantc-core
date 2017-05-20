package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.InformationView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “资讯”的分页列表内容，Restful接口返回结果的封装，是对{@link InformationView}的再一次封装。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class InformationViewPaginationList extends PaginationList {

    @JsonProperty(value = "information")
    private List<InformationView> informationViews = new ArrayList<>();

    public List<InformationView> getInformationViews() {
        return informationViews;
    }

    public void setInformationViews(List<InformationView> informationViews) {
        this.informationViews = informationViews;
    }
}
