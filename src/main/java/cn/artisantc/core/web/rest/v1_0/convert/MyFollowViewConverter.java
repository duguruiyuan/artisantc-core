package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.MyFollow;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.web.rest.v1_0.vo.MyFollowView;
import org.springframework.core.convert.converter.Converter;

/**
 * “MyFollow”转“MyFollowView”的转换器。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFollowViewConverter implements Converter<MyFollow, MyFollowView> {

    @Override
    public MyFollowView convert(MyFollow source) {
        MyFollowView vo = new MyFollowView();
        vo.setFollowUserId(String.valueOf(source.getFollow().getId()));
        vo.setFollowUserNickname(source.getFollow().getProfile().getNickname());
        vo.setFollowUserSerialNumber(source.getFollow().getSerialNumber());
        vo.setFollowUserAvatarUrl(UserHelper.getAvatar3xUrl(source.getFollow()));

        return vo;
    }
}
