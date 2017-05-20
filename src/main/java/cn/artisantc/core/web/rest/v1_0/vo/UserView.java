package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “用户信息”的视图对象。
 * Created by xinjie.li on 2017/1/3.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class UserView extends BaseUserView {

    private String createDateTime;// 用户的注册时间

    private String followers;// 用户关注的人数

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }
}
