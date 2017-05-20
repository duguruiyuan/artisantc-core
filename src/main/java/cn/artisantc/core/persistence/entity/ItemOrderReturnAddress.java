package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * “订单的退货地址”，该订单在发生“退货”流程时由卖家使用的“退货地址”信息，相当于做了数据冗余。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_order_return_address")
public class ItemOrderReturnAddress extends BaseItemAddress {
}
