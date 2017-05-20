package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.ArtMomentService;
import cn.artisantc.core.service.UserService;
import cn.artisantc.core.web.rest.HttpHeaderConstants;
import cn.artisantc.core.web.rest.v1_0.vo.ICentreView;
import cn.artisantc.core.web.rest.v1_0.vo.IView;
import cn.artisantc.core.web.rest.v1_0.vo.UserAccountView;
import cn.artisantc.core.web.rest.v1_0.vo.UserPreferenceView;
import cn.artisantc.core.web.rest.v1_0.vo.UserProfileView;
import cn.artisantc.core.web.rest.v1_0.vo.UserQRCodeView;
import cn.artisantc.core.web.rest.v1_0.vo.UserShowImageView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.SearchUserViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.BindMobileRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.RetrievePasswordRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.SuggestionRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.UpdatePasswordRequest;
import cn.artisantc.core.web.rest.v1_0.vo.request.UpdateUserProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “用户”相关的操作的REST API。
 * Created by xinjie.li on 2016/8/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class UserFacade {

    private UserService userService;

    private ArtMomentService artMomentService;

    @Autowired
    public UserFacade(UserService userService, ArtMomentService artMomentService) {
        this.userService = userService;
        this.artMomentService = artMomentService;
    }

    /**
     * “我的”。
     *
     * @return “我的”需要的数据
     */
    @RequestMapping(value = "/i", method = RequestMethod.GET)
    public IView getMyHome() {
        return userService.findMyHome();
    }

    /**
     * 获得“我的附加信息”。
     *
     * @return “我的附加信息”
     */
    @RequestMapping(value = "/i/profile", method = RequestMethod.GET)
    public UserProfileView getMyProfile() {
        return userService.findMyProfile();
    }

    /**
     * 完善“个人资料”。
     *
     * @param files    个人头像
     * @param nickname 昵称
     * @param sex      性别
     * @param birthday 生日
     * @since 2.1
     */
    @RequestMapping(value = "/i/profile", method = RequestMethod.POST)
    public void completeMyProfile(@RequestPart(value = "avatarFile", required = false) MultipartFile[] files,
                                  @RequestParam(value = "nickname", required = false) String nickname,
                                  @RequestParam(value = "sex", required = false) String sex,
                                  @RequestParam(value = "birthday", required = false) String birthday) {
        userService.completeMyProfile(nickname, sex, birthday, files);
    }

    /**
     * 检查指定的“昵称”是否可用。
     *
     * @param nickname 待检查的“昵称”
     * @return “昵称”可用的时候返回“true”，否则返回“false”
     * @since 2.1
     */
    @RequestMapping(value = "/i/profile/nickname-available", method = RequestMethod.GET)
    public Map<String, String> validateNicknameAvailable(@RequestParam(value = "nickname", required = false) String nickname) {
        Map<String, String> map = new HashMap<>();
        map.put("available", String.valueOf(userService.validateNicknameAvailable(nickname)));

        return map;
    }

    /**
     * “我的个人中心”。
     *
     * @return “我的个人中心”需要的数据
     */
    @RequestMapping(value = "/i/centre", method = RequestMethod.GET)
    public ICentreView getMyCentre() {
        return userService.findPersonal();
    }

    /**
     * “我的艺文”的分页列表。
     *
     * @param page 分页
     * @return “我的艺文”的分页列表
     */
    @RequestMapping(value = "/i/centre/art-moments", method = RequestMethod.GET)
    public ArtMomentViewPaginationList getMyMoments(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artMomentService.findMyArtMomentsByPage(page);
    }

    /**
     * “他人的个人中心”。
     *
     * @param serialNumber 用户匠号
     * @return “他人的个人中心”需要的数据
     */
    @RequestMapping(value = "/users/{serialNumber}/centre", method = RequestMethod.GET)
    public ICentreView getCentreBySerialNumber(@PathVariable(value = "serialNumber") String serialNumber) {
        return userService.findPersonalBySerialNumber(serialNumber);
    }

    /**
     * “他人的艺文”的分页列表。
     *
     * @param serialNumber 用户匠号
     * @param page         分页
     * @return “我他人的艺文”的分页列表
     */
    @RequestMapping(value = "/users/{serialNumber}/centre/art-moments", method = RequestMethod.GET)
    public ArtMomentViewPaginationList getMomentsByUserSerialNumber(@PathVariable(value = "serialNumber") String serialNumber,
                                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return artMomentService.findArtMomentsBySerialNumberAndPage(serialNumber, page);
    }

    /**
     * 用户修改密码。
     *
     * @param updatePasswordRequest 修改密码时的请求参数的封装对象，具体参数请查看对象中的属性
     */
    @RequestMapping(value = "/i/password", method = RequestMethod.PATCH)
    public void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        userService.updatePassword(updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword());
    }

    /**
     * 用户找回密码，用于“忘记密码”。
     *
     * @param retrievePasswordRequest 找回密码时的请求参数的封装对象，具体参数请查看对象中的属性
     */
    @RequestMapping(value = "/i/password/retrieve", method = RequestMethod.PUT)
    public void retrievePassword(@RequestBody RetrievePasswordRequest retrievePasswordRequest, @RequestHeader("User-Agent") String userAgent) {
        userService.retrievePassword(retrievePasswordRequest.getMobile(), retrievePasswordRequest.getNewPassword(), retrievePasswordRequest.getSmsCaptcha(), userAgent);
    }

    /**
     * 获得指定艺文和其发布者的“用户倾向(偏好)”操作的视图，例如关注、收藏、屏蔽等等。
     *
     * @param momentId 艺文ID
     * @return 用户倾向(偏好)
     */
    @RequestMapping(value = "/i/preferences", method = RequestMethod.GET)
    public UserPreferenceView getPreferencesByMomentId(@RequestParam(value = "momentId", required = false) String momentId) {
        return userService.getPreferencesByMomentId(momentId);
    }

    /**
     * 获得指定“匠号”的用户的附加信息。
     *
     * @param serialNumber 匠号
     * @return 指定“匠号”的用户的附加信息
     */
    @RequestMapping(value = "/users/{serialNumber}/profile", method = RequestMethod.GET)
    public UserProfileView getUserProfile(@PathVariable(value = "serialNumber") String serialNumber) {
        return userService.findUserProfileBySerialNumber(serialNumber);
    }

    /**
     * 修改我的性别。
     *
     * @param updateUserProfileRequest 我要修改的性别
     */
    @RequestMapping(value = "/i/sex", method = RequestMethod.PUT)
    public void updateMySex(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        userService.updateMySex(updateUserProfileRequest.getSex());
    }

    /**
     * 修改我的昵称。
     *
     * @param updateUserProfileRequest 我要修改的昵称
     */
    @RequestMapping(value = "/i/nickname", method = RequestMethod.PUT)
    public void updateMyNickname(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        userService.updateMyNickname(updateUserProfileRequest.getNickname());
    }

    /**
     * 修改我的年龄。
     *
     * @param updateUserProfileRequest 我要修改的年龄
     */
    @RequestMapping(value = "/i/age", method = RequestMethod.PUT)
    public void updateMyAge(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        userService.updateMyAge(updateUserProfileRequest.getAge());
    }

    /**
     * 修改我的头像。
     *
     * @param files 我要修改的头像
     */
    @RequestMapping(value = "/i/avatar", method = RequestMethod.POST)
    public Map<String, String> updateMyAvatar(@RequestPart(value = "avatarFile", required = false) MultipartFile[] files) {
        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", userService.updateMyAvatar(files));
        return map;
    }

    /**
     * 获得我的头像(原始图片)。
     */
    @RequestMapping(value = "/i/avatar", method = RequestMethod.GET)
    public Map<String, String> getMyAvatar() {
        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", userService.getMyAvatar());
        return map;
    }

    /**
     * 意见反馈。
     *
     * @param suggestionRequest 意见反馈的请求对象
     */
    @RequestMapping(value = "/i/suggestion", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void suggest(@RequestBody SuggestionRequest suggestionRequest) {
        userService.suggest(suggestionRequest.getContent());
    }

    /**
     * 添加新的图片到我的“个人展示”。
     *
     * @param files 我要添加到“个人展示”的图片
     * @return 添加成功后的“个人展示”的图片的信息
     */
    @RequestMapping(value = "/i/shows", method = RequestMethod.POST)
    public Map<String, List<UserShowImageView>> updateMyShows(@RequestPart(value = "showFiles", required = false) MultipartFile[] files) {
        Map<String, List<UserShowImageView>> map = new HashMap<>();
        map.put("shows", userService.updateMyShows(files));
        return map;
    }

    /**
     * 获得“我的个人展示”的所有图片的信息。
     *
     * @return 我的个人展示”的所有图片的信息
     */
    @RequestMapping(value = "/i/shows", method = RequestMethod.GET)
    public Map<String, List<UserShowImageView>> getMyShows() {
        Map<String, List<UserShowImageView>> map = new HashMap<>();
        map.put("shows", userService.getMyShows());
        return map;
    }

    /**
     * 删除“我的个人展示”中指定的图片。
     */
    @RequestMapping(value = "/i/shows/{imageId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMyShowByImageId(@PathVariable(value = "imageId") String imageId) {
        userService.deleteMyShowByImageId(imageId);
    }

    /**
     * 获得指定“匠号”的用户的“个人展示”的所有图片的信息。
     *
     * @param serialNumber 用户匠号
     * @return 指定“匠号”的用户的“个人展示”的所有图片的信息
     */
    @RequestMapping(value = "/users/{serialNumber}/shows", method = RequestMethod.GET)
    public Map<String, List<UserShowImageView>> getShowsBySerialNumber(@PathVariable(value = "serialNumber") String serialNumber) {
        Map<String, List<UserShowImageView>> map = new HashMap<>();
        map.put("shows", userService.findShowsBySerialNumber(serialNumber));
        return map;
    }

    /**
     * 根据“关键字”查询用户。
     *
     * @param key  关键字
     * @param page 分页
     * @return 根据“关键字”查询用户
     */
    @RequestMapping(value = "/users/search/{key}", method = RequestMethod.GET)
    public SearchUserViewPaginationList getUsersBySearchKey(@PathVariable(value = "key") String key, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return userService.findUsersBySearchKey(key, page);
    }

    /**
     * 获得“我的二维码名片”。
     *
     * @return “我的二维码名片”
     * @since 1.2
     */
    @RequestMapping(value = "/i/qr-code", method = RequestMethod.GET)
    public UserQRCodeView getMyQRCode() {
        return userService.getMyQRCode();
    }

    /**
     * 绑定手机。
     *
     * @param bindMobileRequest 用户绑定手机号需要的数据的封装对象
     * @since 2.4
     */
    @RequestMapping(value = "/i/mobile", method = RequestMethod.PUT)
    public void bindMobile(@RequestBody BindMobileRequest bindMobileRequest, @RequestHeader(name = HttpHeaderConstants.USER_AGENT) String userAgent) {
        userService.bindMobile(bindMobileRequest.getMobile(), bindMobileRequest.getSmsCaptcha(), userAgent);
    }

    /**
     * 获得“我的钱包”的信息，用户的个人账户。有别于“商家的个人账户”。
     *
     * @return “我的钱包”的信息
     * @since 2.4
     */
    @RequestMapping(value = "/i/wallet", method = RequestMethod.GET)
    public UserAccountView getMyAccount() {
        return userService.getMyAccount();
    }

    /**
     * 修改我的生日。
     *
     * @param updateUserProfileRequest 我要修改的生日
     * @since 2.5
     */
    @RequestMapping(value = "/i/birthday", method = RequestMethod.PUT)
    public void updateMyBirthday(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        userService.updateMyBirthday(updateUserProfileRequest.getBirthday());
    }

    /**
     * 修改我的个人简介。
     *
     * @param updateUserProfileRequest 我要修改的个人简介
     * @since 2.5
     */
    @RequestMapping(value = "/i/personal-introduction", method = RequestMethod.PUT)
    public void updateMyPersonalIntroduction(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        userService.updateMyPersonalIntroduction(updateUserProfileRequest.getPersonalIntroduction());
    }

    /**
     * 修改我的个性签名。
     *
     * @param updateUserProfileRequest 我要修改的个性签名
     * @since 2.5
     */
    @RequestMapping(value = "/i/personalized-signature", method = RequestMethod.PUT)
    public void updateMyPersonalizedSignature(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        userService.updateMyPersonalizedSignature(updateUserProfileRequest.getPersonalizedSignature());
    }
}
