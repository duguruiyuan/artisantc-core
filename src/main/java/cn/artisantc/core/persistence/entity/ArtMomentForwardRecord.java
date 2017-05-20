package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “艺文(艺术圈)”的转发记录。
 * Created by Administrator on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_art_moment_forward_record")
public class ArtMomentForwardRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "art_moment_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_FORWARD_RECORD_ART_MOMENT_ID"))
    private ArtMoment artMoment;// 被转发的艺文

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_FORWARD_RECORD_USER_ID"))
    private User user;// 转发的人

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
