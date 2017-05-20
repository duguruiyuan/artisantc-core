package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * “资讯的点赞”进行点赞操作后的视图对象。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class InformationGiveLikeSummaryView extends BaseGiveLikeSummaryView {

    @JsonProperty(value = "likes")
    private List<InformationLikeView> informationLikeViews;// 点赞详情

    public List<InformationLikeView> getInformationLikeViews() {
        return informationLikeViews;
    }

    public void setInformationLikeViews(List<InformationLikeView> informationLikeViews) {
        this.informationLikeViews = informationLikeViews;
    }
}
