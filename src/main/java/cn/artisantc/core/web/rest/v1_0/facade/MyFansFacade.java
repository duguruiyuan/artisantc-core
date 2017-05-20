package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MyFansService;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFansPaginationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * “我的粉丝”操作的API。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MyFansFacade {

    private MyFansService fansService;

    @Autowired
    public MyFansFacade(MyFansService fansService) {
        this.fansService = fansService;
    }

    /**
     * 根据给定的页数获取“我的粉丝”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     */
    @RequestMapping(value = "/i/fans", method = RequestMethod.GET)
    public MyFansPaginationList getMyFans(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return fansService.findByPage(page);
    }
}
