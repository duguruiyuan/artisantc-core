package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “用户的二维码”的视图对象。
 * Created by xinjie.li on 2016/12/21.
 *
 * @author xinjie.li
 * @since 1.2
 */
public class UserQRCodeView {

    @JsonProperty(value = "userId")
    private String id;

    private String avatarFileUrl = "";// 我的头像的地址

    private String nickname;// 昵称

    private String serialNumber;// 匠号

    private String sex = "";// 性别

    private String qrCodeBase64 = "";// 二维码的Base64编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarFileUrl() {
        return avatarFileUrl;
    }

    public void setAvatarFileUrl(String avatarFileUrl) {
        this.avatarFileUrl = avatarFileUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }
}
