package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * “用户的打赏订单”。
 * Created by xinjie.li on 2017/1/19.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Entity
@Table(name = "t_user_reward_order")
public class UserRewardOrder extends BasePaymentOrder {

    @ManyToOne
    @JoinColumn(name = "sender_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_REWARD_ORDER_SENDER_USER_ID"))
    private User sender;// 薪赏的打赏者

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_REWARD_ORDER_RECEIVER_USER_ID"))
    private User receiver;// 薪赏的接收者

    @Column(name = "leave_message", length = 60)
    private String leaveMessage = "";// 打赏者的留言

    @ManyToOne
    @JoinColumn(name = "art_moment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_REWARD_ORDER_ART_MOMENT_ID"))
    private ArtMoment artMoment;// 打赏的艺文

    @Column(name = "receiver_account_balance", precision = 14, scale = 2)
    private BigDecimal receiverAccountBalance;// 薪赏的接收者的个人账户的余额，打赏成功时的一个瞬时值，做为冗余数据

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public ArtMoment getArtMoment() {
        return artMoment;
    }

    public void setArtMoment(ArtMoment artMoment) {
        this.artMoment = artMoment;
    }

    public BigDecimal getReceiverAccountBalance() {
        return receiverAccountBalance;
    }

    public void setReceiverAccountBalance(BigDecimal receiverAccountBalance) {
        this.receiverAccountBalance = receiverAccountBalance;
    }
}
