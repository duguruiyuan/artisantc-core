package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “实名认证”的图片。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_real_name_image")
public class RealNameImage extends BaseImage {

    @ManyToOne
    @JoinColumn(name = "real_name_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_REAL_NAME_IMAGE_REAL_NAME_ID"))
    private RealName realName;

    public RealName getRealName() {
        return realName;
    }

    public void setRealName(RealName realName) {
        this.realName = realName;
    }
}
