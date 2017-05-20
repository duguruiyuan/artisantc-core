package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * 用户的附加信息。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Entity
@Table(name = "t_user_profile",
        uniqueConstraints = {@UniqueConstraint(name = "UK_USER_PROFILE_NICKNAME", columnNames = {"nickname"})},
        indexes = {@Index(name = "IND_T_USER_PROFILE_NICKNAME", columnList = "nickname")})
public class UserProfile extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_PROFILE_USER_ID"))
    private User user;

    @Column(length = 20, nullable = false)
    private String nickname;// 昵称

    @Enumerated(value = EnumType.STRING)
    @Column(length = 6)
    private UserSex sex;// 性别

    @Column(length = 100)
    private String avatar;// 个人头像图片的文件名称

    @Column(name = "avatar_3x", length = 100)
    private String avatar3x;// 个人头像图片的3X图文件名称

    @Column(name = "real_name", length = EntityConstant.REAL_NAME_LENGTH)
    private String realName;// 真实姓名，在经过“实名认证”后会保存到这里

    @Column(name = "identity_number", length = EntityConstant.IDENTITY_NUMBER_LENGTH)
    private String identityNumber;// 身份证号，在经过“实名认证”后会保存到这里

    @Column
    private int age = 0;// 年龄

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column
    private Date birthday;// 生日

    @Column(name = "personal_introduction", length = 500)
    private String personalIntroduction;// 个人简介

    @Column(name = "personalized_signature", length = 100)
    private String personalizedSignature;// 个性签名

    /**
     * “用户性别”枚举。
     */
    public enum UserSex {
        /**
         * 男。
         */
        MALE("text.label.male"),
        /**
         * 女。
         */
        FEMALE("text.label.female");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        UserSex(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserSex getSex() {
        return sex;
    }

    public void setSex(UserSex sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar3x() {
        return avatar3x;
    }

    public void setAvatar3x(String avatar3x) {
        this.avatar3x = avatar3x;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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
