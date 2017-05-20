package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.ItemImage;
import cn.artisantc.core.persistence.entity.ItemOrder;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ItemOrderView;
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
 * “ItemOrder”转“ItemOrderView”的转换器。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderViewConverter implements Converter<ItemOrder, ItemOrderView> {

    private static final Logger LOG = LoggerFactory.getLogger(ItemViewConverter.class);

    private MessageSource messageSource;

    public ItemOrderViewConverter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public ItemOrderView convert(ItemOrder source) {
        ItemOrderView view = new ItemOrderView();

        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(source.getCreateDateTime()));
        view.setAmount(String.valueOf(source.getItem().getBidPrice()));
        view.setOrderNumber(source.getOrderNumber());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setStatus(messageSource.getMessage(source.getStatus().getMessageKey(), null, request.getLocale()));
        view.setStatusCode(source.getStatus().name());

        if (null != source.getResult()) {
            view.setResult(messageSource.getMessage(source.getResult().getMessageKey(), null, request.getLocale()));
            view.setResult(source.getResult().name());
        }

        // 订单的拍品属性
        view.setItemId(String.valueOf(source.getItem().getId()));
        view.setTitle(source.getItem().getTitle());
        view.setIsFixed(String.valueOf(source.getItem().isFixed()));
        view.setInitialPrice(String.valueOf(source.getItem().getInitialPrice()));

        try {
            ItemImage itemImage = source.getItem().getImages().iterator().next();
            Merchant merchant = source.getItem().getShop().getMerchant();
            view.setCoverUrl(ImageUtil.getItemImageUrlPrefix(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/shop/items/" + itemImage.getImageName());// 封面图片的URL地址
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }

        return view;
    }
}
