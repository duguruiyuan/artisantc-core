package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * “融云的Token”，该“Token”用来做为用户通过融云发起聊天的安全凭证，其值为“融云”端生成。
 * Created by xinjie.li on 2016/10/10.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_rong_cloud_token")
public class RongCloudToken extends BaseEntity {

    @Column
    private String token;// 用户和“融云”通讯的令牌

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_RONG_CLOUD_TOKEN_USER_ID"))
    private User user;//

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
