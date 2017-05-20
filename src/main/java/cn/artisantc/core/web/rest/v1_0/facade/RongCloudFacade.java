package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.exception.RongCloudGetTokenFailureException;
import cn.artisantc.core.service.RongCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * “融云”通讯的API。
 * Created by xinjie.li on 2016/10/10.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class RongCloudFacade {

    private RongCloudService rongCloudService;

    @Autowired
    public RongCloudFacade(RongCloudService rongCloudService) {
        this.rongCloudService = rongCloudService;
    }

    @ExceptionHandler({RongCloudGetTokenFailureException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleInvalidCredentialsException() {
    }

    /**
     * 获取令牌。
     *
     * @return
     */
    @RequestMapping(value = "/messages/token", method = RequestMethod.GET)
    public Map<String, String> getRongCloudToken() {
        Map<String, String> map = new HashMap<>();
        map.put("token", rongCloudService.getToken());
        return map;
    }
}
