package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “艺文(艺术圈)”的举报记录。
 * Created by Administrator on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_art_moment_report_record")
public class ArtMomentReportRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "art_moment_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_REPORT_RECORD_ART_MOMENT_ID"))
    private ArtMoment artMoment;// 被举报的艺文

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ART_MOMENT_REPORT_RECORD_USER_ID"))
    private User user;// 举报者

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
