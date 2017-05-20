package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ScreenShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * “显示界面的参数控制”相关操作的API。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@RestController
@RequestMapping(value = "/api")
public class ScreenShowFacade {

    private ScreenShowService screenShowService;

    @Autowired
    public ScreenShowFacade(ScreenShowService screenShowService) {
        this.screenShowService = screenShowService;
    }

    /**
     * 获得“显示界面的参数控制”。
     *
     * @return “显示界面的参数控制”
     */
    @RequestMapping(value = "/screen-show", method = RequestMethod.GET)
    public Map<String, String> getScreenShow() {
        return screenShowService.getScreenShow();
    }
}
