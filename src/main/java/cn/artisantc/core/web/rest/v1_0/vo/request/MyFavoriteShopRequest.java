package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “收藏店铺”的请求对象。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteShopRequest {

    private String serialNumber;// 店铺号

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
