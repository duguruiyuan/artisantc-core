package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ItemDeliveryAddressService;
import cn.artisantc.core.web.rest.v1_0.vo.ItemDeliveryAddressView;
import cn.artisantc.core.web.rest.v1_0.vo.request.CreateItemAddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “拍品的收货地址”的API。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class ItemDeliveryAddressFacade {

    private ItemDeliveryAddressService addressService;

    @Autowired
    public ItemDeliveryAddressFacade(ItemDeliveryAddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 获得所有的“我的收货地址”。
     *
     * @return 所有的“我的收货地址”
     */
    @RequestMapping(value = "/i/addresses", method = RequestMethod.GET)
    public Map<String, List<ItemDeliveryAddressView>> getItemDeliveryAddresses() {
        Map<String, List<ItemDeliveryAddressView>> map = new HashMap<>();
        map.put("addresses", addressService.findMyDeliveryAddresses());
        return map;
    }

    /**
     * “新增收货地址”。
     *
     * @param createItemAddressRequest “新增收货地址”的请求参数的封装对象。
     */
    @RequestMapping(value = "/i/addresses", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createItemDeliveryAddresses(@RequestBody CreateItemAddressRequest createItemAddressRequest) {
        addressService.createItemDeliveryAddresses(createItemAddressRequest.getName(), createItemAddressRequest.getMobile(), createItemAddressRequest.getProvince(), createItemAddressRequest.getCity(), createItemAddressRequest.getDistrict(), createItemAddressRequest.getAddress(), createItemAddressRequest.getRemark(), createItemAddressRequest.getPostcode());
    }

    /**
     * 获得指定“地址ID”的收货地址信息。
     *
     * @param addressId 地址ID
     * @return 指定“地址ID”的收货地址信息
     */
    @RequestMapping(value = "/i/addresses/{addressId}", method = RequestMethod.GET)
    public ItemDeliveryAddressView getItemDeliveryAddress(@PathVariable(value = "addressId") String addressId) {
        return addressService.findMyDeliveryAddressById(addressId);
    }

    /**
     * 将指定“地址ID”的收货地址设为“默认收货地址”。
     *
     * @param addressId 地址ID
     */
    @RequestMapping(value = "/i/addresses/{addressId}", method = RequestMethod.PATCH)
    public void setToDefault(@PathVariable(value = "addressId") String addressId) {
        addressService.setToDefaultDeliveryAddress(addressId);
    }

    /**
     * “修改收货地址”，修改指定“地址ID”的“我的收货地址”的信息。
     *
     * @param addressId                地址ID
     * @param createItemAddressRequest “修改收货地址”的请求参数的封装对象。
     */
    @RequestMapping(value = "/i/addresses/{addressId}", method = RequestMethod.PUT)
    public void updateItemDeliveryAddress(@PathVariable(value = "addressId") String addressId, @RequestBody CreateItemAddressRequest createItemAddressRequest) {
        addressService.updateItemDeliveryAddresses(addressId, createItemAddressRequest.getName(), createItemAddressRequest.getMobile(), createItemAddressRequest.getProvince(), createItemAddressRequest.getCity(), createItemAddressRequest.getDistrict(), createItemAddressRequest.getAddress(), createItemAddressRequest.getRemark(), createItemAddressRequest.getPostcode());
    }

    /**
     * “删除收货地址”。
     *
     * @param addressId 地址ID
     */
    @RequestMapping(value = "/i/addresses/{addressId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItemDeliveryAddress(@PathVariable(value = "addressId") String addressId) {
        addressService.deleteItemDeliveryAddresses(addressId);
    }
}
