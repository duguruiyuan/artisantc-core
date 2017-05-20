package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “艺术圈的艺文”的分页列表内容，Restful接口返回结果的封装，是对{@link ArtMomentView}的再一次封装。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentViewPaginationList extends PaginationList {

    @JsonProperty(value = "moments")
    private List<ArtMomentView> artMomentViews = new ArrayList<>();// “艺术圈的艺文”的列表

    public List<ArtMomentView> getArtMomentViews() {
        return artMomentViews;
    }

    public void setArtMomentViews(List<ArtMomentView> artMomentViews) {
        this.artMomentViews = artMomentViews;
    }
}
