package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “拍品”的浏览记录。
 * Created by xinjie.li on 2016/11/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_browse_record")
public class ItemBrowseRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ITEM_BROWSE_RECORD_ITEM_ID"))
    private Item item;// 被浏览的拍品

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ITEM_BROWSE_RECORD_USER_ID"))
    private User user;// 浏览的人

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
