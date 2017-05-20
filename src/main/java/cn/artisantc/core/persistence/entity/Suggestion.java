package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户提出的“意见反馈”信息。
 * Created by xinjie.li on 2016/11/3.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_suggestion")
public class Suggestion extends BaseEntity {

    @Column(length = 100)
    private String content;// 内容

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
