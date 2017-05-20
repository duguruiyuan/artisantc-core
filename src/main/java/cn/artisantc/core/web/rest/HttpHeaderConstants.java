package cn.artisantc.core.web.rest;

/**
 * "Http Header"使用的参数名的常量集合。
 * Created by xinjie.li on 2016/5/27.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class HttpHeaderConstants {

    private HttpHeaderConstants() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 用于在"Http Header"中使用的"Content-Type"指定的json格式。
     */
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 用于在"Http Header"中使用的"User-Agent"。
     */
    public static final String USER_AGENT = "User-Agent";
}
