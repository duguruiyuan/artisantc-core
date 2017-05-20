package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.AdvertisementService;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.AdvertisementViewPaginationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * “广告”操作的API。
 * Created by xinjie.li on 2016/11/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class AdvertisementFacade {

    private AdvertisementService advertisementService;

    @Autowired
    public AdvertisementFacade(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @RequestMapping(value = "/advertisements", method = RequestMethod.GET)
    public AdvertisementViewPaginationList getAdvertisements(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return advertisementService.findByPage(page, PageUtil.ADVERTISEMENT_PAGE_SIZE);
    }
}
