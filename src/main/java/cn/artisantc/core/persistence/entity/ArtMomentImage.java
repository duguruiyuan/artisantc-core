package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “艺术圈的艺文”的图片。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_art_moment_image")
public class ArtMomentImage extends BaseImage {

    @ManyToOne
    @JoinColumn(name = "art_moment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_MOMENT_IMAGE_ART_MOMENT_ID"))
    private ArtMoment artMoment;// 该图片所属的艺文的ID

    public ArtMoment getArtMoment() {
        return artMoment;
    }

    public void setArtMoment(ArtMoment artMoment) {
        this.artMoment = artMoment;
    }
}
