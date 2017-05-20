package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “拍品订单的全部物流信息”的视图对象。
 * Created by xinjie.li on 2016/11/16.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderExpressView {

    private String status = "";// 物流信息的最新状态

    private String expressNumber = "";// 快递单号

    private String expressCompanyName = "";// 快递公司的名称

    private String coverUrl = "";// 拍品封面图片的URL地址

    @JsonProperty(value = "expresses")
    private List<ItemOrderExpressDetailView> expressDetailViews = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<ItemOrderExpressDetailView> getExpressDetailViews() {
        return expressDetailViews;
    }

    public void setExpressDetailViews(List<ItemOrderExpressDetailView> expressDetailViews) {
        this.expressDetailViews = expressDetailViews;
    }
}
