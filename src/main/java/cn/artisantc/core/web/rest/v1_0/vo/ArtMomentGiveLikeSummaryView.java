package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * “艺文(艺术圈)的点赞”进行点赞操作后的视图对象。
 * Created by xinjie.li on 2016/9/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentGiveLikeSummaryView extends BaseGiveLikeSummaryView {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "likes")
    private List<ArtMomentLikeView> artMomentLikeViews;// 点赞详情

    public List<ArtMomentLikeView> getArtMomentLikeViews() {
        return artMomentLikeViews;
    }

    public void setArtMomentLikeViews(List<ArtMomentLikeView> artMomentLikeViews) {
        this.artMomentLikeViews = artMomentLikeViews;
    }
}
