package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.persistence.entity.ItemImage;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ItemDetailView;
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
 * “Item”转“ItemDetailView”的转换器。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemDetailViewConverter implements Converter<Item, ItemDetailView> {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDetailViewConverter.class);

    private MessageSource messageSource;

    public ItemDetailViewConverter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ItemDetailView convert(Item source) {
        ItemDetailView view = new ItemDetailView();
        if (null != source) {
            view.setId(String.valueOf(source.getId()));
            view.setDetail(source.getDetail());
            view.setExpressFee(String.valueOf(source.getExpressFee()));
            view.setFixedPrice(String.valueOf(source.getFixedPrice()));
            view.setFreeExpress(source.isFreeExpress());
            view.setFreeReturn(source.isFreeReturn());
            view.setInitialPrice(String.valueOf(source.getInitialPrice()));
            view.setMargin(String.valueOf(source.getMargin()));
            view.setRaisePrice(String.valueOf(source.getRaisePrice()));
            view.setReferencePrice(String.valueOf(source.getReferencePrice()));
            view.setTitle(source.getTitle());
            view.setCountdown(DateTimeUtil.getCountdownDescription(source.getStartDateTime()));
            view.setCreateDateTime(DateTimeUtil.getPrettyDescription(source.getCreateDateTime()));
            view.setIsFixed(String.valueOf(source.isFixed()));
            view.setSerialNumber(source.getShop().getUser().getSerialNumber());
            view.setNickname(source.getShop().getUser().getProfile().getNickname());
            view.setShopSerialNumber(source.getShop().getSerialNumber());
            view.setStartDateTime(DateFormatUtils.format(source.getStartDateTime(), DateTimeUtil.DATE_FORMAT_ALL));
            view.setEndDateTime(DateFormatUtils.format(source.getEndDateTime(), DateTimeUtil.DATE_FORMAT_ALL));

            // “头像”和“头像缩略图”地址
            view.setAvatarUrl(UserHelper.getAvatar3xUrl(source.getShop().getUser()));
            try {
                ItemImage itemImage = source.getImages().iterator().next();
                Merchant merchant = source.getShop().getMerchant();
                view.setCoverUrl(ImageUtil.getItemImageUrlPrefix(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/shop/items/" + itemImage.getImageName());// 封面图片的URL地址
            } catch (ConfigurationException e) {
                LOG.error(e.getMessage(), e);
            }

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view.setStatus(messageSource.getMessage(source.getStatus().getMessageKey(), null, request.getLocale()));
            view.setStatusCode(source.getStatus().name());
        }
        return view;
    }
}
