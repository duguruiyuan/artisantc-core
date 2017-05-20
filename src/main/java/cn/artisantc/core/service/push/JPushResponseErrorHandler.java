package cn.artisantc.core.service.push;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 针对“JPush”实现的基于接口{@link ResponseErrorHandler}的实现类。
 * 用于拦截和处理在调用“JPush”接口时发生的错误异常。
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class JPushResponseErrorHandler extends DefaultResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = this.getHttpStatusCode(response);
        switch (statusCode.series()) {
            case CLIENT_ERROR:
                break;
            default:
                super.handleError(response);
        }
    }

    /**
     * 从{@link DefaultResponseErrorHandler}中复制过来的代码，因为其为私有方法，无法直接使用。
     */
    private HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode;
        try {
            statusCode = response.getStatusCode();
        } catch (IllegalArgumentException ex) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
                    response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
        }
        return statusCode;
    }

    /**
     * 从{@link DefaultResponseErrorHandler}中复制过来的代码，因为其为私有方法，无法直接使用。
     */
    private byte[] getResponseBody(ClientHttpResponse response) {
        try {
            InputStream responseBody = response.getBody();
            if (responseBody != null) {
                return FileCopyUtils.copyToByteArray(responseBody);
            }
        } catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }

    /**
     * 从{@link DefaultResponseErrorHandler}中复制过来的代码，因为其为私有方法，无法直接使用。
     */
    private Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return contentType != null ? contentType.getCharset() : null;
    }
}
