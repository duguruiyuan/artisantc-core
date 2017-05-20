package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.ArtMomentComment;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentView;
import org.springframework.core.convert.converter.Converter;

/**
 * “ArtMomentComment”转“ArtMomentCommentView”的转换器。
 * Created by xinjie.li on 2016/9/13.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentCommentViewConverter implements Converter<ArtMomentComment, ArtMomentCommentView> {

    @Override
    public ArtMomentCommentView convert(ArtMomentComment source) {
        ArtMomentCommentView view = new ArtMomentCommentView();
        view.setId(String.valueOf(source.getId()));
        view.setComment(source.getComment());
        view.setCommentDateTime(source.getCreateDateTime());
        view.setOwnerNickname(source.getOwner().getProfile().getNickname());
        view.setOwnerAvatarUrl(UserHelper.getAvatar3xUrl(source.getOwner()));
        if (null != source.getParentComment()) {
            view.setParentCommentId(String.valueOf(source.getParentComment().getId()));
            view.setParentCommentUserNickname(source.getParentComment().getOwner().getProfile().getNickname());
        }
        view.setOwnerId(String.valueOf(source.getOwner().getId()));
        view.setOauthId(UserHelper.getCurrentLoginOauthId());

        return view;
    }
}
