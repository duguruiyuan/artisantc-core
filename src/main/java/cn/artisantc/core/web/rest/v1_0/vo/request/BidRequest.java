package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “对拍品进行出价竞拍”的请求视图对象。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BidRequest {

    private String bidPrice;// 竞拍价格

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }
}
