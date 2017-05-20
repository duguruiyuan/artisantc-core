package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ItemReturnAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * “拍品的退货地址”的API。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class ItemReturnAddressFacade {

    private ItemReturnAddressService addressService;

    @Autowired
    public ItemReturnAddressFacade(ItemReturnAddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 获得所有的“我的退货地址”，商家专属接口。
     *
     * @return 所有的“我的退货地址”
     */
//    @RequestMapping(value = "/i/shop/addresses", method = RequestMethod.GET)
//    public Map<String, List<ItemReturnAddressView>> getItemReturnAddresses() {
//        Map<String, List<ItemReturnAddressView>> map = new HashMap<>();
//        map.put("addresses", addressService.findMyReturnAddresses());
//        return map;
//    }

    /**
     * “新增退货地址”，商家专属接口。
     *
     * @param createItemAddressRequest “新增退货地址”的请求参数的封装对象。
     */
//    @RequestMapping(value = "/i/shop/addresses", method = RequestMethod.POST)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void createItemReturnAddresses(@RequestBody CreateItemAddressRequest createItemAddressRequest) {
//        addressService.createItemReturnAddresses(createItemAddressRequest.getName(), createItemAddressRequest.getMobile(), createItemAddressRequest.getProvince(), createItemAddressRequest.getCity(), createItemAddressRequest.getDistrict(), createItemAddressRequest.getAddress(), createItemAddressRequest.getRemark(), createItemAddressRequest.getPostcode());
//    }

    /**
     * 获得指定“地址ID”的退货地址信息，商家专属接口。
     *
     * @param addressId 地址ID
     * @return 指定“地址ID”的退货地址信息
     */
//    @RequestMapping(value = "/i/shop/addresses/{addressId}", method = RequestMethod.GET)
//    public ItemReturnAddressView getItemReturnAddress(@PathVariable(value = "addressId") String addressId) {
//        return addressService.findMyReturnAddressById(addressId);
//    }

    /**
     * 将指定“地址ID”的退货地址设为“默认退货地址”，商家专属接口。
     *
     * @param addressId 地址ID
     */
//    @RequestMapping(value = "/i/shop/addresses/{addressId}", method = RequestMethod.PATCH)
//    public void setToDefault(@PathVariable(value = "addressId") String addressId) {
//        addressService.setToDefaultReturnAddress(addressId);
//    }

    /**
     * “修改退货地址”，修改指定“地址ID”的“我的退货地址”的信息，商家专属接口。
     *
     * @param addressId                地址ID
     * @param createItemAddressRequest “修改退货地址”的请求参数的封装对象。
     */
//    @RequestMapping(value = "/i/shop/addresses/{addressId}", method = RequestMethod.PUT)
//    public void updateItemReturnAddress(@PathVariable(value = "addressId") String addressId, @RequestBody CreateItemAddressRequest createItemAddressRequest) {
//        addressService.updateItemReturnAddresses(addressId, createItemAddressRequest.getName(), createItemAddressRequest.getMobile(), createItemAddressRequest.getProvince(), createItemAddressRequest.getCity(), createItemAddressRequest.getDistrict(), createItemAddressRequest.getAddress(), createItemAddressRequest.getRemark(), createItemAddressRequest.getPostcode());
//    }

    /**
     * “删除退货地址”，商家专属接口。
     *
     * @param addressId 地址ID
     */
//    @RequestMapping(value = "/i/shop/addresses/{addressId}", method = RequestMethod.DELETE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void deleteItemReturnAddress(@PathVariable(value = "addressId") String addressId) {
//        addressService.deleteItemReturnAddresses(addressId);
//    }
}
