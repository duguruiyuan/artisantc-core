package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.Tag;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.OriginalArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.TagView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * “ArtMoment”转“ArtMomentView”的转换器。
 * Created by xinjie.li on 2016/5/27.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentViewConverter implements Converter<ArtMoment, ArtMomentView> {

    @Override
    public ArtMomentView convert(ArtMoment source) {
        ArtMomentView view = new ArtMomentView();
        // “艺文ID”
        view.setMomentId(String.valueOf(source.getId()));

        // “艺文内容”
        if (null == source.getContent()) {
            view.setContent("");
        } else {
            view.setContent(source.getContent());
        }

        // “发布时的地理位置”
        if (null == source.getLocation()) {
            view.setLocation("");
        } else {
            view.setLocation(source.getLocation());
        }

        // “发布时间”
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(source.getCreateDateTime()));

        // “头像”和“头像缩略图”地址
        view.setAvatarFileUrl(UserHelper.getAvatarUrl(source.getUser()));
        view.setAvatarUrl(UserHelper.getAvatar3xUrl(source.getUser()));

        // “昵称”
        view.setNickname(source.getUser().getProfile().getNickname());

        // “艺文图片”
        view.setArtMomentImages(ConverterUtil.getImages(source));

        // “发布者的用户ID”
        view.setUserId(String.valueOf(source.getUser().getId()));
        view.setUserSerialNumber(source.getUser().getSerialNumber());
        view.setOauthId(UserHelper.getCurrentLoginOauthId());

        // “原始艺文”
        if (null != source.getOriginalArtMoment()) {
            OriginalArtMomentView original = new OriginalArtMomentView();
            if (null != source.getOriginalArtMoment().getImages() && !source.getOriginalArtMoment().getImages().isEmpty()) {
                original.getArtMomentImages().addAll(ConverterUtil.getImages(source.getOriginalArtMoment()));
            }
            original.setMomentId(String.valueOf(source.getOriginalArtMoment().getId()));
            original.setAvatar(UserHelper.getAvatar3xUrl(source.getOriginalArtMoment().getUser()));
            if (StringUtils.isNotBlank(source.getOriginalArtMoment().getContent())) {
                original.setContent(source.getOriginalArtMoment().getContent());
            }
            original.setNickname(source.getOriginalArtMoment().getUser().getProfile().getNickname());
            original.setUserSerialNumber(source.getOriginalArtMoment().getUser().getSerialNumber());

            view.setOriginalArtMoment(original);
            view.setForwardDateTime(source.getCreateDateTime());// 如果有“原始艺文”，则表示为“转发”，此时记录“转发时间”
        }

        // “主要标签”
        if (null != source.getPrimaryTag()) {
            view.setPrimaryTag(source.getPrimaryTag().getName());
        }

        // “次要标签”
        if (null != source.getSecondaryTags() && !source.getSecondaryTags().isEmpty()) {
            List<TagView> tagViews = new ArrayList<>();
            for (Tag tag : source.getSecondaryTags()) {
                TagView tagView = new TagView();
                tagView.setName(tag.getName());

                tagViews.add(tagView);
            }

            view.getSecondaryTags().addAll(tagViews);
        }

        return view;
    }
}
