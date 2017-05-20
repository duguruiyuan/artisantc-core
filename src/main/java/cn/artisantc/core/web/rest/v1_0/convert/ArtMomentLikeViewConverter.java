package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.ArtMomentLike;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentLikeView;
import org.springframework.core.convert.converter.Converter;

/**
 * “ArtMomentLike”转“ArtMomentLikeView”的转换器。
 * Created by xinjie.li on 2016/9/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentLikeViewConverter implements Converter<ArtMomentLike, ArtMomentLikeView> {

    @Override
    public ArtMomentLikeView convert(ArtMomentLike source) {
        ArtMomentLikeView vo = new ArtMomentLikeView();
        vo.setId(String.valueOf(source.getId()));
        vo.setCreateDateTime(DateTimeUtil.getPrettyDescription(source.getCreateDateTime()));
        vo.setNickname(source.getUser().getProfile().getNickname());
        vo.setAvatarUrl(UserHelper.getAvatar3xUrl(source.getUser()));

        return vo;
    }
}
