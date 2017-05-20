package cn.artisantc.core.web.controller;

import cn.artisantc.core.exception.AdvertisementNotFoundException;
import cn.artisantc.core.exception.BaseNotFoundException;
import cn.artisantc.core.exception.InformationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 对Controller的Exception进行全局处理。
 * Created by xinjie.li on 2016/11/1.
 *
 * @author xinjie.li
 * @since 1.0
 */
@ControllerAdvice(basePackages = {"cn.artisantc.core.web.controller"})
public class ControllerExceptionHandle {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandle.class);

    private MessageSource messageSource;

    @Autowired
    public ControllerExceptionHandle(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 对<b>InformationNotFoundException</b>及其子类异常的处理。
     *
     * @param exception BaseNotFoundException
     */
    @ExceptionHandler({InformationNotFoundException.class, AdvertisementNotFoundException.class})
    public String handleInformationNotFoundException(BaseNotFoundException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return "redirect:/404";
    }

    /**
     * 对其他未特别指定类型的异常的处理。
     *
     * @param exception 其他未特别制定类型的Exception异常
     * @param locale    当前国际化i18n的设置
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception exception, HttpServletRequest request, Locale locale) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }

        request.setAttribute("error", messageSource.getMessage("validation.error.api.E010000", null, locale));
    }
}
