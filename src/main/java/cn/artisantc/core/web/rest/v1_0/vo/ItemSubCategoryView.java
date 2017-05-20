package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “拍品的二级分类”的视图对象。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemSubCategoryView {

    @JsonProperty(value = "name")
    private String categoryName;

    @JsonProperty(value = "code")
    private String categoryCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "icon")
    private String iconUrl;

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
}
