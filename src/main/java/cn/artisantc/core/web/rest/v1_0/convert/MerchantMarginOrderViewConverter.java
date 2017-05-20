package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.MerchantMarginOrder;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.web.controller.PaymentConstant;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantMarginOrderView;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * “MerchantMarginOrder”转“MerchantMarginOrderView”的转换器。
 * Created by xinjie.li on 2016/12/6.
 *
 * @author xinjie.li
 * @since 1.1
 */
public class MerchantMarginOrderViewConverter implements Converter<MerchantMarginOrder, MerchantMarginOrderView> {

    private MessageSource messageSource;

    public MerchantMarginOrderViewConverter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public MerchantMarginOrderView convert(MerchantMarginOrder source) {
        MerchantMarginOrderView view = new MerchantMarginOrderView();
        view.setAmount(source.getAmount().toString());
        view.setAmountPaid(source.getAmountPaid().toString());
        view.setAmountPayable(source.getAmountPayable().toString());
        view.setOrderNumber(source.getOrderNumber());
        view.setTitle(source.getTitle());
        view.setUpdateDateTime(DateTimeUtil.getPrettyDescription(source.getUpdateDateTime()));

        // 计算支付超时的“倒计时”
        Date createDateTime = source.getCreateDateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createDateTime.getTime() + PaymentConstant.TIMEOUT_MILLIS_MARGIN_ORDER);// 需要在订单创建时间上加上超时时间，再进行倒计时的计算
        view.setCountdown(DateTimeUtil.getCountdownDescription(calendar.getTime()));

        // 需要国际化的数据
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setStatus(messageSource.getMessage(source.getStatus().getMessageKey(), null, request.getLocale()));
        if (null != source.getPaymentChannel()) {
            view.setPaymentChannel(messageSource.getMessage(source.getPaymentChannel().getMessageKey(), null, request.getLocale()));
        }

        return view;
    }
}
