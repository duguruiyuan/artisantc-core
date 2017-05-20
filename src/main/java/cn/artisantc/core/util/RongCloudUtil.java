package cn.artisantc.core.util;

/**
 * “融云”即时聊天的工具类。
 * Created by xinjie.li on 2016/11/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RongCloudUtil {

    private RongCloudUtil() {
    }

    public static final String DOMAIN = "http://api.cn.ronghub.com";// TODO: 2016/10/10 放到配置文件中
    public static final String URI = "/user/getToken.json";// TODO: 2016/10/10 放到配置文件中
    public static final String APP_KEY = "z3v5yqkbzcq30";// TODO: 2016/10/10 放到配置文件中，生产环境
    public static final String APP_SECRET = "KO1jQuFWYll5oU";// TODO: 2016/10/10 放到配置文件中，生产环境
}
