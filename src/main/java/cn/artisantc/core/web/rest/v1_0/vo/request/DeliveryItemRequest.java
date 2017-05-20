package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “商家发货的请求”的视图对象。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class DeliveryItemRequest {

    private String expressNumber;// 快递单号

    private String expressCompanyCode;// 快递公司编码

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressCompanyCode() {
        return expressCompanyCode;
    }

    public void setExpressCompanyCode(String expressCompanyCode) {
        this.expressCompanyCode = expressCompanyCode;
    }
}
