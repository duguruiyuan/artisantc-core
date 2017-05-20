package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “快递单号验证结果”的视图对象。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ValidateExpressNumberView {

    private String expressNumber;// 快递单号

    private String expressCompanyCode;// 快递公司编码

    private String expressCompanyName;// 快递公司名称

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

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }
}
