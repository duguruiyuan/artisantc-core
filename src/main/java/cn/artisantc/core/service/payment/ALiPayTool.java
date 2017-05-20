package cn.artisantc.core.service.payment;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * “支付宝”接口调用的工具类。
 * Created by xinjie.li on 2016/10/26.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ALiPayTool {

    private static final Logger LOG = LoggerFactory.getLogger(ALiPayTool.class);

    /**
     * 支付宝的正式分配的AppId。
     */
    public static final String APP_ID = "2016110702598792";// 正式的appId

    /**
     * 支付宝的正式地址。
     */
    public static final String URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 支付宝的正式地址用的RSA签名用的私钥。
     */
    public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ4IGdNfVS9xXT5X\n" +
            "ljttp9hAinzcJsqfcvjrqi2pd8cdstWE0VljKgn4FqmckoFYLkcAhC+DsL7R9kAU\n" +
            "G6//ERy5XinuUXXhxHUyN+bI7Wd5wtYV9Y5XReygeAbroHu+OJouEXdfSwuOUOyf\n" +
            "OKaMffgT3OMkgLWut+JiOnYYC+CpAgMBAAECgYBtF1iEosXSZ2af16S8x5jbXsp7\n" +
            "IRqiO2oJzd1/ggJOcQD4BqTiGON3WEyEr7IvGelwWDnn9ve28ogUeg0W9HQhk0zD\n" +
            "YbA898OdTUnnxlvdw00NhVRD9yRiL58Ak5e/jhJovHNUfbDfFev0bb4G4IvMFW9+\n" +
            "B6YSMBa5VMqWzXuG0QJBAM2jofuf4JsxPnbk1xYU8m9GRzya/JLClEqCfZ9yV3QW\n" +
            "xuSVcCpGdyF6iza/AyxnK5dwDPaerKQgBEtUMUOy4NUCQQDEu8FOeO/dK1guH+hI\n" +
            "+yqPzchGndkqU3RhM6B4xhF4W3htjr3Nx9eAz98DbtI1TPnlFUMCjfpcsyUrb1g+\n" +
            "YcqFAkBCRPlunZdEbBo1NooaE6YNNhi+7zN2GD6xxFu3hqGoR67Q+1eqSAscPuGe\n" +
            "QvBC3vytUO7ci0YXnjnEDN3Tc1IBAkAawXIzyuzMRoXSmBJOQqJyNkljmTocsIbm\n" +
            "ZVAQvNdJaB9DvyL/JoufgCXTYLID/7kIt9CKO2buTorikl0M9t3FAkBX/8eSbNvl\n" +
            "224SjZeXHh3uN0pNQHWbqCMtSFTAq4wl9/Pq+OCPSJpNugtu+I0BLGBT0dYQQGPy\n" +
            "kspgxOZAqaiO";

    /**
     * 支付宝的正式地址用的RSA签名用的公钥。
     */
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeCBnTX1UvcV0+V5Y7bafYQIp8\n" +
            "3CbKn3L466otqXfHHbLVhNFZYyoJ+BapnJKBWC5HAIQvg7C+0fZAFBuv/xEcuV4p\n" +
            "7lF14cR1MjfmyO1necLWFfWOV0XsoHgG66B7vjiaLhF3X0sLjlDsnzimjH34E9zj\n" +
            "JIC1rrfiYjp2GAvgqQIDAQAB";

    /**
     * “支付宝”接口“APP支付”方式的调用方法，用于在生成签名时使用。
     */
    static final String METHOD = "alipay.trade.app.pay";

    /**
     * “支付宝”调用接口时使用的签名算法。
     */
    public static final String SIGN_TYPE = "RSA";

    /**
     * “支付宝”调用接口时使用的版本号。
     */
    public static final String VERSION = "1.0";

    /**
     * “支付宝”调用接口时使用的PID。
     */
    public static final String SELL_ID = "2088421459136999";

    /**
     * 给“支付宝”的异步通知使用的回调地址。
     */
    public static String getNotifyUrl() {
        try {
            Configurations configs = new Configurations();
            Configuration config = configs.properties(new File("payment.properties"));
            return config.getString("alipay.notify.url");
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);

            // 读取配置文件出错，强制使用“生产环境”的地址
            return "https://api.artisansay.com:8443/payment/alipay-notify-callback";
        }
    }

    /**
     * “支付宝”调用接口时使用的销售产品码，根据“支付宝”文档的描述，固定为该值。
     */
    public static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    /**
     * “卖家支付宝用户号”，用于对返回结果“验签”的时候使用。
     */
    public static final String SELLER_EMAIL = "ch@artisantc.cn";

    /**
     * “卖家支付宝账号”，用于对返回结果“验签”的时候使用。
     */
    public static final String SELLER_ID = "2088421459136999";// 对应“支付宝”的商户的PID的值

    static AlipayClient getAlipayClient() {
        return new DefaultAlipayClient(ALiPayTool.URL, ALiPayTool.APP_ID, ALiPayTool.PRIVATE_KEY, AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8); //获得初始化的AlipayClient
    }

    static final String ORDER_NUMBER_KEY = "out_trade_no";

    static final String TOTAL_AMOUNT_KEY = "total_amount";

    static final String APP_ID_KEY = "app_id";
}
