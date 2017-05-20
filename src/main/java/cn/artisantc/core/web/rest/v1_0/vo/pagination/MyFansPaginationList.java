package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MyFansView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我的粉丝”的分页列表内容，Restful接口返回结果的封装，是对{@link MyFansView}的再一次封装。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFansPaginationList extends PaginationList {

    @JsonProperty(value = "fans")
    private List<MyFansView> fansResponses = new ArrayList<>();// 我的粉丝列表

    public List<MyFansView> getFansResponses() {
        return fansResponses;
    }

    public void setFansResponses(List<MyFansView> fansResponses) {
        this.fansResponses = fansResponses;
    }
}
