package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentMessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * “艺文的消息”的列表内容，Restful接口返回结果的封装，是对{@link ArtMomentMessageView}的再一次封装。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
public class ArtMomentMessageViewList extends BaseMessageViewList {

    private List<ArtMomentMessageView> messages = new ArrayList<>();

    public List<ArtMomentMessageView> getMessages() {
        return messages;
    }

    public void setMessages(List<ArtMomentMessageView> messages) {
        this.messages = messages;
    }

    @Override
    public String getCount() {
        return String.valueOf(messages.size());
    }
}
