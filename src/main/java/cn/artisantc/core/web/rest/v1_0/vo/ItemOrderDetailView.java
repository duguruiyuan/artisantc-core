package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * “订单详情”的视图对象。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderDetailView {

    // 下面是“订单”信息
    private String orderNumber = "";// 订单号

    private String amount = "0";// 订单的金额

    private String createDateTime;// 订单的创建时间，也是中标的时间

    private String status;// 订单状态

    private String statusCode;// 订单状态代码

    private String result = "";// 订单结果

    private String resultCode = "";// 订单结果代码

    private String countdown;// 付款倒计时

    private String expressFee = "0";// 邮费

    // 下面是“拍品”信息
    @JsonProperty(value = "item")
    private ItemDetail itemDetail = new ItemDetail();

    // 下面是“收货地址”信息
    @JsonProperty(value = "deliveryAddress")
    private ItemOrderDeliveryAddressDetail deliveryAddressDetail = new ItemOrderDeliveryAddressDetail();

    // 下面是“退货货地址”信息
    @JsonProperty(value = "returnAddress")
    private ItemOrderReturnAddressDetail returnAddressDetail = new ItemOrderReturnAddressDetail();

    // 下面是“发货”的物流信息
    @JsonProperty(value = "express")
    private ItemOrderExpressDetailView deliveryExpressDetail = new ItemOrderExpressDetailView();

    // 下面是“退货”的物流信息
    @JsonProperty(value = "returnExpress")
    private ItemOrderExpressDetailView returnExpressDetail = new ItemOrderExpressDetailView();

    /**
     * 买家是否设置了“默认收货地址”。
     *
     * @return 当且仅当设置了“默认收货地址”时返回“true”，否则返回“false”
     */
    @JsonProperty(value = "hasDeliveryAddress")
    public String hasDeliveryAddress() {
        if (null != deliveryAddressDetail && StringUtils.isNotBlank(deliveryAddressDetail.getMobile())) {
            return "true";
        }
        return "false";
    }

    public class ItemDetail {

        private String title;// 拍品的标题

        private String coverUrl;// 拍品的封面图片的URL地址

        private String isFixed;// 拍品是否是“一口价”成交

        private String itemId;// 拍品ID

        private String expressFee = "0";// 邮费

        private String margin = "0";// 保证金

        private String bidPrice = "0";// 拍品的成交价格

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getIsFixed() {
            return isFixed;
        }

        public void setIsFixed(String isFixed) {
            this.isFixed = isFixed;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getExpressFee() {
            return expressFee;
        }

        public void setExpressFee(String expressFee) {
            this.expressFee = expressFee;
        }

        public String getMargin() {
            return margin;
        }

        public void setMargin(String margin) {
            this.margin = margin;
        }

        public String getBidPrice() {
            return bidPrice;
        }

        public void setBidPrice(String bidPrice) {
            this.bidPrice = bidPrice;
        }
    }

    private class AddressDetail {

        private String mobile = "";// 手机号码

        private String province = "";// 所在省份

        private String city = "";// 所在城市

        private String district = "";// 所在地区

        private String address = "";// 详细地址

        private String name = "";// 姓名

        private String postcode = "";// 邮政编码

        public void setAddressDetail(String mobile, String province, String city, String district, String address, String name, String postcode) {
            if (StringUtils.isNotBlank(mobile)) {
                this.mobile = mobile;
            }
            if (StringUtils.isNotBlank(province)) {
                this.province = province;
            }
            if (StringUtils.isNotBlank(city)) {
                this.city = city;
            }
            if (StringUtils.isNotBlank(district)) {
                this.district = district;
            }
            if (StringUtils.isNotBlank(address)) {
                this.address = address;
            }
            if (StringUtils.isNotBlank(name)) {
                this.name = name;
            }
            if (StringUtils.isNotBlank(postcode)) {
                this.postcode = postcode;
            }
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

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
    }

    public class ItemOrderDeliveryAddressDetail extends AddressDetail {
    }

    public class ItemOrderReturnAddressDetail extends AddressDetail {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public ItemOrderDeliveryAddressDetail getDeliveryAddressDetail() {
        return deliveryAddressDetail;
    }

    public void setDeliveryAddressDetail(ItemOrderDeliveryAddressDetail deliveryAddressDetail) {
        this.deliveryAddressDetail = deliveryAddressDetail;
    }

    public ItemOrderExpressDetailView getDeliveryExpressDetail() {
        return deliveryExpressDetail;
    }

    public void setDeliveryExpressDetail(ItemOrderExpressDetailView deliveryExpressDetail) {
        this.deliveryExpressDetail = deliveryExpressDetail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(String expressFee) {
        this.expressFee = expressFee;
    }

    public ItemOrderExpressDetailView getReturnExpressDetail() {
        return returnExpressDetail;
    }

    public void setReturnExpressDetail(ItemOrderExpressDetailView returnExpressDetail) {
        this.returnExpressDetail = returnExpressDetail;
    }

    public ItemOrderReturnAddressDetail getReturnAddressDetail() {
        return returnAddressDetail;
    }

    public void setReturnAddressDetail(ItemOrderReturnAddressDetail returnAddressDetail) {
        this.returnAddressDetail = returnAddressDetail;
    }
}
