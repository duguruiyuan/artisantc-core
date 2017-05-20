package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 拍品的一级分类”的视图对象。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemCategoryView {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "name")
    private String categoryName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "code")
    private String categoryCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "icon")
    private String iconUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "subCategories")
    private List<ItemSubCategoryView> subCategoryViews;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "total")
    private String totalItems;// 总记录数

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getIconUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(iconUrl);
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<ItemSubCategoryView> getSubCategoryViews() {
        return subCategoryViews;
    }

    public void setSubCategoryViews(List<ItemSubCategoryView> subCategoryViews) {
        this.subCategoryViews = subCategoryViews;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }
}
