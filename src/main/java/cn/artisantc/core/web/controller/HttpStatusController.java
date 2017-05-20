package cn.artisantc.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 处理“40x”等Http Status Code的响应逻辑。
 * Created by xinjie.li on 2016/9/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Controller
public class HttpStatusController {

    @RequestMapping(value = "/400", method = RequestMethod.GET)
    public String error400() {
        return "400";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "404";
    }
}
