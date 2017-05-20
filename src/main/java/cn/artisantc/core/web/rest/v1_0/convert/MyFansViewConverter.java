package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.MyFans;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.web.rest.v1_0.vo.MyFansView;
import org.springframework.core.convert.converter.Converter;

/**
 * “MyFans”转“MyFansView”的转换器。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFansViewConverter implements Converter<MyFans, MyFansView> {

    @Override
    public MyFansView convert(MyFans source) {
        MyFansView vo = new MyFansView();
        vo.setFansUserId(String.valueOf(source.getFans().getId()));
        vo.setFansUserNickname(source.getFans().getProfile().getNickname());
        vo.setFansUserSerialNumber(source.getFans().getSerialNumber());
        vo.setFansUserAvatarUrl(UserHelper.getAvatar3xUrl(source.getFans()));

        return vo;
    }
}
