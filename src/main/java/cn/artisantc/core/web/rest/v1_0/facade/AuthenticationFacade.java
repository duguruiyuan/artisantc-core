package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * “商家认证”相关操作的API。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class AuthenticationFacade {

    private MerchantService merchantService;

    @Autowired
    public AuthenticationFacade(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

//    @RequestMapping(value = "/i/authentications", method = RequestMethod.GET)
//    public void getAuthentications() {
//        // todo：考虑支持“认证中心”需要的数据
//    }

//    /**
//     * 获得“我的个人商家认证”信息。
//     *
//     * @return “我的个人商家认证”信息
//     */
//    @RequestMapping(value = "/i/authentications/personal", method = RequestMethod.GET)
//    public MerchantView getPersonalMerchantAuthentication() {
//        return merchantService.findPersonalMerchant();
//    }

//    /**
//     * 提交“我的个人商家认证”信息。
//     */
//    @RequestMapping(value = "/i/authentications/personal", method = RequestMethod.POST)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void authenticatePersonalMerchant() {
//        merchantService.applyForPersonal();
//    }

//    /**
//     * 获得“我的企业商家认证”信息。
//     *
//     * @return “我的企业商家认证”信息
//     */
//    @RequestMapping(value = "/i/authentications/enterprise", method = RequestMethod.GET)
//    public MerchantView getEnterpriseMerchantAuthentication() {
//        return merchantService.findEnterpriseMerchant();
//    }

//    /**
//     * 提交“我的企业商家认证”信息。
//     *
//     * @param realName        真实姓名
//     * @param identityNumber  身份证号
//     * @param telephoneNumber 固定电话
//     * @param district        所在地区
//     * @param files           认证所需的图片
//     */
//    @RequestMapping(value = "/i/authentications/enterprise", method = RequestMethod.POST)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void authenticateEnterpriseMerchant(@RequestParam(value = "realName", required = false) String realName,
//                                               @RequestParam(value = "identityNumber", required = false) String identityNumber,
//                                               @RequestParam(value = "telephoneNumber", required = false) String telephoneNumber,
//                                               @RequestParam(value = "district", required = false) String district,
//                                               @RequestPart(value = "merchantFiles", required = false) MultipartFile[] files) {
//        merchantService.applyForEnterprise(realName, identityNumber, telephoneNumber, district, files);
//    }

    /**
     * 获得“我的实名认证”的状态信息。
     *
     * @return 获得“我的实名认证”的状态信息
     */
    @RequestMapping(value = "/i/authentications/real-name", method = RequestMethod.GET)
    public Map<String, String> getRealNameAuthentication() {
        Map<String, String> map = new HashMap<>();
        map.put("status", String.valueOf(merchantService.getApprovedRealNameStatus()));
        return map;
    }

    /**
     * 提交“我的实名认证”信息。
     *
     * @param realName       真实姓名
     * @param identityNumber 身份证号
     * @param files          认证所需的图片
     */
    @RequestMapping(value = "/i/authentications/real-name", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void authenticateRealName(@RequestParam(value = "realName", required = false) String realName,
                                     @RequestParam(value = "identityNumber", required = false) String identityNumber,
                                     @RequestPart(value = "realNameFiles", required = false) MultipartFile[] files) {
        merchantService.applyForRealName(realName, identityNumber, files);
    }

    /**
     * 校验“我是否经过实名认证”。
     *
     * @return 实名认证的结果，已经完成返回true，否则返回false
     */
    @RequestMapping(value = "/i/authentications/real-name/is-approved", method = RequestMethod.GET)
    public Map<String, String> hasApprovedRealName() {
        Map<String, String> map = new HashMap<>();
        map.put("isApproved", String.valueOf(merchantService.hasApprovedRealName()));
        return map;
    }
}
