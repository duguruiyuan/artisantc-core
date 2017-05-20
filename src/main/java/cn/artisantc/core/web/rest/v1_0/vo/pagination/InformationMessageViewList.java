package cn.artisantc.core.web.rest.v1_0.vo.pagination;

import cn.artisantc.core.web.rest.v1_0.vo.InformationMessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * “资讯的消息”的列表内容，Restful接口返回结果的封装，是对{@link InformationMessageView}的再一次封装。
 * Created by xinjie.li on 2017/1/18.
 *
 * @author xinjie.li
 * @since 2.3
 */
public class InformationMessageViewList extends BaseMessageViewList {

    private List<InformationMessageView> messages = new ArrayList<>();

    public List<InformationMessageView> getMessages() {
        return messages;
    }

    public void setMessages(List<InformationMessageView> messages) {
        this.messages = messages;
    }

    @Override
    public String getCount() {
        return String.valueOf(messages.size());
    }
}
