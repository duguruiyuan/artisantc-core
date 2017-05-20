package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.ArtMomentImage;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentImageView;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * “ArtMomentImage”转“ArtMomentImageView”的转换器。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ArtMomentImageViewConverter implements Converter<ArtMomentImage, ArtMomentImageView> {

    private static final Logger LOG = LoggerFactory.getLogger(ArtMomentImageViewConverter.class);

    @Override
    public ArtMomentImageView convert(ArtMomentImage source) {
        ArtMomentImageView vo = new ArtMomentImageView();
        vo.setId(String.valueOf(source.getId()));
        try {
            // 图片
            vo.setImageUrl(ImageUtil.getMomentImageUrlPrefix() + source.getImageName());
            vo.setImageWidth(String.valueOf(source.getImageWidth()));
            vo.setImageHeight(String.valueOf(source.getImageHeight()));

            // 缩略图
            vo.setThumbnailUrl(ImageUtil.getThumbnailUrlPrefix() + source.getThumbnailName());
            vo.setThumbnailWidth(String.valueOf(source.getThumbnailWidth()));
            vo.setThumbnailHeight(String.valueOf(source.getThumbnailHeight()));
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }

        return vo;
    }
}
