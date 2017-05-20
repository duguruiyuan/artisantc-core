package cn.artisantc.core.service.push.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “JPush”响应的封装。
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class JPushResponse {

    @JsonProperty(value = "msg_id")
    private long msgId;

    private JPushResponseError error;

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public JPushResponseError getError() {
        return error;
    }

    public void setError(JPushResponseError error) {
        this.error = error;
    }
}
