package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * “收货地址”的基类。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseAddress extends BaseEntity {

    @Column(nullable = false, length = EntityConstant.MOBILE_LENGTH)
    private String mobile;// 手机号码

    @Column(nullable = false, length = 20)
    private String province;// 所在省份

    @Column(nullable = false, length = 40)
    private String city;// 所在城市

    @Column(nullable = false, length = 80)
    private String district;// 所在地区

    @Column(nullable = false, length = 200)
    private String address;// 详细地址

    @Column(nullable = false, length = EntityConstant.REAL_NAME_LENGTH)
    private String name;// 姓名

    @Column(length = 10)
    private String postcode;// 邮政编码

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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
