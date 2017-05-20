package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “账单”的视图对象的基类。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class BaseBillView {

    @JsonProperty(value = "billId")
    private String id;// 账单ID

    private String title;// 账单标题

    private String createDateTime;// 账单时间

    private String amount;// 账单的金额

    private String category = "";// 账单的类型

    private String categoryCode = "";// 账单的类型的代码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
