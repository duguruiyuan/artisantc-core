package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “拍品订单的支付订单”。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_payment_order")
public class ItemPaymentOrder extends BasePaymentOrder {

    @ManyToOne
    @JoinColumn(name = "item_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_PAYMENT_ORDER_ITEM_ORDER_ID"))
    private ItemOrder itemOrder;// 针对哪个“拍品订单”的支付订单

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_PAYMENT_ORDER_USER_ID"))
    private User user;// 谁的订单

    public ItemOrder getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(ItemOrder itemOrder) {
        this.itemOrder = itemOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
