package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * “艺文评论的点赞”进行点赞操作后的视图对象。
 * Created by xinjie.li on 2017/2/15.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class ArtMomentCommentGiveLikeSummaryView extends BaseGiveLikeSummaryView {

    @JsonProperty(value = "likes")
    private List<ArtMomentCommentLikeView> artMomentCommentLikeViews;// 点赞详情

    public List<ArtMomentCommentLikeView> getArtMomentCommentLikeViews() {
        return artMomentCommentLikeViews;
    }

    public void setArtMomentCommentLikeViews(List<ArtMomentCommentLikeView> artMomentCommentLikeViews) {
        this.artMomentCommentLikeViews = artMomentCommentLikeViews;
    }
}
