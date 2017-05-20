package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * “订单的收货地址”，该订单生成时使用的“收货地址”信息，相当于做了数据冗余。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_order_delivery_address")
public class ItemOrderDeliveryAddress extends BaseItemAddress {
}
