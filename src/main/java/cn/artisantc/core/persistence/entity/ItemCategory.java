package cn.artisantc.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * “拍品一级类别”。
 * Created by xinjie.li on 2016/9/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item_category", uniqueConstraints = {@UniqueConstraint(name = "UK_ITEM_CATEGORY_CATEGORY_CODE", columnNames = {"category_code"})})
public class ItemCategory extends BaseItemCategory {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentCategory")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<ItemSubCategory> subCategories;

    public List<ItemSubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<ItemSubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
