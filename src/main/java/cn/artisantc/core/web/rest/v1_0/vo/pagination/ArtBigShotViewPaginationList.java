package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ArtBigShotView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “艺术大咖”的分页列表内容，Restful接口返回结果的封装，是对{@link ArtBigShotView}的再一次封装。
 * Created by xinjie.li on 2017/1/4.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class ArtBigShotViewPaginationList extends PaginationList {

    @JsonProperty(value = "artBigShots")
    private List<ArtBigShotView> artBigShotViews = new ArrayList<>();

    public List<ArtBigShotView> getArtBigShotViews() {
        return artBigShotViews;
    }

    public void setArtBigShotViews(List<ArtBigShotView> artBigShotViews) {
        this.artBigShotViews = artBigShotViews;
    }
}
