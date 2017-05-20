package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “收货地址”，买家填写，在发货流程中显示给卖家查看。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_delivery_address")
public class ItemDeliveryAddress extends BaseItemAddress {

    @Column(length = EntityConstant.ADDRESS_REMARK_LENGTH)
    private String remark;// 备注

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_DELIVERY_ADDRESS_USER_ID"))
    private User user;// 发货给谁，指买家

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
