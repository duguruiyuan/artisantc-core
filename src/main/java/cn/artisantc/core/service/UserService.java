package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.web.rest.v1_0.vo.ICentreView;
import cn.artisantc.core.web.rest.v1_0.vo.IView;
import cn.artisantc.core.web.rest.v1_0.vo.UserAccountView;
import cn.artisantc.core.web.rest.v1_0.vo.UserPreferenceView;
import cn.artisantc.core.web.rest.v1_0.vo.UserProfileView;
import cn.artisantc.core.web.rest.v1_0.vo.UserQRCodeView;
import cn.artisantc.core.web.rest.v1_0.vo.UserShowImageView;
import cn.artisantc.core.web.rest.v1_0.vo.UserView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.SearchUserViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.SuggestionViewPaginationList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 支持“用户信息”操作的服务接口。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface UserService {
    /**
     * 注册新用户。
     *
     * @param mobile     手机号，必填
     * @param password   密码，必填
     * @param registerIp 注册IP，必填
     * @param smsCaptcha 短信验证码，必填
     * @param userAgent  从Request Header里获取到的“User-Agent”的值
     */
    void register(String mobile, String password, String registerIp, String smsCaptcha, String userAgent);

    /**
     * 获得第三方登录的用户数据，并将其注册成为系统的用户。
     *
     * @param oauthId          认证ID，必填
     * @param oauthAccessToken 认证令牌，必填
     * @param registerIp       注册IP，必填
     */
    User registerByChannel(String oauthId, String oauthAccessToken, String registerIp);

    /**
     * 修改密码。
     *
     * @param oldPassword 旧密码，必填
     * @param newPassword 新密码，必填
     */
    void updatePassword(String oldPassword, String newPassword);

    /**
     * 获得对指定艺文及其发布者的“用户倾向”。
     *
     * @param momentId 艺文ID
     * @return 对指定艺文及其发布者的“用户倾向”
     */
    UserPreferenceView getPreferencesByMomentId(String momentId);

    /**
     * 获得“我的个人中心”的数据。
     *
     * @return “我的个人中心”的数据
     */
    ICentreView findPersonal();

    /**
     * 获得“他人的个人中心”的数据。
     *
     * @param serialNumber 用户匠号
     * @return “他人的个人中心”的数据
     */
    ICentreView findPersonalBySerialNumber(String serialNumber);

    /**
     * 找回密码。
     *
     * @param mobile      手机号
     * @param newPassword 设置的新密码
     * @param smsCaptcha  短信验证码，必填
     * @param userAgent   从Request Header里获取到的“User-Agent”的值
     */
    void retrievePassword(String mobile, String newPassword, String smsCaptcha, String userAgent);

    /**
     * 获得“我的”的数据。
     *
     * @return “我的”的数据
     */
    IView findMyHome();

    /**
     * 指定“匠号”的用户的附加信息。
     *
     * @param serialNumber 匠号
     * @return 指定“匠号”的用户的附加信息
     */
    UserProfileView findUserProfileBySerialNumber(String serialNumber);

    /**
     * 修改我的性别。
     *
     * @param sex 我要修改的性别
     */
    void updateMySex(String sex);

    /**
     * 获得“我的附加信息”。
     *
     * @return “我的附加信息”
     */
    UserProfileView findMyProfile();

    /**
     * 修改我的昵称。
     *
     * @param nickname 我要修改的昵称
     */
    void updateMyNickname(String nickname);

    /**
     * 修改我的头像。
     *
     * @param files 我要修改的头像
     */
    String updateMyAvatar(MultipartFile[] files);

    /**
     * 修改我的年龄。
     *
     * @param age 我要修改的年龄
     */
    void updateMyAge(String age);

    /**
     * 获得我的头像(原始图片)。
     *
     * @return 我的头像(原始图片)
     */
    String getMyAvatar();

    /**
     * 意见反馈。
     *
     * @param content 意见反馈的内容
     */
    void suggest(String content);

    /**
     * 添加新的图片到我的“个人展示”。
     *
     * @param files 我要添加到“个人展示”的图片
     * @return 添加成功后的“个人展示”的图片的信息
     */
    List<UserShowImageView> updateMyShows(MultipartFile[] files);

    /**
     * 获得“我的个人展示”的所有图片的信息。
     *
     * @return 我的个人展示”的所有图片的信息
     */
    List<UserShowImageView> getMyShows();

    /**
     * 删除“我的个人展示”中指定的图片。
     *
     * @param imageId 图片ID
     */
    void deleteMyShowByImageId(String imageId);

    /**
     * 获得指定“匠号”的用户的“个人展示”的所有图片的信息。
     *
     * @param serialNumber 用户匠号
     * @return 指定“匠号”的用户的“个人展示”的所有图片的信息
     */
    List<UserShowImageView> findShowsBySerialNumber(String serialNumber);

    /**
     * 根据“关键字”查询用户。
     *
     * @param key  关键字
     * @param page 分页
     * @return 根据“关键字”查询用户
     */
    SearchUserViewPaginationList findUsersBySearchKey(String key, int page);

    /**
     * 获取“意见反馈”的分页列表。
     *
     * @param page 分页
     * @return “意见反馈”的分页列表
     */
    SuggestionViewPaginationList getSuggestions(int page);

    /**
     * 获得“我的二维码名片”。
     *
     * @return 我的二维码名片”
     * @since 1.2
     */
    UserQRCodeView getMyQRCode();

    /**
     * 完善“个人资料”。
     *
     * @param files    个人头像
     * @param nickname 昵称
     * @param sex      性别
     * @param birthday 生日
     * @since 2.1
     */
    void completeMyProfile(String nickname, String sex, String birthday, MultipartFile[] files);

    /**
     * 检查指定的“昵称”是否可用。
     *
     * @param nickname 昵称
     * @return 昵称”可用的时候返回“true”，否则返回“false”
     * @since 2.1
     */
    boolean validateNicknameAvailable(String nickname);

    /**
     * 获得“用户列表”。
     *
     * @param page 分页
     * @return “用户列表”
     * @since 2.1
     */
    SearchUserViewPaginationList findUsers(int page);

    /**
     * 获得指定“用户ID”的用户信息。
     *
     * @param id 用户ID
     * @return 指定“用户ID”的用户信息
     * @since 2.1
     */
    UserView findUserById(String id);

    /**
     * 绑定手机号。
     *
     * @param mobile     待绑定的手机号，必填
     * @param smsCaptcha 短信验证码，必填
     * @param userAgent  从Request Header里获取到的“User-Agent”的值
     * @since 2.4
     */
    void bindMobile(String mobile, String smsCaptcha, String userAgent);

    /**
     * 获得“我的钱包”的信息，用户的个人账户。
     *
     * @return “我的钱包”的信息
     * @since 2.4
     */
    UserAccountView getMyAccount();

    /**
     * 获得指定“用户ID”的“用户的钱包”的信息。
     *
     * @param userId 用户ID
     * @return 指定“用户ID”的“用户的钱包”的信息
     * @since 2.5
     */
    UserAccountView getUserAccountByUserId(long userId);

    /**
     * 修改我的生日。
     *
     * @param birthday 我要修改的生日
     * @since 2.5
     */
    void updateMyBirthday(String birthday);

    /**
     * 修改我的个人简介。
     *
     * @param personalIntroduction 我要修改的个人简介
     * @since 2.5
     */
    void updateMyPersonalIntroduction(String personalIntroduction);

    /**
     * 修改我的个性签名。
     *
     * @param personalizedSignature 我要修改的个性签名
     * @since 2.5
     */
    void updateMyPersonalizedSignature(String personalizedSignature);
}
