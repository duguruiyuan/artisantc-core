package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * “UserALiPayAccount”的帮助类。
 * Created by xinjie.li on 2017/2/24.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class UserALiPayAccountHelper {

    private UserALiPayAccountHelper() {
    }

    /**
     * todo:javadoc
     *
     * @param userALiPayAccount
     */
    public static void validateUserALiPayAccount(String userALiPayAccount) {
        if (StringUtils.isBlank(userALiPayAccount) || !NumberUtils.isParsable(userALiPayAccount)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010214.getErrorCode());
        }
    }
}
