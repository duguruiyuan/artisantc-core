package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “广告的图片”。
 * Created by xinjie.li on 2016/11/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_advertisement_image")
public class AdvertisementImage extends BaseImage {

    @ManyToOne
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ADVERTISEMENT_IMAGE_ADVERTISEMENT_ID"))
    private Advertisement advertisement;// 该图片所属的艺文的ID

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
