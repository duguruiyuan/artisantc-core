package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.persistence.entity.UserProfile;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * “UserProfile”的帮助类。
 * Created by xinjie.li on 2016/12/29.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class UserProfileHelper {

    private UserProfileHelper() {
    }

    public static void validateNickname(String nickname) {
        // 校验“昵称”
        if (StringUtils.isNotBlank(nickname) && nickname.length() > APIErrorResponse.MAX_USER_NICKNAME_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010034.getErrorCode());
        }
    }

    public static void validateSex(String sex) {
        if (StringUtils.isNotBlank(sex)) {
            try {
                UserProfile.UserSex.valueOf(sex);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010030.getErrorCode());
            }
        }
    }

    public static Date validateBirthday(String birthday) {
        if (StringUtils.isNotBlank(birthday)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_YEAR_MONTH_DAY);
                return sdf.parse(birthday);
            } catch (ParseException e) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010042.getErrorCode());
            }
        }
        return null;
    }
}
