package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 艺文相关的消息。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Entity
@Table(name = "t_art_moment_message", indexes = {@Index(name = "IND_T_ART_MOMENT_MESSAGE_CATEGORY", columnList = "category")})
public class ArtMomentMessage extends BaseMessage {

    @Column(name = "user_id")
    private long userId;// 消息的接受者的用户ID

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
