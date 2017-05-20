package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * “拍品的竞拍记录”。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 */
@Entity
@Table(name = "t_item_bid_record")
public class ItemBidRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_BID_RECORD_ITEM_ID"))
    private Item item;// 该竞拍记录所对应的拍品的ID

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_BID_RECORD_USER_ID"))
    private User user;// 该竞拍记录所对应的竞拍人

    @Column(name = "bid_price", precision = 14, scale = 2)
    private BigDecimal bidPrice = BigDecimal.ZERO;// 竞拍的价格

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

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }
}
