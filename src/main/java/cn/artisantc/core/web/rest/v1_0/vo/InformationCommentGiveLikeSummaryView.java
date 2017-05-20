package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * “资讯评论的点赞”进行点赞操作后的视图对象。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class InformationCommentGiveLikeSummaryView extends BaseGiveLikeSummaryView {

    @JsonProperty(value = "likes")
    private List<InformationCommentLikeView> informationCommentLikeViews;// 点赞详情

    public List<InformationCommentLikeView> getInformationCommentLikeViews() {
        return informationCommentLikeViews;
    }

    public void setInformationCommentLikeViews(List<InformationCommentLikeView> informationCommentLikeViews) {
        this.informationCommentLikeViews = informationCommentLikeViews;
    }
}
