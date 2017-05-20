package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.ItemDeliveryAddressView;

import java.util.List;

/**
 * 支持“拍品的退货地址”操作的服务接口。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface ItemDeliveryAddressService {

    /**
     * 获得所有的“我的收货地址”。
     *
     * @return 所有的“我的收货地址”
     */
    List<ItemDeliveryAddressView> findMyDeliveryAddresses();

    /**
     * “新增收货地址”。
     *
     * @param name     联系人
     * @param mobile   联系电话
     * @param province 所在身份
     * @param city     所在城市
     * @param district 所在地区
     * @param address  详细地址
     * @param remark   备注
     * @param postcode 邮编
     */
    void createItemDeliveryAddresses(String name, String mobile, String province, String city, String district, String address, String remark, String postcode);

    /**
     * 获得指定“地址ID”的收货地址信息。
     *
     * @param addressId 地址ID
     * @return 指定“地址ID”的收货地址信息
     */
    ItemDeliveryAddressView findMyDeliveryAddressById(String addressId);

    /**
     * 将指定“地址ID”的收货地址设为“默认收货地址”。
     *
     * @param addressId 地址ID
     */
    void setToDefaultDeliveryAddress(String addressId);

    /**
     * “修改收货地址”。
     *
     * @param addressId 待修改的收货地址的地址ID
     * @param name      联系人
     * @param mobile    联系电话
     * @param province  所在身份
     * @param city      所在城市
     * @param district  所在地区
     * @param address   详细地址
     * @param remark    备注
     * @param postcode  邮编
     */
    void updateItemDeliveryAddresses(String addressId, String name, String mobile, String province, String city, String district, String address, String remark, String postcode);

    /**
     * “删除收货地址”。
     *
     * @param addressId 地址ID
     */
    void deleteItemDeliveryAddresses(String addressId);
}
