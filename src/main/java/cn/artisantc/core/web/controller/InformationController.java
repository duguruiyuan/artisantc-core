package cn.artisantc.core.web.controller;

import cn.artisantc.core.service.AdvertisementService;
import cn.artisantc.core.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * “资讯”相关的控制器。
 * Created by xinjie.li on 2016/10/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/web")
public class InformationController {

    private InformationService informationService;

    private AdvertisementService advertisementService;

    @Autowired
    public InformationController(InformationService informationService, AdvertisementService advertisementService) {
        this.informationService = informationService;
        this.advertisementService = advertisementService;
    }

    /**
     * 资讯详情页面。
     *
     * @param id    资讯ID
     * @param model
     * @return 资讯详情页面
     */
    @RequestMapping(value = "/information/{id}", method = RequestMethod.GET)
    public String getInformationById(@PathVariable(value = "id") String id, Model model) {
        model.addAttribute("view", informationService.findById(id));
        model.addAttribute("comments", informationService.findLatestInformationCommentsByInformationId(id));
        return "/web/information_detail";
    }

    /**
     * 广告详情页面。
     *
     * @param id    广告ID
     * @param model
     * @return 广告详情页面
     */
    @RequestMapping(value = "/advertisements/{id}", method = RequestMethod.GET)
    public String getAdvertisementById(@PathVariable(value = "id") String id, Model model) {
        model.addAttribute("view", advertisementService.findById(id));
        return "/web/advertisement_detail";
    }
}
