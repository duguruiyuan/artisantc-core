package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “商家认证的审核记录”。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_merchant_review_record")
public class MerchantReviewRecord extends BaseEntity {

    @Column(name = "real_name", length = 30)
    private String realName;// 真实姓名

    @Column(name = "identity_number", length = 30)
    private String identityNumber;// 身份证号

    @Column(name = "telephone_number", length = 30)
    private String telephoneNumber;// 固定电话

    @Column(length = 50)
    private String district;// 所在地区

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Merchant.Category category;// 商家的分类

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Merchant.Status status;// 商家的状态

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_REVIEW_RECORD_USER_ID"))
    private User user;// 商家的主人

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_REVIEW_RECORD_REVIEWER_ID"))
    private Administrator reviewer;// 审核人

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Merchant.Category getCategory() {
        return category;
    }

    public void setCategory(Merchant.Category category) {
        this.category = category;
    }

    public Merchant.Status getStatus() {
        return status;
    }

    public void setStatus(Merchant.Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Administrator getReviewer() {
        return reviewer;
    }

    public void setReviewer(Administrator reviewer) {
        this.reviewer = reviewer;
    }
}
