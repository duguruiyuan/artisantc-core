package cn.artisantc.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * 艺术圈的艺文。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_art_moment", indexes = {@Index(name = "IND_T_ART_MOMENT_CATEGORY", columnList = "category")})
public class ArtMoment extends BaseMoment {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_MOMENT_USER_ID"))
    private User user;// 该艺文的发布者

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artMoment")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<ArtMomentImage> images;// 艺文中的图片

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artMoment")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<ArtMomentComment> comments;// 该艺文的评论内容

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artMoment")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<ArtMomentLike> likes;// 该艺文的点赞

    @ManyToOne
    @JoinColumn(name = "original_art_moment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_MOMENT_ORIGINAL_ART_MOMENT_ID"))
    private ArtMoment originalArtMoment;// 如果该艺文是原创，则该属性为null。如果该艺文是转发，则该属性表示原文

    @ManyToOne
    @JoinColumn(name = "primary_tag_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_MOMENT_PRIMARY_TAG_ID"))
    private Tag primaryTag;// 主要标签

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_art_moment_secondary_tag", uniqueConstraints = {@UniqueConstraint(name = "UK_ART_MOMENT_SECONDARY_TAG_ID", columnNames = {"art_moment_id", "tag_id"})},
            joinColumns = {
                    @JoinColumn(name = "art_moment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_MOMENT_SECONDARY_TAG_ART_MOMENT_ID"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_MOMENT_SECONDARY_TAG_TAG_ID"))
            }
    )
    private List<Tag> secondaryTags;// 次要标签

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ArtMomentImage> getImages() {
        return images;
    }

    public void setImages(List<ArtMomentImage> images) {
        this.images = images;
    }

    public List<ArtMomentComment> getComments() {
        return comments;
    }

    public void setComments(List<ArtMomentComment> comments) {
        this.comments = comments;
    }

    public List<ArtMomentLike> getLikes() {
        return likes;
    }

    public void setLikes(List<ArtMomentLike> likes) {
        this.likes = likes;
    }

    public ArtMoment getOriginalArtMoment() {
        return originalArtMoment;
    }

    public void setOriginalArtMoment(ArtMoment originalArtMoment) {
        this.originalArtMoment = originalArtMoment;
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
