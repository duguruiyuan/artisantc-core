package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “修改个人资料”的请求对象。
 * Created by xinjie.li on 2017/2/28.
 *
 * @author xinjie.li
 * @since 2.5
 */
public class UpdateUserProfileRequest {

    private String nickname;// 昵称

    private String sex;// 性别

    private String age;// 年龄

    private String birthday;// 生日

    private String personalIntroduction;// 个人简介

    private String personalizedSignature;// 个性签名

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPersonalIntroduction() {
        return personalIntroduction;
    }

    public void setPersonalIntroduction(String personalIntroduction) {
        this.personalIntroduction = personalIntroduction;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }
}
