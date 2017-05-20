package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * “艺文”的基类，其子类有：“艺术圈的艺文”和“校园的艺文”。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseMoment extends BaseEntity {

    @Column(length = 400)
    private String content;// 发布艺文的内容

    @Column(length = 100)
    private String location;// 发布艺文时的地理为止

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Category category;// 艺文的类别

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.AVAILABLE;// 艺文的状态

    /**
     * “艺文的类别”枚举。
     */
    public enum Category {
        /**
         * 艺术圈。
         */
        ART,
        /**
         * 校园。
         */
        CAMPUS
    }

    /**
     * “艺文的状态”枚举。
     */
    public enum Status {
        /**
         * 有效，艺文在一般情况下的状态。
         */
        AVAILABLE,
        /**
         * 已删除，表明该艺文已经被其发布者删除。
         */
        DELETED
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
