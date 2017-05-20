package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “商家认证”的图片。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_merchant_image")
public class MerchantImage extends BaseImage {

    @ManyToOne
    @JoinColumn(name = "enterprise_merchant_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_IMAGE_MERCHANT_ID"))
    private Merchant merchant;// 该图片所属的拍品的ID

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}
