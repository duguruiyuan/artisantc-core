package cn.artisantc.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * “用户的个人展示的图片”。
 * Created by xinjie.li on 2016/11/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_user_show_image")
public class UserShowImage extends BaseImage {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_SHOW_IMAGE_USER_ID"))
    private User user;// “个人展示的图片”所属的用户

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
