package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * “拍品二级类别”。
 * Created by xinjie.li on 2016/9/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_sub_category", uniqueConstraints = {@UniqueConstraint(name = "UK_ITEM_SUB_CATEGORY_CATEGORY_CODE", columnNames = {"category_code"})})
public class ItemSubCategory extends BaseItemCategory {

    @ManyToOne
    @JoinColumn(name = "parent_item_category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ITEM_CATEGORY_PARENT_ITEM_CATEGORY_ID"))
    private ItemCategory parentCategory;// 父类别

    public ItemCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ItemCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
}
