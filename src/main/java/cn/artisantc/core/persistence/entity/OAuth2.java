package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * “OAuth2的认证信息”。
 * Created by xinjie.li on 2016/12/23.
 *
 * @author xinjie.li
 * @since 2.0
 */
@Entity
@Table(name = "t_oauth2")
public class OAuth2 extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_OAUTH2_USER_ID"))
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "oauth_channel", length = EntityConstant.OAUTH_CHANNEL_LENGTH)
    private OAuthChannel oauthChannel;// 认证渠道，例如：QQ，微信，新浪微博等

    @Column(name = "oauth_id", length = EntityConstant.OAUTH_ID_LENGTH)
    private String oauthId;// 认证ID，可以理解为：openId、username

    @Column(name = "oauth_access_token", length = 200)
    private String oauthAccessToken;// 认证令牌，可以理解为密码

    /**
     * “认证渠道”枚举。
     */
    public enum OAuthChannel {
        /**
         * 系统的注册用户的方式登录。
         */
        LOCAL("text.label.oauth.channel.local"),
        /**
         * QQ的方式登录。
         */
        QQ("text.label.oauth.channel.qq"),
        /**
         * 新浪微博的方式登录。
         */
        WEIBO("text.label.oauth.channel.weibo"),
        /**
         * 微信的方式登录。
         */
        WEIXIN("text.label.oauth.channel.weixin");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        OAuthChannel(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OAuthChannel getOauthChannel() {
        return oauthChannel;
    }

    public void setOauthChannel(OAuthChannel oauthChannel) {
        this.oauthChannel = oauthChannel;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getOauthAccessToken() {
        return oauthAccessToken;
    }

    public void setOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }
}
