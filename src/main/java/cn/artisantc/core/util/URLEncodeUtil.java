package cn.artisantc.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 对URL中的特殊字符进行过滤，主要是为了对可能出现的特殊字符做处理，增强健壮性和容错性。
 * Created by xinjie.li on 2016/9/28.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class URLEncodeUtil {

    private URLEncodeUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    public static String replaceSpecialCharacters(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        return url
//                .replaceAll("\\+", "%2B")
//                .replaceAll("\\?", "%3F")
//                .replaceAll("%", "%25")
//                .replaceAll("#", "%23")
//                .replaceAll("&", "%26")
//                .replaceAll("=", "%3D")
                .replaceAll(" ", "%20");
    }
}
