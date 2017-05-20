package cn.artisantc.core.persistence;

/**
 * 本常量类是用于列举在实体类中定义字段的长度的公共常量，目的主要用于解决相同含义、相同名称的字段的长度一致性问题。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class EntityConstant {

    /**
     * "用户名 - username"的字段长度。
     */
    public static final int USERNAME_LENGTH = 50;

    /**
     * “拍品类别 - category”的字段长度。
     */
    public static final int ITEM_CATEGORY_LENGTH = 40;

    /**
     * “真实姓名 - realName”的字段长度。
     */
    public static final int REAL_NAME_LENGTH = 30;

    /**
     * “身份证号 - identityNumber”的字段长度。
     */
    public static final int IDENTITY_NUMBER_LENGTH = 30;

    /**
     * “手机号 - mobile”的字段长度。
     */
    public static final int MOBILE_LENGTH = 30;

    /**
     * “快递公司代码 - code”的字段长度。
     */
    public static final int EXPRESS_COMPANY_CODE_LENGTH = 30;

    /**
     * “银行代码 - bankCode”的字段长度。
     */
    public static final int BANK_CODE_LENGTH = 20;

    /**
     * “银行名称 - bankName”的字段长度。
     */
    public static final int BANK_NAME_LENGTH = 40;

    /**
     * “收货地址、退货地址 - remark”的字段长度。
     */
    public static final int ADDRESS_REMARK_LENGTH = 40;

    /**
     * “IP地址 - ip”的字段长度，最大长度128位用以支持IPV6。
     *
     * @since 2.4
     */
    public static final int IP_LENGTH = 128;

    /**
     * “认证ID - oauthId”的字段长度。
     *
     * @since 2.4
     */
    public static final int OAUTH_ID_LENGTH = 200;

    /**
     * “认证渠道 - oauthChannel”的字段长度。
     *
     * @since 2.4
     */
    public static final int OAUTH_CHANNEL_LENGTH = 20;
}
