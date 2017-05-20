package cn.artisantc.core.web.controller;

import cn.artisantc.core.service.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * “用来接收第三方支付渠道的异步通知的控制器”。
 * Created by xinjie.li on 2016/10/27.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/payment")
public class PaymentNotifyCallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentNotifyCallbackController.class);

    private PaymentService paymentService;

    @Autowired
    public PaymentNotifyCallbackController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 接收“支付宝”异步通知。
     *
     * @param request “支付宝”异步通知的请求参数，即“支付宝”的处理结果的参数
     * @return “系统自身处理异步通知的结果页面”，按照“支付宝”文档的要求的内容返回
     */
    @RequestMapping(value = "/alipay-notify-callback", method = RequestMethod.POST)
    public String alipayNotifyCallback(HttpServletRequest request) {
        boolean result = paymentService.getALiPayNotifyCallback(request.getParameterMap());
        LOG.debug("收到支付宝异步通知的处理结果：{}", result);
        if (result) {
            request.setAttribute("result", "success");// 支付宝要求必须返回“success”，做为商户系统处理成功的标志
        } else {
            request.setAttribute("result", "failure");// 支付宝要求必须返回“failure”，做为商户系统处理失败的标志
        }

        return "/alipay_notify_callback";
    }
}
