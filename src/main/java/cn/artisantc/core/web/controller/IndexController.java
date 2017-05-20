package cn.artisantc.core.web.controller;

import cn.artisantc.core.service.InformationService;
import cn.artisantc.core.service.MerchantAccountBillService;
import cn.artisantc.core.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * “管理端”的界面。
 * Created by xinjie.li on 2016/10/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/console")
public class IndexController {

    private MerchantService merchantService;

    private MerchantAccountBillService billService;

    private InformationService informationService;

    @Autowired
    public IndexController(MerchantService merchantService, MerchantAccountBillService billService, InformationService informationService) {
        this.merchantService = merchantService;
        this.billService = billService;
        this.informationService = informationService;
    }

    /**
     * 首页。
     *
     * @return 首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }

    /**
     * “登录”模态框的内容。
     *
     * @return “登录”模态框的内容
     */
    @RequestMapping(value = "/login-modal", method = RequestMethod.GET)
    public String toLogin() {
        return "modal/login_modal_content";
    }

    /**
     * 银行列表。
     *
     * @return 银行列表
     */
    @RequestMapping(value = "/banks", method = RequestMethod.GET)
    public String getBanks() {
        return "/banks";
    }

    /**
     * 待审核的拍品列表。
     *
     * @return 待审核的拍品列表
     */
    @RequestMapping(value = "/items/pending-review", method = RequestMethod.GET)
    public String getPendingReviewItems() {
        return "/items_pending_review";
    }

    /**
     * 待审核的拍品详情。
     *
     * @param itemId 待审核的拍品ID
     * @return 待审核的拍品详情
     */
    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public String getPendingReviewItemById(@PathVariable(value = "itemId") String itemId) {
        return "/items_pending_review_detail";
    }

    /**
     * 待审核的实名认证列表。
     *
     * @return 待审核的实名认证列表
     */
    @RequestMapping(value = "/real-name/pending-review", method = RequestMethod.GET)
    public String getPendingReviewRealNames() {
        return "/real_names_pending_review";
    }

    @RequestMapping(value = "/real-name/{realNameId}", method = RequestMethod.GET)
    public String getPendingReviewRealNameById(@PathVariable(value = "realNameId") String realNameId) {
        return "/real_names_pending_review_detail";
    }

    /**
     * 快递公司列表。
     *
     * @return 快递公司列表
     */
    @RequestMapping(value = "/express-companies", method = RequestMethod.GET)
    public String getExpressCompanies() {
        return "/express_companies";
    }

    /**
     * 资讯列表。
     *
     * @return 资讯列表
     */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String getInformation() {
        return "/information";
    }

    /**
     * 获得“新增资讯”页面的内容。
     *
     * @return “新增资讯”页面的内容
     */
    @RequestMapping(value = "/information-modal", method = RequestMethod.GET)
    public String toCreateInformation() {
        return "modal/information_create_modal_content";
    }

    /**
     * 资讯修改页面。
     *
     * @param id 资讯ID
     * @return 资讯修改页面
     */
    @RequestMapping(value = "/information/{id}", method = RequestMethod.GET)
    public String toUpdateInformationById(@PathVariable(value = "id") String id) {
        if (informationService.isDraft(id)) {
            return "/information_update";
        }

        return "forward:/web/information/" + id;
    }

    /**
     * 广告列表。
     *
     * @return 广告列表
     */
    @RequestMapping(value = "/advertisements", method = RequestMethod.GET)
    public String getAdvertisements() {
        return "/advertisements";
    }

    /**
     * 获得“新增广告”页面的内容。
     *
     * @return “新增广告”页面的内容
     */
    @RequestMapping(value = "/advertisement-modal", method = RequestMethod.GET)
    public String toCreateAdvertisement() {
        return "modal/advertisement_create_modal_content";
    }

    /**
     * “提现申请”列表。
     *
     * @return “提现申请”列表
     */
    @RequestMapping(value = "/withdrawal-balance", method = RequestMethod.GET)
    public String getWithdrawalBalance() {
        return "/withdrawal_balance";
    }

    /**
     * “提现申请”详情页面。
     *
     * @param billId 商户账单ID
     * @return “提现申请”详情页面
     */
    @RequestMapping(value = "/withdrawal-balance/{billId}", method = RequestMethod.GET)
    public String getWithdrawalBalanceById(@PathVariable(value = "billId") String billId, Model model) {
        model.addAttribute("category", "balance");
        return "withdrawal_detail";
    }

    /**
     * “转出保证金申请”列表。
     *
     * @return “转出保证金申请”列表
     */
    @RequestMapping(value = "/withdrawal-margin", method = RequestMethod.GET)
    public String getWithdrawalMargin() {
        return "/withdrawal_margin";
    }

    /**
     * “转出保证金申请”详情页面。
     *
     * @param billId 商户账单ID
     * @return “转出保证金申请”详情页面
     */
    @RequestMapping(value = "/withdrawal-margin/{billId}", method = RequestMethod.GET)
    public String getWithdrawalMarginById(@PathVariable(value = "billId") String billId, Model model) {
        model.addAttribute("category", "margin");
        return "/withdrawal_detail";
    }

    /**
     * “意见反馈”列表页面。
     *
     * @return “意见反馈”列表页面
     */
    @RequestMapping(value = "/suggestions", method = RequestMethod.GET)
    public String getSuggestions() {
        return "/suggestions";
    }

    /**
     * “修改密码”页面。
     *
     * @return “修改密码”页面
     */
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String toUpdatePassword() {
        return "password_update";
    }

    /**
     * “拍品订单的支付订单列表”页面。
     *
     * @return “拍品订单的支付订单列表”页面
     * @since 1.1
     */
    @RequestMapping(value = "/item-payment-orders", method = RequestMethod.GET)
    public String toItemPaymentOrders() {
        return "item_payment_orders";
    }

    /**
     * “拍品订单的支付订单”的详情页面。
     *
     * @param itemOrderNumber 拍品订单的订单号，不是支付订单的订单号
     * @return “拍品订单的支付订单”的详情页面
     * @since 1.1
     */
    @RequestMapping(value = "/item-payment-orders/{itemOrderNumber}", method = RequestMethod.GET)
    public String toItemPaymentOrdersDetail(@PathVariable(value = "itemOrderNumber") String itemOrderNumber) {
        return "item_payment_orders_detail";
    }

    /**
     * “拍品保证金的支付订单列表”页面。
     *
     * @return “拍品保证金的支付订单列表”页面
     * @since 1.1
     */
    @RequestMapping(value = "/item-margin-orders", method = RequestMethod.GET)
    public String toItemMarginOrders() {
        return "item_margin_orders";
    }

    /**
     * “商家保证金的支付订单列表”页面。
     *
     * @return “商家保证金的支付订单列表”页面
     * @since 1.1
     */
    @RequestMapping(value = "/merchant-margin-orders", method = RequestMethod.GET)
    public String toMerchantMarginOrders() {
        return "merchant_margin_orders";
    }

    /**
     * “拍品退款订单订单列表”页面。
     *
     * @return “拍品退款订单订单列表”页面
     * @since 1.1
     */
    @RequestMapping(value = "/item-refund-orders", method = RequestMethod.GET)
    public String toItemRefundOrders() {
        return "item_refund_orders";
    }

    /**
     * “拍品保证金的支付订单列表”页面。
     *
     * @return “拍品保证金的支付订单列表”页面
     * @since 1.1
     */
    @RequestMapping(value = "/item-margin-refund-orders", method = RequestMethod.GET)
    public String toItemMarginRefundOrders() {
        return "item_margin_refund_orders";
    }
}
