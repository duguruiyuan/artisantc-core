package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * “实名认证”的信息。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_real_name")
public class RealName extends BaseEntity {

    @Column(name = "real_name", length = EntityConstant.REAL_NAME_LENGTH)
    private String realName;// 真实姓名

    @Column(name = "identity_number", length = EntityConstant.IDENTITY_NUMBER_LENGTH)
    private String identityNumber;// 身份证号

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "realName")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<RealNameImage> images = new ArrayList<>(3);// 实名认证需要提交的图片，固定为3张：身份证正面照，身份证反面照，手执身份证合照

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status;// 实名认证的状态

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_REAL_NAME_USER_ID"))
    private User user;// 实名认证的提交者

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_REAL_NAME__REVIEWER_ID"))
    private Administrator reviewer;// 审核人

    /**
     * “商家的状态”。
     */
    public enum Status {
        /**
         * 审核中。
         */
        PENDING_REVIEW("text.real.name.status.pending.review"),
        /**
         * 已认证。
         */
        APPROVED("text.real.name.status.approved"),
        /**
         * 已拒绝。
         */
        REJECTED("text.real.name.status.rejected");

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

    public List<RealNameImage> getImages() {
        return images;
    }

    public void setImages(List<RealNameImage> images) {
        this.images = images;
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

    public Administrator getReviewer() {
        return reviewer;
    }

    public void setReviewer(Administrator reviewer) {
        this.reviewer = reviewer;
    }
}
