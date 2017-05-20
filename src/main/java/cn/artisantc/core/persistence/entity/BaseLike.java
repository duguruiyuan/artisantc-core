package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * “点赞记录”的基类。
 * Created by xinjie.li on 2016/9/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseLike extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Status status;// 点赞的状态

    @Column(name = "is_send_message")
    private boolean sendMessage = false;// 该条点赞是否已经发送过信息给用户，默认为false，表示未发送

    public enum Status {
        /**
         * 点赞
         */
        GIVEN,
        /**
         * 取消点赞
         */
        CANCEL
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(boolean sendMessage) {
        this.sendMessage = sendMessage;
    }
}
