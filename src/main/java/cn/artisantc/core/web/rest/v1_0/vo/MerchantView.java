package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “商家”的视图对象。
 * Created by xinjie.li on 2016/9/26.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantView {

    @JsonProperty(value = "merchantId")
    private String id;

    private String realName;// 真实姓名

    private String identityNumber;// 身份证号

    private String telephoneNumber;// 固定电话

    private String district;// 所在地区

    private String status;// 商家认证的状态

    private List<MerchantImageView> images = new ArrayList<>(3);// 商家认证必须提交的图片资料

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<MerchantImageView> getImages() {
        return images;
    }

    public void setImages(List<MerchantImageView> images) {
        this.images = images;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
