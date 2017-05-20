package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * “消息”的基类。
 * Created by xinjie.li on 2017/1/16.
 *
 * @author xinjie.li
 * @since 2.3
 */
@MappedSuperclass
public class BaseMessage extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(length = 30)
    private Category category;// 消息的类型

    @Column(name = "content_id")
    private long contentId;// 消息内容的ID，具体对应的消息内容的判断逻辑由“Category”和“Type”来决定

    @Column(name = "is_read")
    private boolean isRead = false;// 消息是否已读，默认false，表示“未读”

    /**
     * “消息的类型”枚举。
     */
    public enum Category {
        /**
         * 评论。
         */
        COMMENT,
        /**
         * 评论“评论”。
         */
        COMMENT_ON_COMMENT,
        /**
         * 转发。
         */
        FORWARD,
        /**
         * 点赞。
         */
        GIVE_LIKE,
        /**
         * 点赞“评论”。
         */
        GIVE_LIKE_ON_COMMENT
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
