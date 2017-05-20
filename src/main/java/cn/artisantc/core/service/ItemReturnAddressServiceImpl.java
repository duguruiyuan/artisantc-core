package cn.artisantc.core.service;

import cn.artisantc.core.exception.ItemReturnAddressNotFoundException;
import cn.artisantc.core.persistence.entity.ItemReturnAddress;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ItemReturnAddressRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ItemReturnAddressView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “ItemReturnAddressService”接口的实现类。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class ItemReturnAddressServiceImpl implements ItemReturnAddressService {

    private OAuth2Repository oAuth2Repository;

    private ItemReturnAddressRepository returnAddressRepository;

    @Autowired
    public ItemReturnAddressServiceImpl(OAuth2Repository oAuth2Repository, ItemReturnAddressRepository returnAddressRepository) {
        this.oAuth2Repository = oAuth2Repository;
        this.returnAddressRepository = returnAddressRepository;
    }

    @Override
    public List<ItemReturnAddressView> findMyReturnAddresses() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        List<ItemReturnAddress> addresses = returnAddressRepository.findByUser_idOrderByIsDefaultDescIdDesc(user.getId());
        List<ItemReturnAddressView> views = new ArrayList<>();
        if (null != addresses && !addresses.isEmpty()) {
            for (ItemReturnAddress address : addresses) {
                views.add(this.getItemReturnAddressView(address));
            }
        }

        return views;
    }

    @Override
    public void createItemReturnAddresses(String name, String mobile, String province, String city, String district, String address, String remark, String postcode) {
        // 校验输入参数
        this.validateBeforeSave(name, mobile, province, city, district, address, remark, postcode);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 保存数据
        ItemReturnAddress returnAddress = new ItemReturnAddress();
        returnAddress.setUser(user);
        returnAddress.setName(name);
        returnAddress.setProvince(province);
        returnAddress.setCity(city);
        returnAddress.setDistrict(district);
        returnAddress.setAddress(address);
        returnAddress.setMobile(mobile);
        returnAddress.setDefault(returnAddressRepository.countByUser_idAndIsDefaultIsTrue(user.getId()) == 0);// 检查是否已经有默认的地址

        Date date = new Date();
        returnAddress.setCreateDateTime(date);
        returnAddress.setUpdateDateTime(date);

        if (StringUtils.isNotBlank(remark)) {
            returnAddress.setRemark(remark);
        }
        if (StringUtils.isNotBlank(postcode)) {
            returnAddress.setPostcode(postcode);
        }

        returnAddressRepository.save(returnAddress);
    }

    @Override
    public ItemReturnAddressView findMyReturnAddressById(String addressId) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“地址ID”
        ItemReturnAddress address = this.validateAddressId(addressId, user);

        return this.getItemReturnAddressView(address);
    }

    @Override
    public void setToDefaultReturnAddress(String addressId) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“地址ID”
        ItemReturnAddress address = this.validateAddressId(addressId, user);

        // 查找原来设置的“默认收货地址”
        ItemReturnAddress defaultAddress = returnAddressRepository.findByUser_idAndIsDefaultIsTrue(user.getId());
        if (null != defaultAddress) {
            defaultAddress.setDefault(Boolean.FALSE);
            returnAddressRepository.save(defaultAddress);
        }

        // 设置新的“默认收货地址”
        address.setDefault(Boolean.TRUE);
        returnAddressRepository.save(address);
    }

    @Override
    public void updateItemReturnAddresses(String addressId, String name, String mobile, String province, String city, String district, String address, String remark, String postcode) {
        // 校验输入参数
        this.validateBeforeSave(name, mobile, province, city, district, address, remark, postcode);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“地址ID”
        ItemReturnAddress oldAddress = this.validateAddressId(addressId, user);

        // 保存数据
        oldAddress.setName(name);
        oldAddress.setProvince(province);
        oldAddress.setCity(city);
        oldAddress.setDistrict(district);
        oldAddress.setAddress(address);
        oldAddress.setMobile(mobile);

        Date date = new Date();
        oldAddress.setUpdateDateTime(date);

        if (StringUtils.isNotBlank(remark)) {
            oldAddress.setRemark(remark);
        }
        if (StringUtils.isNotBlank(postcode)) {
            oldAddress.setPostcode(postcode);
        }

        returnAddressRepository.save(oldAddress);
    }

    @Override
    public void deleteItemReturnAddresses(String addressId) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“地址ID”
        ItemReturnAddress oldAddress = this.validateAddressId(addressId, user);
        if (null != oldAddress) {
            returnAddressRepository.delete(oldAddress.getId());
        }
    }

    /**
     * 将“ItemReturnAddress”转换为“ItemReturnAddressView”。
     *
     * @param address 待转换的“ItemReturnAddress”
     * @return 转换后的“ItemReturnAddressView”
     */
    private ItemReturnAddressView getItemReturnAddressView(ItemReturnAddress address) {
        ItemReturnAddressView view = new ItemReturnAddressView();
        if (null != address) {
            view.setAddress(address.getAddress());
            view.setCity(address.getCity());
            view.setDistrict(address.getDistrict());
            view.setIsDefault(String.valueOf(address.isDefault()));
            view.setMobile(address.getMobile());
            view.setProvince(address.getProvince());
            view.setUserId(String.valueOf(address.getUser().getId()));
            view.setName(address.getName());
            view.setAddressId(String.valueOf(address.getId()));
            if (StringUtils.isNotBlank(address.getRemark())) {
                view.setRemark(address.getRemark());
            }
            if (StringUtils.isNotBlank(address.getPostcode())) {
                view.setPostcode(address.getPostcode());
            }
        }

        return view;
    }

    /**
     * 校验“地址ID”
     *
     * @param addressId 地址ID
     * @param user      当前登录用户
     * @return 当“地址ID”校验通过后返回对应的“收货地址”
     */
    private ItemReturnAddress validateAddressId(String addressId, User user) {
        assert null != user;

        // 校验“地址ID”
        if (StringUtils.isBlank(addressId) || !NumberUtils.isParsable(addressId)) {
            throw new ItemReturnAddressNotFoundException("没有找到指定“addressId”对应的资源：" + addressId);
        }

        ItemReturnAddress address = returnAddressRepository.findByUser_idAndId(user.getId(), NumberUtils.toLong(addressId));
        if (null == address) {
            throw new ItemReturnAddressNotFoundException("没有找到指定“addressId”对应的资源：" + addressId);
        }

        return address;
    }

    /**
     * 在“新增/修改”收货地址前校验参数。
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
    private void validateBeforeSave(String name, String mobile, String province, String city, String district, String address, String remark, String postcode) {
        // 校验“姓名”
        if (StringUtils.isBlank(name) || (StringUtils.isNotBlank(name) && name.length() > APIErrorResponse.MAX_ADDRESS_NAME_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010133.getErrorCode());
        }

        // 校验“手机号”
        if (StringUtils.isBlank(mobile) || (StringUtils.isNotBlank(mobile) && mobile.length() > APIErrorResponse.MAX_ADDRESS_MOBILE_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010134.getErrorCode());
        }

        // 校验“所在省份”
        if (StringUtils.isBlank(province) || (StringUtils.isNotBlank(province) && province.length() > APIErrorResponse.MAX_ADDRESS_PROVINCE_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010135.getErrorCode());
        }

        // 校验“所在城市”
        if (StringUtils.isBlank(city) || (StringUtils.isNotBlank(city) && city.length() > APIErrorResponse.MAX_ADDRESS_CITY_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010136.getErrorCode());
        }

        // 校验“所在地区”
        if (StringUtils.isBlank(district) || (StringUtils.isNotBlank(district) && district.length() > APIErrorResponse.MAX_ADDRESS_DISTRICT_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010137.getErrorCode());
        }

        // 校验“详细地址”
        if (StringUtils.isBlank(address) || (StringUtils.isNotBlank(address) && address.length() > APIErrorResponse.MAX_ADDRESS_ADDRESS_LENGTH)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010138.getErrorCode());
        }

        // 校验“备注”
        if (StringUtils.isNotBlank(remark) && remark.length() > APIErrorResponse.MAX_ADDRESS_REMARK_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010139.getErrorCode());
        }

        // 校验“邮编”
        if (StringUtils.isNotBlank(postcode) && postcode.length() > APIErrorResponse.MAX_ADDRESS_POSTCODE_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010140.getErrorCode());
        }
    }
}
