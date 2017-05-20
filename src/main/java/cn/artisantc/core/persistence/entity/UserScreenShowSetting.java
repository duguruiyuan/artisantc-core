package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “基于用户操作的显示界面的参数控制”。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Entity
@Table(name = "t_user_screen_show_setting")
public class UserScreenShowSetting extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_SCREEN_SHOW_SETTING_USER_ID"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "screen_show_setting_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_SCREEN_SHOW_SETTING_SCREEN_SHOW_SETTING_ID"))
    private ScreenShowSetting screenShowSetting;

    @Column(name = "is_show")
    private boolean isShow;// 控制该界面是否需要显示

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ScreenShowSetting getScreenShowSetting() {
        return screenShowSetting;
    }

    public void setScreenShowSetting(ScreenShowSetting screenShowSetting) {
        this.screenShowSetting = screenShowSetting;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
