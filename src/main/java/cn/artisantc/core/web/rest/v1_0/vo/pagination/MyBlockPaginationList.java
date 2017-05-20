package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.MyBlockView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我屏蔽的用户”的分页列表内容，Restful接口返回结果的封装，是对{@link MyBlockView}的再一次封装。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyBlockPaginationList extends PaginationList {

    @JsonProperty(value = "blocks")
    private List<MyBlockView> blockResponses = new ArrayList<>();// “我关注的用户”列表

    public List<MyBlockView> getBlockResponses() {
        return blockResponses;
    }

    public void setBlockResponses(List<MyBlockView> blockResponses) {
        this.blockResponses = blockResponses;
    }
}
