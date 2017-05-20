package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * “用户的二维码”。
 * Created by xinjie.li on 2016/12/19.
 *
 * @author xinjie.li
 * @since 1.2
 */
@Entity
@Table(name = "t_user_qr_code")
public class UserQRCode extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_QR_CODE_USER_ID"))
    private User user;

    @Lob
    @Column(name = "qr_code_base64", columnDefinition = "mediumtext")
    private String qrCodeBase64;// 二维码的Base64编码

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }
}
