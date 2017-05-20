package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “我收藏的艺文”。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_my_favorite_art_moment")
public class MyFavoriteArtMoment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "art_moment_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_MY_FAVORITE_ART_MOMENT_ART_MOMENT_ID"))
    private ArtMoment artMoment;// 被收藏的艺文

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_MY_FAVORITE_ART_MOMENT_USER_ID"))
    private User user;// 收藏的人

    public ArtMoment getArtMoment() {
        return artMoment;
    }

    public void setArtMoment(ArtMoment artMoment) {
        this.artMoment = artMoment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
