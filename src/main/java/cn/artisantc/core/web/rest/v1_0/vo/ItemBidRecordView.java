package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “拍品的竞拍出价记录”的视图对象。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemBidRecordView {

    private String userAvatarUrl;// 该竞拍记录所对应的用户头像缩略图的URL

    private String nickname;// 该竞拍记录所对应的用户的昵称

    private String bidPrice;// 竞拍价格

    private String order;// 按照竞拍价格的排序

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
