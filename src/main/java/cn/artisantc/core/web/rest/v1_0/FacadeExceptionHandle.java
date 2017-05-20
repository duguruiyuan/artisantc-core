package cn.artisantc.core.web.rest.v1_0;

import cn.artisantc.core.exception.BaseNotFoundException;
import cn.artisantc.core.exception.CommentNotFoundException;
import cn.artisantc.core.exception.InformationNotFoundException;
import cn.artisantc.core.exception.ItemCategoryNotFoundException;
import cn.artisantc.core.exception.ItemDeliveryAddressNotFoundException;
import cn.artisantc.core.exception.ItemNotFoundException;
import cn.artisantc.core.exception.ItemOrderNotFoundException;
import cn.artisantc.core.exception.ItemReturnAddressNotFoundException;
import cn.artisantc.core.exception.MerchantAccountBillNotFountException;
import cn.artisantc.core.exception.MomentAccessDeniedException;
import cn.artisantc.core.exception.MomentNotFoundException;
import cn.artisantc.core.exception.PaymentOrderNotFoundException;
import cn.artisantc.core.exception.ShopNotFoundException;
import cn.artisantc.core.exception.UploadAdvertisementImageFailureException;
import cn.artisantc.core.exception.UserAccountBillNotFountException;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 对REST API层面的Exception进行全局处理。
 * Created by xinjie.li on 2016/9/5.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestControllerAdvice(basePackages = {"cn.artisantc.core.web.rest.v1_0.facade"})
public class FacadeExceptionHandle {

    private static final Logger LOG = LoggerFactory.getLogger(FacadeExceptionHandle.class);

    private MessageSource messageSource;

    @Autowired
    public FacadeExceptionHandle(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 对服务层接口抛出的<b>IllegalArgumentException</b>异常的处理。
     *
     * @param exception 服务层接口抛出的IllegalArgumentException异常
     * @param locale    当前国际化i18n的设置
     * @return 错误信息
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public APIErrorResponse handleInvalidCredentialsException(IllegalArgumentException exception, Locale locale) {
        String errorCode = exception.getMessage();
        String messageKey = "";
        String[] args = null;
        for (APIErrorResponse.ErrorCode code : APIErrorResponse.ErrorCode.values()) {
            if (errorCode.equals(code.getErrorCode())) {
                messageKey = code.getErrorDesc();
                if (null != code.getArgs()) {
                    args = code.getArgs();
                }
                break;
            }
        }
        String message = messageSource.getMessage(messageKey, args, locale);

        if (LOG.isErrorEnabled()) {
            LOG.error(errorCode + ": " + message);
        }

        return new APIErrorResponse(errorCode, message);
    }

    /**
     * 对<b>HttpMessageNotReadableException</b>异常的处理。
     *
     * @param exception HttpMessageNotReadableException
     * @param locale    当前国际化i18n的设置
     * @return 错误信息
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public APIErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, Locale locale) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }

        return new APIErrorResponse("010000", messageSource.getMessage("validation.error.api.E010000", null, locale));
    }

    /**
     * 对<b>HttpMediaTypeNotSupportedException</b>异常的处理。
     *
     * @param exception HttpMediaTypeNotSupportedException
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public void handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    /**
     * 对其他未特别指定类型的异常的处理。
     *
     * @param exception 其他未特别制定类型的Exception异常
     * @param locale    当前国际化i18n的设置
     * @return 错误信息
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public APIErrorResponse handleException(Exception exception, Locale locale) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
        return new APIErrorResponse(APIErrorResponse.ErrorCode.E999999.getErrorCode(), messageSource.getMessage(APIErrorResponse.ErrorCode.E999999.getErrorDesc(), null, locale));
    }

    /**
     * 对<b>UsernameNotFoundException</b>异常的处理。
     *
     * @param exception UsernameNotFoundException
     */
    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public void handleUsernameNotFoundException(UsernameNotFoundException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    /**
     * 对<b>BaseNotFoundException</b>及其子类异常的处理。
     *
     * @param exception BaseNotFoundException
     */
    @ExceptionHandler({MomentNotFoundException.class, ItemCategoryNotFoundException.class, ShopNotFoundException.class,
            ItemNotFoundException.class, ItemDeliveryAddressNotFoundException.class, ItemReturnAddressNotFoundException.class,
            ItemOrderNotFoundException.class, MerchantAccountBillNotFountException.class, PaymentOrderNotFoundException.class,
            InformationNotFoundException.class, CommentNotFoundException.class, UserAccountBillNotFountException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleNotFoundException(BaseNotFoundException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    /**
     * 对<b>MomentAccessDeniedException</b>异常的处理。
     *
     * @param exception MomentAccessDeniedException
     */
    @ExceptionHandler({MomentAccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void handleMomentAccessDenied(MomentAccessDeniedException exception) {
        if (LOG.isErrorEnabled()) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    @ExceptionHandler({UploadAdvertisementImageFailureException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUploadAdvertisementImageFailureException(UploadAdvertisementImageFailureException exception, Locale locale) {
        String errorCode = exception.getMessage();
        String messageKey = "";
        String[] args = null;
        for (APIErrorResponse.ErrorCode code : APIErrorResponse.ErrorCode.values()) {
            if (errorCode.equals(code.getErrorCode())) {
                messageKey = code.getErrorDesc();
                if (null != code.getArgs()) {
                    args = code.getArgs();
                }
                break;
            }
        }

        Map<String, String> map = new HashMap<>();
        map.put("error", messageSource.getMessage(messageKey, args, locale));

        return map;
    }

    // TODO: 发布艺文时上传文件如果超过了规定的最大值，则抛出异常，针对这个异常做处理
}
