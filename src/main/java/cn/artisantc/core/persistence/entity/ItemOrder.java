package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * “拍品的支订单”。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_order", uniqueConstraints = {@UniqueConstraint(name = "UK_ITEM_ORDER_ORDER_NUMBER", columnNames = {"order_number"})})
public class ItemOrder extends BaseOrder {

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_ORDER_ITEM_ID"))
    private Item item;// 针对哪个拍品的订单

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_ORDER_USER_ID"))
    private User user;// 谁下的订单，买家

    @Column(name = "express_fee", precision = 14, scale = 2)
    private BigDecimal expressFee = BigDecimal.ZERO;// 邮费

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status;// 订单状态

    @Enumerated(value = EnumType.STRING)
    @Column(length = 22)
    private Result result;// 订单结果

    @OneToOne
    @JoinColumn(name = "delivery_address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_ORDER_DELIVERY_ADDRESS_ID"))
    private ItemOrderDeliveryAddress deliveryAddress;// 订单的收货地址，买家提供的收货地址

    @OneToOne
    @JoinColumn(name = "return_address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_ORDER_RETURN_ADDRESS_ID"))
    private ItemOrderReturnAddress returnAddress;// 订单的退货地址，商家提供的退货地址

    @OneToOne
    @JoinColumn(name = "delivery_express_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_ORDER_DELIVERY_EXPRESS_ID"))
    private ItemOrderExpress deliveryExpress;// 收货的物流信息

    @OneToOne
    @JoinColumn(name = "return_express_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_ORDER_RETURN_EXPRESS_ID"))
    private ItemOrderExpress returnExpress;// 退货的物流信息

    /**
     * “拍品订单”的状态。
     */
    public enum Status {
        // 下面是订单正常流转的状态
        /**
         * 待付款，需要买家进行“支付”操作。
         */
        PENDING_PAY("text.order.status.pending.pay"),
        /**
         * 待发货，需要商家进行“发货”和“填写物流信息”操作。
         */
        PENDING_DELIVERY("text.order.status.pending.delivery"),
        /**
         * 待收货，需要买家进行“确认收货”操作，
         */
        PENDING_RECEIVING("text.order.status.pending.receiving"),
        /**
         * 已收货，买家进行“确认收货”操作后的订单状态，
         */
        RECEIVED("text.order.status.received"),

        // 下面是会造成订单关闭的状态
        /**
         * 退货申请中，由买家发起申请，需要商家进行“同意退货”操作。
         */
        PENDING_AGREE_RETURN("text.order.status.pending.agree.return"),
        /**
         * 卖家同意退货，买家可以进行“退货”操作。
         */
        AGREED_RETURN("text.order.status.agreed.return"),
        /**
         * 退货中，商家“同意退货”后，由买家进行“退货”和“填写退货物流信息”操作。
         */
        PENDING_RETURN("text.order.status.pending.return"),
        /**
         * 已退货，商家收到退货，执行了“确认退货”操作后的订单状态。
         */
        RETURNED("text.order.status.returned"),
        /**
         * 付款超时，买家进行“支付”操作超时。
         */
        TIMEOUT_PAY("text.order.status.timeout.pay"),
        /**
         * 发货超时，商家进行“发货”操作超时。
         */
        TIMEOUT_DELIVERY("text.order.status.timeout.delivery");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    /**
     * “拍品订单”的结果。
     */
    public enum Result {
        /**
         * 交易关闭，表示交易没有成功，因为各种原因导致交易失败，最后订单关闭。
         */
        TRANSACTION_CLOSED("text.order.result.transaction.closed"),
        /**
         * 交易成功。
         */
        TRANSACTION_SUCCESSFUL("text.order.result.transaction.end");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Result(String messageKey) {
            this.messageKey = messageKey;
        }
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ItemOrderDeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(ItemOrderDeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public ItemOrderExpress getDeliveryExpress() {
        return deliveryExpress;
    }

    public void setDeliveryExpress(ItemOrderExpress deliveryExpress) {
        this.deliveryExpress = deliveryExpress;
    }

    public ItemOrderReturnAddress getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(ItemOrderReturnAddress returnAddress) {
        this.returnAddress = returnAddress;
    }

    public ItemOrderExpress getReturnExpress() {
        return returnExpress;
    }

    public void setReturnExpress(ItemOrderExpress returnExpress) {
        this.returnExpress = returnExpress;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }
}
