package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “修改店铺资料”请求的视图对象。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UpdateShopRequest {

    private String name;// 店铺名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
