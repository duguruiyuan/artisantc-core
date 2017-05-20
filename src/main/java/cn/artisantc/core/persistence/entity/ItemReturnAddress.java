package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “退货地址”，卖家填写，在退货流程中显示给买家查看。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_return_address")
public class ItemReturnAddress extends BaseItemAddress {

    @Column(length = EntityConstant.ADDRESS_REMARK_LENGTH)
    private String remark;// 备注

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_RETURN_ADDRESS_USER_ID"))
    private User user;// 退给谁，指卖家

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
