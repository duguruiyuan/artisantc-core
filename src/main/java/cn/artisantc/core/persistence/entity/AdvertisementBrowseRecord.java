package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “广告”的浏览记录。
 * Created by xinjie.li on 2016/11/15.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_advertisement_browse_record")
public class AdvertisementBrowseRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ADVERTISEMENT_BROWSE_RECORD_ADVERTISEMENT_ID"))
    private Advertisement advertisement;// 被浏览的广告

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ADVERTISEMENT_BROWSE_RECORD_USER_ID"))
    private User user;// 浏览的人

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
