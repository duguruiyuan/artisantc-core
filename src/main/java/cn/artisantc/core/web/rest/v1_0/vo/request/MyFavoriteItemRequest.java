package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “收藏拍品”的请求对象。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyFavoriteItemRequest {

    private String itemId;// 拍品ID

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
