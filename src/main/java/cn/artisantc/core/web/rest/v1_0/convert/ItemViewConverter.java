package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.persistence.entity.ItemImage;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ItemView;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * “Item”转“ItemView”的转换器。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemViewConverter implements Converter<Item, ItemView> {

    private static final Logger LOG = LoggerFactory.getLogger(ItemViewConverter.class);

    private MessageSource messageSource;

    public ItemViewConverter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ItemView convert(Item source) {
        ItemView view = new ItemView();

        if (null != source) {
            view.setId(String.valueOf(source.getId()));
            view.setCountdown(DateTimeUtil.getCountdownDescription(source.getStartDateTime()));

            // 拍品发布者的头像的缩略图地址
            view.setAvatarUrl(UserHelper.getAvatar3xUrl(source.getShop().getUser()));
            try {
                ItemImage itemImage = source.getImages().iterator().next();
                Merchant merchant = source.getShop().getMerchant();
                // 设置封面图片的“缩略图”的信息
                view.setCoverUrl(ImageUtil.getItemImageUrlPrefix(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/shop/items/" + itemImage.getThumbnailName());// 封面图片的URL地址
                view.setCoverWidth(String.valueOf(itemImage.getThumbnailWidth()));// 封面图片的宽度
                view.setCoverHeight(String.valueOf(itemImage.getThumbnailHeight()));// 封面图片的高度
            } catch (ConfigurationException e) {
                LOG.error(e.getMessage(), e);
            }

            view.setInitialPrice(String.valueOf(source.getInitialPrice()));
            view.setTitle(source.getTitle());

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view.setStatus(messageSource.getMessage(source.getStatus().getMessageKey(), null, request.getLocale()));

            view.setCreateDateTime(DateTimeUtil.getPrettyDescription(source.getCreateDateTime()));
            view.setIsFixed(String.valueOf(source.isFixed()));
            view.setNickname(source.getShop().getUser().getProfile().getNickname());
            view.setSerialNumber(source.getShop().getUser().getSerialNumber());
            view.setStartDateTime(DateFormatUtils.format(source.getStartDateTime(), DateTimeUtil.DATE_FORMAT_ALL));
            view.setEndDateTime(DateFormatUtils.format(source.getEndDateTime(), DateTimeUtil.DATE_FORMAT_ALL));
        }

        return view;
    }
}
