package cn.artisantc.core.persistence.entity;


import cn.artisantc.core.persistence.EntityConstant;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * “拍品”。
 * Created by xinjie.li on 2016/9/23.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_item")
public class Item extends BaseEntity {

    @Column(length = 60)
    private String title;// 标题

    @Column(length = 4000)
    private String detail;// 详情

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    @OrderBy(value = "createDateTime DESC, id DESC")
    private List<ItemImage> images;// 详情中的图片

    @Enumerated(value = EnumType.STRING)
    @Column(length = EntityConstant.ITEM_CATEGORY_LENGTH)
    private BaseItemCategory.SubCategory category;// 拍品的分类，精确到“二级分类”

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "start_date_time")
    private Date startDateTime;// 拍卖开始时间

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "end_date_time")
    private Date endDateTime;// 拍卖截至时间

    @Column(name = "initial_price", precision = 14, scale = 2)
    private BigDecimal initialPrice = BigDecimal.ZERO;// 起拍价

    @Column(name = "raise_price", precision = 14, scale = 2)
    private BigDecimal raisePrice = BigDecimal.ZERO;// 加价幅度

    @Column(name = "free_express")
    private boolean freeExpress = Boolean.FALSE;// 包邮

    @Column(name = "express_fee", precision = 14, scale = 2)
    private BigDecimal expressFee = BigDecimal.ZERO;// 邮费

    @Column(name = "free_return")
    private boolean freeReturn = Boolean.FALSE;// 包退

    @Column(name = "reference_price", precision = 14, scale = 2)
    private BigDecimal referencePrice = BigDecimal.ZERO;// 参考价

    @Column(name = "fixed_price", precision = 14, scale = 2)
    private BigDecimal fixedPrice = BigDecimal.ZERO;// 一口价

    @Column(name = "fixed")
    private boolean fixed = Boolean.FALSE;// 是否是一口价成交

    @Column(precision = 14, scale = 2)
    private BigDecimal margin = BigDecimal.ZERO;// 保证金

    @Column(name = "bid_price", precision = 14, scale = 2)
    private BigDecimal bidPrice = BigDecimal.ZERO;// 成交价格

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status;// 拍品状态

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ITEM_SHOP_ID"))
    private Shop shop;// 该拍品属于哪个店铺

    /**
     * “拍品状态”枚举。
     */
    public enum Status {
        /**
         * 审核中。
         */
        PENDING_REVIEW("text.item.status.pending.review"),
        /**
         * 预展中。
         */
        PREVIEW("text.item.status.preview"),
        /**
         * 竞拍中。
         */
        BIDDING("text.item.status.bidding"),
        /**
         * 已结束。
         */
        END("text.item.status.end"),
        /**
         * 审核不通过。
         */
        REJECTED("text.item.status.rejected");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<ItemImage> getImages() {
        return images;
    }

    public void setImages(List<ItemImage> images) {
        this.images = images;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public BigDecimal getRaisePrice() {
        return raisePrice;
    }

    public void setRaisePrice(BigDecimal raisePrice) {
        this.raisePrice = raisePrice;
    }

    public boolean isFreeExpress() {
        return freeExpress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setFreeExpress(boolean freeExpress) {
        this.freeExpress = freeExpress;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BaseItemCategory.SubCategory getCategory() {
        return category;
    }

    public void setCategory(BaseItemCategory.SubCategory category) {
        this.category = category;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public boolean isFreeReturn() {
        return freeReturn;
    }

    public void setFreeReturn(boolean freeReturn) {
        this.freeReturn = freeReturn;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }
}
