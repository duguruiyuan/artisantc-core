package cn.artisantc.core.web.rest;

/**
 * 从Mob返回的响应结果的常量集合，数据来源于：http://wiki.mob.com/webapi2-0/，页面底部附录“错误编码”里的内容。
 * Created by xinjie.li on 2016/9/5.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MobStatusCode {

    private MobStatusCode() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 验证成功。
     */
    public static final String OK = "200";

    /**
     * AppKey为空。
     */
    public static final String REAPP_KEY_BLANK = "405";

    /**
     * AppKey无效。
     */
    public static final String APP_KEY_INVALID = "406";

    /**
     * 国家代码或手机号码为空。
     */
    public static final String REZONE_OR_PHONE_BLANK = "456";

    /**
     * 手机号码格式错误。
     */
    public static final String PHONE_INVALID = "457";

    /**
     * 请求校验的验证码为空。
     */
    public static final String SMS_CAPTCHA_BLANK = "466";

    /**
     * 请求校验验证码频繁（5分钟内同一个appkey的同一个号码最多只能校验三次）。
     */
    public static final String SMS_CAPTCHA_FREQUENTLY = "467";

    /**
     * 验证码错误。
     */
    public static final String SMS_CAPTCHA_INVALID = "468";

    /**
     * 没有打开服务端验证开关。
     */
    public static final String SERVICE_TURN_OFF = "474";
}
