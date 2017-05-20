package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.MyFavoriteArtMoment;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.web.rest.v1_0.vo.MyFavoriteArtMomentView;
import org.springframework.core.convert.converter.Converter;

/**
 * “MyFavoriteArtMoment”转“MyFavoriteArtMomentView”的转换器。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteArtMomentViewConverter implements Converter<MyFavoriteArtMoment, MyFavoriteArtMomentView> {

    @Override
    public MyFavoriteArtMomentView convert(MyFavoriteArtMoment source) {
        MyFavoriteArtMomentView vo = new MyFavoriteArtMomentView();
        vo.setNickname(String.valueOf(source.getArtMoment().getUser().getProfile().getNickname()));
        vo.setContent(source.getArtMoment().getContent());
        vo.setCreateDateTime(DateTimeUtil.getPrettyDescription(source.getCreateDateTime()));
        vo.setMomentId(String.valueOf(source.getArtMoment().getId()));
        vo.setAvatarUrl(UserHelper.getAvatar3xUrl(source.getArtMoment().getUser()));

        return vo;
    }
}
