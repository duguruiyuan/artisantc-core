package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “我的拍品的计算数量”的视图对象。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyItemCountView {

    private String biddingItemCount = "0";// “我参与的竞拍中”的拍品数量

    private String winBidItemCount = "0";// “我参与的已拍下”的拍品数量

    private String failedBidItemCount = "0";// “我参与的已结束”的拍品数量

    private String serialNumber;// 匠号

    private String nickname;// 昵称

    private String avatarFileUrl = "";// 我的头像的地

    public String getBiddingItemCount() {
        return biddingItemCount;
    }

    public void setBiddingItemCount(String biddingItemCount) {
        this.biddingItemCount = biddingItemCount;
    }

    public String getWinBidItemCount() {
        return winBidItemCount;
    }

    public void setWinBidItemCount(String winBidItemCount) {
        this.winBidItemCount = winBidItemCount;
    }

    public String getFailedBidItemCount() {
        return failedBidItemCount;
    }

    public void setFailedBidItemCount(String failedBidItemCount) {
        this.failedBidItemCount = failedBidItemCount;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarFileUrl() {
        return avatarFileUrl;
    }

    public void setAvatarFileUrl(String avatarFileUrl) {
        this.avatarFileUrl = avatarFileUrl;
    }
}
