package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.AdvertisementView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “广告”的分页列表内容，Restful接口返回结果的封装，是对{@link AdvertisementView}的再一次封装。
 * Created by xinjie.li on 2016/11/15.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AdvertisementViewPaginationList extends PaginationList {

    @JsonProperty(value = "advertisements")
    private List<AdvertisementView> advertisementViews = new ArrayList<>();

    public List<AdvertisementView> getAdvertisementViews() {
        return advertisementViews;
    }

    public void setAdvertisementViews(List<AdvertisementView> advertisementViews) {
        this.advertisementViews = advertisementViews;
    }
}
