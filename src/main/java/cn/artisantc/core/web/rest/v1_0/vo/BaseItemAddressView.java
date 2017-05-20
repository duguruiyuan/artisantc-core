package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “拍品的收货、退货地址”的公共属性的视图对象。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BaseItemAddressView {

    private String userId;// 地址的主人，如果是“收货地址”就是指买家；如果是“退货地址”就是指卖家

    private String isDefault = "false";// 该地址是否是“默认使用的”

    private String mobile;// 手机号码

    private String province;// 所在省份

    private String city;// 所在城市

    private String district;// 所在地区

    private String address;// 详细地址

    private String name;// 姓名

    private String remark = "";// 备注

    private String addressId;// 地址ID

    private String postcode = "";// 邮政编码

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
