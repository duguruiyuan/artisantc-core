package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * “商家认证”的信息。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_merchant")
public class Merchant extends BaseEntity {

    @Column(name = "real_name", length = EntityConstant.REAL_NAME_LENGTH)
    private String realName;// 真实姓名

    @Column(name = "identity_number", length = EntityConstant.IDENTITY_NUMBER_LENGTH)
    private String identityNumber;// 身份证号

    @Column(name = "telephone_number", length = 30)
    private String telephoneNumber;// 固定电话

    @Column(length = 50)
    private String district;// 所在地区

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<MerchantImage> images = new ArrayList<>(3);// 企业商家认证需要提交的图片，固定为3张：身份证正面照，身份证反面照，公司营业执照

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Category category;// 商家的分类

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status;// 商家的状态

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_USER_ID"))
    private User user;// 商家的主人

    /**
     * “商家的分类”。
     */
    public enum Category {
        /**
         * 个人商家
         */
        PERSONAL,
        /**
         * 企业商家
         */
        ENTERPRISE,
        /**
         * 表明仅仅是通过了“实名认证”，既不是“个人商家”也不是“企业商家”
         */
        NONE
    }

    /**
     * “商家的状态”。
     */
    public enum Status {
        /**
         * 审核中。
         */
        PENDING_REVIEW("text.merchant.status.pending.review"),
        /**
         * 已认证。
         */
        APPROVED("text.merchant.status.approved"),
        /**
         * 已拒绝。
         */
        REJECTED("text.merchant.status.rejected"),
        /**
         * 未认证，这是一个中间状态，仅存在于用户通过了“实名认证”后开店后的商家的状态。
         */
        NOT_REVIEW("text.merchant.status.not.review");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

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

    public List<MerchantImage> getImages() {
        return images;
    }

    public void setImages(List<MerchantImage> images) {
        this.images = images;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
