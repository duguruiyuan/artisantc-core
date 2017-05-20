package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentRewardView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “用户的打赏信息”的分页列表内容，Restful接口返回结果的封装，是对{@link ArtMomentRewardView}的再一次封装。
 * Created by xinjie.li on 2017/2/6.
 *
 * @author xinjie.li
 * @since 2.3
 */
public class ArtMomentRewardViewPaginationList extends PaginationList {

    @JsonProperty(value = "userRewardOrders")
    private List<ArtMomentRewardView> artMomentRewardViews = new ArrayList<>();// “用户的打赏信息”列表

    public List<ArtMomentRewardView> getArtMomentRewardViews() {
        return artMomentRewardViews;
    }

    public void setArtMomentRewardViews(List<ArtMomentRewardView> artMomentRewardViews) {
        this.artMomentRewardViews = artMomentRewardViews;
    }
}
