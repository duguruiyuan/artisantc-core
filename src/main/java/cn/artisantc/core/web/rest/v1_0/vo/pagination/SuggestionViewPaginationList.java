package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.SuggestionView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “我关注的用户”的分页列表内容，Restful接口返回结果的封装，是对{@link SuggestionView}的再一次封装。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class SuggestionViewPaginationList extends PaginationList {

    @JsonProperty(value = "suggestions")
    private List<SuggestionView> suggestionViews = new ArrayList<>();// “意见反馈”列表

    public List<SuggestionView> getSuggestionViews() {
        return suggestionViews;
    }

    public void setSuggestionViews(List<SuggestionView> suggestionViews) {
        this.suggestionViews = suggestionViews;
    }
}
