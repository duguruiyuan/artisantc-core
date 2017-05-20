package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * “显示界面的参数控制”。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Entity
@Table(name = "t_screen_show_setting")
public class ScreenShowSetting extends BaseEntity {

    @Column(length = 50)
    private String name;// 需要控制的界面名称

    @Column(name = "is_show")
    private boolean isShow;// 控制该界面是否需要显示

    @Enumerated(value = EnumType.STRING)
    @Column(name = "show_category", length = 20)
    private ShowCategory showCategory;// 该控制属与哪种类型

    @Enumerated(value = EnumType.STRING)
    @Column(name = "screen_category", length = 20)
    private ScreenCategory screenCategory;// 界面属于那种类型

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status;// 该控制的状态

    @Column(length = 100)
    private String description;// 该控制的说明

    /**
     * “控制类型”枚举。
     */
    public enum ShowCategory {
        /**
         * 基于“系统”的控制。
         */
        SYSTEM("text.label.show.screen.show.category.system"),
        /**
         * 基于“用户”操作的控制。
         */
        USER("text.label.show.screen.show.category.user");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        ShowCategory(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    /**
     * “界面类型”枚举。
     */
    public enum ScreenCategory {
        /**
         * “引导”类型的界面，即在用户登录后出现的具有引导性质的界面。
         */
        GUIDE("text.label.show.screen.screen.category.guide");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        ScreenCategory(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    /**
     * “状态”枚举。
     */
    public enum Status {
        /**
         * “启用”，可以使用的状态。
         */
        ENABLED("text.label.show.screen.status.enabled"),

        /**
         * “禁用”，不能使用的状态。
         */
        DISABLED("text.label.show.screen.status.disabled");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        Status(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public ShowCategory getShowCategory() {
        return showCategory;
    }

    public void setShowCategory(ShowCategory showCategory) {
        this.showCategory = showCategory;
    }

    public ScreenCategory getScreenCategory() {
        return screenCategory;
    }

    public void setScreenCategory(ScreenCategory screenCategory) {
        this.screenCategory = screenCategory;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
