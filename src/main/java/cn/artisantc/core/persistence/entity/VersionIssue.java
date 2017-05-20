package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “版本所包含的任务内容”。
 * Created by xinjie.li on 2016/12/26.
 *
 * @author xinjie.li
 * @since 2.0
 */
@Entity
@Table(name = "t_version_issue")
public class VersionIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;// 内部使用的逻辑主键

    @Column(length = 100)
    private String content;// 内容

    @ManyToOne
    @JoinColumn(name = "version_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_VERSION_CONTENT_VERSION_ID"))
    private Version version;// 对应哪个版本

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private VersionContentCategory category;// 内容的类型

    @Column(length = 200)
    private String link;// 任务跟踪的URL地址

    /**
     * “内容的类型”枚举。
     */
    public enum VersionContentCategory {
        /**
         * Bug。
         */
        BUG("text.label.version.content.category.bug"),
        /**
         * 改善。
         */
        IMPROVEMENT("text.label.version.content.category.improvement"),
        /**
         * 任务。
         */
        ISSUE("text.label.version.content.category.issue"),
        /**
         * 新特性。
         */
        FEATURE("text.label.version.content.category.feature");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        VersionContentCategory(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public VersionContentCategory getCategory() {
        return category;
    }

    public void setCategory(VersionContentCategory category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
