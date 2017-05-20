package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “商家的银行卡”。
 * Created by xinjie.li on 2016/10/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_merchant_bank_card")
public class MerchantBankCard extends BaseEntity {

    @Column(name = "bank_account", length = 30)
    private String bankAccount;// 银行账号，即银行卡的卡号

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Category category;// 银行卡类型

    @Column(name = "bank_code", length = EntityConstant.BANK_CODE_LENGTH)
    private String bankCode;// 所属银行的简称，例如：CMB，CCB

    @Column(name = "bank_name", length = EntityConstant.BANK_NAME_LENGTH)
    private String bankName;// 所属银行的名称

    @Column(name = "real_name", length = EntityConstant.REAL_NAME_LENGTH)
    private String realName;// 该银行卡的主人的真实姓名

    @Column(length = EntityConstant.MOBILE_LENGTH)
    private String mobile;// 银行卡预留的手机号

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MERCHANT_BANK_CARD_USER_ID"))
    private User user;// 银行卡的商家用户

    @Column(name = "is_proceeds")
    private boolean isProceeds = Boolean.FALSE;// 是否是商家默认的收款账户

    /**
     * “银行卡类型”枚举。
     */
    public enum Category {
        /**
         * 借记卡(储蓄卡)。
         */
        DEBIT_CARD("text.bank.card.category.debit"),
        /**
         * 贷记卡(信用卡)。
         */
        CREDIT_CARD("text.bank.card.category.credit"),
        /**
         * 准贷记卡(信用卡)。
         */
        SEMI_CREDIT_CARD("text.bank.card.category.semi.credit");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Category(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isProceeds() {
        return isProceeds;
    }

    public void setProceeds(boolean proceeds) {
        isProceeds = proceeds;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
