package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “意见反馈”的视图对象。
 * Created by xinjie.li on 2016/11/25.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class SuggestionView {

    @JsonProperty(value = "suggestionId")
    private String id;// 意见反馈的ID

    private String content;// 意见反馈的内容

    private String createDateTime;// 意见反馈的提交时间

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}
