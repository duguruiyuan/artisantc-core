package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “拍品的图片”。
 * Created by xinjie.li on 2016/9/23.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_image")
public class ItemImage extends BaseImage {

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_IMAGE_ITEM_ID"))
    private Item item;// 该图片所属的拍品的ID

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
