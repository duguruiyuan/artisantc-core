package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “拍品的保证金的支付订单”。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_margin_order")
public class ItemMarginOrder extends BasePaymentOrder {

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_MARGIN_ORDER_ITEM_ID"))
    private Item item;// 针对哪个拍品的订单

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_MARGIN_ORDER_USER_ID"))
    private User user;// 谁的订单

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
