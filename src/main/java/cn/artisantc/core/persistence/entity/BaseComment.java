package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 艺文“评论”的基类。
 * Created by xinjie.li on 2016/9/13.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseComment extends BaseEntity {

    @Column(length = 400)
    private String comment;// 评论内容

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
