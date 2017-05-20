package cn.artisantc.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * “资讯”。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_information")
public class Information extends BaseEntity {

    @Column(length = 200)
    private String title;// 资讯的标题

    @Lob
    @Column(columnDefinition = "mediumtext")
    private String content;// 资讯的内容

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "information")
    private InformationImage coverImage;// 资讯的封面图片

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INFORMATION_USER_ID"))
    private User user;// 资讯的作者

    @Column(length = 40)
    private String source;// 资讯的来源，如果是来自于外部网站，例如：中国书法网

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Information.Status status = Status.DRAFT;// 资讯状态

    @ManyToOne
    @JoinColumn(name = "primary_tag_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INFORMATION_PRIMARY_TAG_ID"))
    private Tag primaryTag;// 主要标签

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_information_secondary_tag", uniqueConstraints = {@UniqueConstraint(name = "UK_INFORMATION_SECONDARY_TAG_ID", columnNames = {"information_id", "tag_id"})},
            joinColumns = {
                    @JoinColumn(name = "information_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INFORMATION_SECONDARY_TAG_INFORMATION_ID"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INFORMATION_SECONDARY_TAG_TAG_ID"))
            }
    )
    private List<Tag> secondaryTags;// 次要标签

    /**
     * “资讯状态”枚举。
     */
    public enum Status {
        /**
         * 草稿。
         */
        DRAFT("text.information.status.draft"),
        /**
         * 已发布。
         */
        PUBLISHED("text.information.status.published");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public InformationImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(InformationImage coverImage) {
        this.coverImage = coverImage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Tag getPrimaryTag() {
        return primaryTag;
    }

    public void setPrimaryTag(Tag primaryTag) {
        this.primaryTag = primaryTag;
    }

    public List<Tag> getSecondaryTags() {
        return secondaryTags;
    }

    public void setSecondaryTags(List<Tag> secondaryTags) {
        this.secondaryTags = secondaryTags;
    }
}
