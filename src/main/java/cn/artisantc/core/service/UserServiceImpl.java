package cn.artisantc.core.service;

import cn.artisantc.core.exception.MomentNotFoundException;
import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.MyBlock;
import cn.artisantc.core.persistence.entity.MyFavoriteArtMoment;
import cn.artisantc.core.persistence.entity.MyFollow;
import cn.artisantc.core.persistence.entity.OAuth2;
import cn.artisantc.core.persistence.entity.Role;
import cn.artisantc.core.persistence.entity.Suggestion;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserAccount;
import cn.artisantc.core.persistence.entity.UserProfile;
import cn.artisantc.core.persistence.entity.UserQRCode;
import cn.artisantc.core.persistence.entity.UserShowImage;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.helper.UserProfileHelper;
import cn.artisantc.core.persistence.repository.ArtBigShotRepository;
import cn.artisantc.core.persistence.repository.ArtMomentRepository;
import cn.artisantc.core.persistence.repository.MyBlockRepository;
import cn.artisantc.core.persistence.repository.MyFansRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteArtMomentRepository;
import cn.artisantc.core.persistence.repository.MyFollowRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.RoleRepository;
import cn.artisantc.core.persistence.repository.SuggestionRepository;
import cn.artisantc.core.persistence.repository.UserAccountRepository;
import cn.artisantc.core.persistence.repository.UserProfileRepository;
import cn.artisantc.core.persistence.repository.UserQRCodeRepository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.repository.UserShowImageRepository;
import cn.artisantc.core.persistence.specification.UserSpecification;
import cn.artisantc.core.util.AvatarUtil;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.util.QRCodeUtil;
import cn.artisantc.core.util.RandomUtil;
import cn.artisantc.core.util.RegexUtil;
import cn.artisantc.core.util.SMSUtil;
import cn.artisantc.core.util.WordEncoderUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.BaseUserView;
import cn.artisantc.core.web.rest.v1_0.vo.ICentreView;
import cn.artisantc.core.web.rest.v1_0.vo.IView;
import cn.artisantc.core.web.rest.v1_0.vo.SearchUserView;
import cn.artisantc.core.web.rest.v1_0.vo.SuggestionView;
import cn.artisantc.core.web.rest.v1_0.vo.UserAccountView;
import cn.artisantc.core.web.rest.v1_0.vo.UserPreferenceView;
import cn.artisantc.core.web.rest.v1_0.vo.UserProfileView;
import cn.artisantc.core.web.rest.v1_0.vo.UserQRCodeView;
import cn.artisantc.core.web.rest.v1_0.vo.UserShowImageView;
import cn.artisantc.core.web.rest.v1_0.vo.UserView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.SearchUserViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.SuggestionViewPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “UserService”接口的实现类。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service(value = "userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private UserProfileRepository userProfileRepository;

    private RoleRepository roleRepository;

    private ArtMomentRepository artMomentRepository;

    private MyBlockRepository blockRepository;

    private MyFollowRepository followRepository;

    private MyFavoriteArtMomentRepository favoriteArtMomentRepository;

    private MyFansRepository fansRepository;

    private MessageSource messageSource;

    private SuggestionRepository suggestionRepository;

    private UserShowImageRepository userShowImageRepository;

    private UserQRCodeRepository userQRCodeRepository;

    private OAuth2Repository oAuth2Repository;

    private ArtBigShotRepository artBigShotRepository;

    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, RoleRepository roleRepository, ArtMomentRepository artMomentRepository,
                           MyBlockRepository blockRepository, MyFollowRepository followRepository, MyFavoriteArtMomentRepository favoriteArtMomentRepository,
                           MessageSource messageSource, MyFansRepository fansRepository, SuggestionRepository suggestionRepository, UserShowImageRepository userShowImageRepository,
                           UserQRCodeRepository userQRCodeRepository, OAuth2Repository oAuth2Repository, ArtBigShotRepository artBigShotRepository,
                           UserAccountRepository userAccountRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.roleRepository = roleRepository;
        this.artMomentRepository = artMomentRepository;
        this.blockRepository = blockRepository;
        this.followRepository = followRepository;
        this.favoriteArtMomentRepository = favoriteArtMomentRepository;
        this.fansRepository = fansRepository;
        this.messageSource = messageSource;
        this.suggestionRepository = suggestionRepository;
        this.userShowImageRepository = userShowImageRepository;
        this.userQRCodeRepository = userQRCodeRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.artBigShotRepository = artBigShotRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public void register(String mobile, String password, String registerIp, String smsCaptcha, String userAgent) {
        // 校验“手机号”
        if (StringUtils.isBlank(mobile)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("无效的手机号：{}", mobile);
            }
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010010.getErrorCode());
        } else {
            User existUser = userRepository.findByMobile(mobile);
            if (null != existUser) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010011.getErrorCode());
            }
        }

        // 校验“密码”
        if (StringUtils.isBlank(password)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("无效的密码：{}", password);
            }
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010020.getErrorCode());
        } else {
            // 校验“密码”组成规则
            if (!RegexUtil.validatePassword(password)) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010021.getErrorCode());
            }
        }

        // 校验“短信验证码”：确保最后一步才进行，这样可以保证“短信验证码”的有效性，避免让用户重复的获取。
        if (!SMSUtil.verifySMSCaptcha(mobile, smsCaptcha, userAgent)) {
            // 验证失败，则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990010.getErrorCode());
        }

        // 创建新用户
        this.registerUser(mobile, password, registerIp);
    }

    @Override
    public User registerByChannel(String oauthId, String oauthAccessToken, String registerIp) {
        return this.registerUser(oauthId, oauthAccessToken, registerIp);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        // 校验“旧密码”
        if (StringUtils.isBlank(oldPassword)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("无效的密码：{}", oldPassword);
            }
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010022.getErrorCode());
        }

        // 校验“新密码”
        if (StringUtils.isBlank(newPassword)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010023.getErrorCode());
        } else {
            // 校验“新密码”组成规则
            if (!RegexUtil.validatePassword(newPassword)) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010021.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        OAuth2 oAuth2 = oAuth2Repository.findByUser_id(user.getId());
        if (null != oAuth2) {
            // 找到用户才执行下面密码更新的逻辑，否则忽略
            if (WordEncoderUtil.matchesWithBCrypt(oldPassword, oAuth2.getOauthAccessToken())) {// 校验“旧密码”
                oAuth2.setOauthAccessToken(WordEncoderUtil.encodePasswordWithBCrypt(newPassword));
                oAuth2.setUpdateDateTime(new Date());

                oAuth2Repository.save(oAuth2);// 保存“用户的基本信息”
            } else {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010024.getErrorCode());
            }
        }
    }

    @Override
    public UserPreferenceView getPreferencesByMomentId(String momentId) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“momentId”
        if (StringUtils.isBlank(momentId) || !NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 查找转发的“原文”
        ArtMoment artMoment = artMomentRepository.findOne(NumberUtils.toLong(momentId));
        if (null == artMoment) {
            // 没有找到给定“momentId”的艺文
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 构建返回数据
        UserPreferenceView view = new UserPreferenceView();

        List<MyBlock> blocks = blockRepository.findByI_mobileAndBlock_id(user.getMobile(), artMoment.getUser().getId());
        if (null != blocks && !blocks.isEmpty()) {
            view.setBlocked(Boolean.TRUE);// 已屏蔽
        }

        List<MyFavoriteArtMoment> favoriteArtMoments = favoriteArtMomentRepository.findByArtMoment_idAndUser_id(NumberUtils.toLong(momentId), user.getId());
        if (null != favoriteArtMoments && !favoriteArtMoments.isEmpty()) {
            view.setFavorite(Boolean.TRUE);// 已收藏
        }

        List<MyFollow> follows = followRepository.findByI_idAndFollow_id(user.getId(), artMoment.getUser().getId());
        if (null != follows && !follows.isEmpty()) {
            view.setFollowed(Boolean.TRUE);// 已关注
        }

        return view;
    }

    @Override
    public ICentreView findPersonal() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getICentreViewByUser(user);
    }

    @Override
    public IView findMyHome() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getIViewByUser(user);
    }

    @Override
    public ICentreView findPersonalBySerialNumber(String serialNumber) {
        // 校验“用户匠号”
        if (StringUtils.isBlank(serialNumber)) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }
        User user = userRepository.findBySerialNumber(serialNumber);
        if (null == user) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }

        return this.getICentreViewByUser(user);
    }

    @Override
    public void retrievePassword(String mobile, String newPassword, String smsCaptcha, String userAgent) {
        // 校验“手机号”
        if (StringUtils.isBlank(mobile)) {
            // “手机号”为空
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010010.getErrorCode());
        }

        // 校验“短信验证码”：确保最后一步才进行，这样可以保证“短信验证码”的有效性，避免让用户重复的获取。
        if (!SMSUtil.verifySMSCaptcha(mobile, smsCaptcha, userAgent)) {
            // 验证失败，则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990010.getErrorCode());
        }

        OAuth2 oAuth2 = oAuth2Repository.findByUser_mobile(mobile);
        if (null != oAuth2) {
            // 找到用户才执行下面密码更新的逻辑，否则忽略
            oAuth2.setOauthAccessToken(WordEncoderUtil.encodePasswordWithBCrypt(newPassword));
            oAuth2.setUpdateDateTime(new Date());

            oAuth2Repository.save(oAuth2);// 保存“认证”
        }
    }

    @Override
    public UserProfileView findUserProfileBySerialNumber(String serialNumber) {
        // 校验“用户匠号”
        if (StringUtils.isBlank(serialNumber)) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }
        User user = userRepository.findBySerialNumber(serialNumber);
        if (null == user) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }

        UserProfileView view = new UserProfileView();
        view.setNickname(user.getProfile().getNickname());

        // “头像”地址
        if (StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
            view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));
        }

        return view;
    }

    @Override
    public void updateMySex(String sex) {
        // 校验“性别”
        if (StringUtils.isBlank(sex)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010030.getErrorCode());
        } else {
            UserProfileHelper.validateSex(sex);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 执行修改“性别”操作
        user.getProfile().setSex(UserProfile.UserSex.valueOf(sex));
        userProfileRepository.save(user.getProfile());
    }

    @Override
    public UserProfileView findMyProfile() {
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        UserProfileView view = new UserProfileView();
        view.setNickname(user.getProfile().getNickname());

        // “头像”地址
        if (StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
            view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));
        }

        return view;
    }

    @Override
    public void updateMyNickname(String nickname) {
        // 校验“昵称”
        if (StringUtils.isBlank(nickname)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010034.getErrorCode());
        }
        this.validateNickname(nickname);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 要修改的昵称与当前昵称不一致时，进行更新的逻辑
        if (!user.getProfile().getNickname().equals(nickname)) {
            // 检查“昵称”是否被使用
            long count = userProfileRepository.countByNickname(nickname);
            if (count > 0) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010012.getErrorCode());
            }

            // 更新
            user.getProfile().setNickname(nickname);
            user.getProfile().setUpdateDateTime(new Date());
            userProfileRepository.save(user.getProfile());
        }
    }

    @Override
    public void updateMyAge(String age) {
        // 校验“年龄”
        if (StringUtils.isBlank(age) || !NumberUtils.isParsable(age) || NumberUtils.toInt(age) <= 0) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010035.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        user.getProfile().setAge(NumberUtils.toInt(age));
        userProfileRepository.save(user.getProfile());
    }

    @Override
    public String updateMyAvatar(MultipartFile[] files) {
        UserProfile profile = this.updateMyAvatarFile(files);

        profile = userProfileRepository.save(profile);

        // 构建返回数据
        return UserHelper.getAvatar3xUrl(profile.getUser());// 返回“3x图片”的地址，“原始图片”的地址通过另一个接口“getMyAvatar”单独获取
    }

    @Override
    public String getMyAvatar() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        // 构建返回数据
        return UserHelper.getAvatarUrl(user);
    }

    @Override
    public void suggest(String content) {
        if (StringUtils.isBlank(content) || content.length() > APIErrorResponse.MAX_SUGGESTION_CONTENT_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010036.getErrorCode());
        }

        Suggestion suggestion = new Suggestion();
        suggestion.setContent(content);

        Date date = new Date();
        suggestion.setCreateDateTime(date);
        suggestion.setUpdateDateTime(date);

        suggestionRepository.save(suggestion);
    }

    @Override
    public List<UserShowImageView> updateMyShows(MultipartFile[] files) {
        // 校验“上传图片”
        List<MultipartFile> uploadedFiles = new ArrayList<>();// 真正提交过来准备上传的图片
        if (null == files) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010037.getErrorCode());
        } else {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    if (!ImageUtil.isPicture(file)) {
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990031.getErrorCode());
                    }
                    uploadedFiles.add(file);
                }
            }
            if (uploadedFiles.size() < APIErrorResponse.MIN_USER_SHOW_IMAGE_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010037.getErrorCode());
            }
            if (uploadedFiles.size() > APIErrorResponse.MAX_USER_SHOW_IMAGE_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010038.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 删除掉已经存在的“个人展示”图片，好给新添加的图片腾出位置
        List<UserShowImage> userShowImages = userShowImageRepository.findByUser_serialNumberOrderByCreateDateTimeAscIdAsc(user.getSerialNumber());
        if (null != userShowImages && !userShowImages.isEmpty()) {
            if ((userShowImages.size() + uploadedFiles.size()) > APIErrorResponse.MAX_USER_SHOW_IMAGE_LENGTH) {// 如果用户的“个人展示”的图片数量大于系统规定的最大数量，则可能是出现了脏数据，删除掉这些脏数据
                int toDeleteCount = userShowImages.size() + uploadedFiles.size() - APIErrorResponse.MAX_USER_SHOW_IMAGE_LENGTH;// 要删除的旧数据的个数
                if (toDeleteCount < 0) {// 如果该值为负数，则置为0
                    toDeleteCount = 0;
                }
                for (int i = 0; i < toDeleteCount; i++) {// 执行“删除旧数据操作”
                    this.deleteUserShowImagesAndFile(userShowImages.get(i));
                }
            }
        }

        // 处理“图片”的上传
        // 进入“上传文件流程”
        List<File> toDeleteFiles = new ArrayList<>();// “待删除文件列表”，用于记录过程中产生的临时文件，在完成后删除掉
        long fileNamePrefix = System.currentTimeMillis();// 时间戳
        String commonFileNamePrefix = fileNamePrefix + "_" + user.getSerialNumber();// 文件名的公共前缀
        String path;// 图片的存储路径
        try {
            path = ImageUtil.geUserShowImageStorePath() + user.getSerialNumber() + File.separatorChar;
            LOG.debug("待上传图片的目标路径：{}", path);
        } catch (ConfigurationException e) {
            LOG.error("获取文件上传路径失败，文件上传操作终止！");
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
        }
        File folder = new File(path);// 存储文件夹
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                LOG.error("文件夹创建失败： {}", path);
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
        }

        // 上传图片
        List<UserShowImageView> userShowImageViews = new ArrayList<>();// 返回数据
        for (MultipartFile file : uploadedFiles) {
            if (!file.isEmpty()) {
                String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
                fileName = ImageUtil.replaceFileNameSpecialCharacters(fileName);
                int originalImageHeight, thumbnailImageHeight;// 图片的高度

                String originalFileName = commonFileNamePrefix + "_" + fileName + "_original.jpg";// 生成存储到本地的文件名，生成的是经过压缩的“原始图片”，并且图片类型固定为“jpg”，这个“原始图片”不是用户上传的真正的原始图片
                String thumbnailFileName = commonFileNamePrefix + "_" + fileName + "_thumbnail.jpg";// 相对于“原始图片”的“缩略图”

                int newOriginalWidth = ImageUtil.USER_SHOW_IMAGE_WIDTH;// “原始图片”的默认宽度
                int newThumbnailWidth = ImageUtil.USER_SHOW_IMAGE_THUMBNAIL_WIDTH;// “缩略图片”的默认宽度
                try {
                    // 上传图片
                    String tempFileName = commonFileNamePrefix + "_" + fileName + "_temp.jpg";// “用户上传的原始图片”的临时文件名
                    File tempUserFile = new File(path + tempFileName);// 将用户上传的文件存储起来，生成临时文件
                    file.transferTo(tempUserFile);// 将用户上传的图片存储到服务器本地
                    BufferedImage bufferedImage = ImageIO.read(tempUserFile);// 读取图片到BufferedImage

                    if (bufferedImage.getWidth() < ImageUtil.USER_SHOW_IMAGE_WIDTH) {// 若用户上传的图片的宽度小于系统的“默认宽度”，则使用用户上传的图片的宽度
                        newOriginalWidth = bufferedImage.getWidth();// 重新(经过压缩)生成的“原始图片”
                        newThumbnailWidth = bufferedImage.getWidth() / 2;// 相对于“原始图片”的“缩略图”
                    }
                    toDeleteFiles.add(tempUserFile);// 添加到“待删除文件列表”中
                    // 上传！
                    originalImageHeight = ImageUtil.zoomImageScale(bufferedImage, path + originalFileName, newOriginalWidth);// 重新(经过压缩)生成的“原始图片”
                    thumbnailImageHeight = ImageUtil.zoomImageScale(bufferedImage, path + thumbnailFileName, newThumbnailWidth);// 相对于“原始图片”的“缩略图”
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                }

                UserShowImage userShowImage = new UserShowImage();
                Date date = new Date();
                userShowImage.setCreateDateTime(date);
                userShowImage.setUpdateDateTime(date);
                userShowImage.setUser(user);
                userShowImage.setImageName(originalFileName);
                userShowImage.setImageWidth(newOriginalWidth);
                userShowImage.setImageHeight(originalImageHeight);
                userShowImage.setThumbnailName(thumbnailFileName);
                userShowImage.setThumbnailWidth(newThumbnailWidth);
                userShowImage.setThumbnailHeight(thumbnailImageHeight);

                userShowImage = userShowImageRepository.save(userShowImage);
                userShowImageViews.add(this.getUserShowImageView(userShowImage));// 添加到返回数据中
            }
        }
        // 删除临时文件
        for (File toDeleteFile : toDeleteFiles) {
            String toDelete = toDeleteFile.getAbsolutePath() + toDeleteFile.getName();
            if (toDeleteFile.delete()) {
                LOG.info("临时文件删除成功： {})", toDelete);
            } else {
                LOG.info("临时文件删除失败：： {})", toDelete);
            }
        }

        return userShowImageViews;
    }

    @Override
    public List<UserShowImageView> getMyShows() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getUserShowImageViews(userShowImageRepository.findByUser_serialNumberOrderByCreateDateTimeDescIdDesc(user.getSerialNumber()));
    }

    @Override
    public void deleteMyShowByImageId(String imageId) {
        // 校验“imageId”
        if (StringUtils.isBlank(imageId) || !NumberUtils.isParsable(imageId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010039.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        UserShowImage userShowImage = userShowImageRepository.findOne(NumberUtils.toLong(imageId));
        // 只能删除“自己”的个人展示的图片
        if (null != userShowImage && user.getId() == userShowImage.getUser().getId()) {
            this.deleteUserShowImagesAndFile(userShowImage);
        }
    }

    @Override
    public List<UserShowImageView> findShowsBySerialNumber(String serialNumber) {
        // 校验“用户匠号”
        if (StringUtils.isBlank(serialNumber)) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }
        User user = userRepository.findBySerialNumber(serialNumber);
        if (null == user) {
            throw new UsernameNotFoundException("没有找到到指定“用户匠号”的用户，操作终止！");
        }

        return this.getUserShowImageViews(userShowImageRepository.findByUser_serialNumberOrderByCreateDateTimeDescIdDesc(user.getSerialNumber()));
    }

    @Override
    public SearchUserViewPaginationList findUsersBySearchKey(String key, int page) {
        return this.findUsersBySpecification(UserSpecification.findUsersBySearchKey(key), page);
    }

    @Override
    public SuggestionViewPaginationList getSuggestions(int page) {
        PageUtil pageUtil = new PageUtil(PageUtil.USER_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<Suggestion> suggestionPage = suggestionRepository.findAll(pageable);
        LOG.debug("本次查询条件总共 {} 页。", suggestionPage.getTotalPages());

        List<Suggestion> suggestions = suggestionPage.getContent();
        SuggestionViewPaginationList paginationList = new SuggestionViewPaginationList();
        paginationList.setTotalPages(String.valueOf(suggestionPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(suggestionPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = suggestionPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (suggestionPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("suggestionPage.getNumber(): {}", suggestionPage.getNumber());
        LOG.debug("suggestionPage.getNumberOfElements(): {}", suggestionPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != suggestions) {
            List<SuggestionView> suggestionViews = new ArrayList<>();
            for (Suggestion suggestion : suggestions) {
                SuggestionView view = new SuggestionView();
                view.setContent(suggestion.getContent());
                view.setId(String.valueOf(suggestion.getId()));
                view.setCreateDateTime(DateTimeUtil.getPrettyDescription(suggestion.getCreateDateTime()));

                suggestionViews.add(view);
            }
            paginationList.getSuggestionViews().addAll(suggestionViews);
        }
        return paginationList;
    }

    @Override
    public UserQRCodeView getMyQRCode() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找用户的二维码
        UserQRCode userQRCode = userQRCodeRepository.findByUser_id(user.getId());
        if (null == userQRCode) {
            userQRCode = new UserQRCode();

            String qrCodeBase64 = QRCodeUtil.getQRCode(user.getSerialNumber(), UserHelper.getAvatar3xPath(user), QRCodeUtil.USER_QR_CODE_WIDTH);
            LOG.debug("qrCodeBase64({}): {}", qrCodeBase64.length(), qrCodeBase64);
            userQRCode.setQrCodeBase64(qrCodeBase64);
            userQRCode.setUser(user);

            Date date = new Date();
            userQRCode.setCreateDateTime(date);
            userQRCode.setUpdateDateTime(date);

            userQRCode = userQRCodeRepository.save(userQRCode);
        }

        // 构建返回数据
        UserQRCodeView view = new UserQRCodeView();
        view.setId(String.valueOf(user.getId()));
        view.setNickname(user.getProfile().getNickname());
        view.setQrCodeBase64(userQRCode.getQrCodeBase64());
        view.setSerialNumber(user.getSerialNumber());
        view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));// “头像”地址

        // 性别
        if (null != user.getProfile().getSex()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view.setSex(messageSource.getMessage(user.getProfile().getSex().getMessageKey(), null, request.getLocale()));
        }

        return view;
    }

    @Override
    public void completeMyProfile(String nickname, String sex, String birthday, MultipartFile[] files) {
        // 校验“昵称”
        if (StringUtils.isNotBlank(nickname)) {
            this.validateNickname(nickname);
        }

        // 校验“性别”
        if (StringUtils.isNotBlank(sex)) {
            UserProfileHelper.validateSex(sex);
        }

        // 校验“生日”
        Date birthdayDate = null;
        if (StringUtils.isNotBlank(birthday)) {
            birthdayDate = UserProfileHelper.validateBirthday(birthday);
        }

        // 执行“完善个人资料”操作
        UserProfile profile = this.updateMyAvatarFile(files);// 更新“头像”
        if (StringUtils.isNotBlank(sex)) {
            profile.setSex(UserProfile.UserSex.valueOf(sex));
        }
        if (StringUtils.isNotBlank(nickname)) {
            profile.setNickname(nickname);
        }
        if (null != birthdayDate) {
            profile.setBirthday(birthdayDate);
        }

        userProfileRepository.save(profile);
    }

    @Override
    public boolean validateNicknameAvailable(String nickname) {
        return this.isNicknameNotExist(nickname);
    }

    @Override
    public SearchUserViewPaginationList findUsers(int page) {
        return this.findUsersBySpecification(UserSpecification.findUsers(), page);
    }

    @Override
    public UserView findUserById(String id) {
        // 校验“ID”
        if (StringUtils.isBlank(id) || !NumberUtils.isParsable(id)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }

        // 查询“用户”
        User user = userRepository.findOne(NumberUtils.toLong(id));

        UserView view = new UserView();
        if (null != user) {
            try {
                view = (UserView) this.buildBaseUserView(user, UserView.class);
                view.setCreateDateTime(DateTimeUtil.getPrettyDescription(user.getCreateDateTime()));
                view.setFollowers(String.valueOf(followRepository.countByI_id(user.getId())));
            } catch (IllegalAccessException | InstantiationException e) {
                LOG.error(e.getMessage(), e);// 仅做日志记录，不需要做其他操作
            }
        }

        return view;
    }

    @Override
    public void bindMobile(String mobile, String smsCaptcha, String userAgent) {
        // 1、校验“手机号”
        if (StringUtils.isBlank(mobile)) {
            // “手机号”为空
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010010.getErrorCode());
        }

        // 2、校验“手机号”是否已被使用
        if (userRepository.countByMobile(mobile) > 0) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010011.getErrorCode());
        }

        // 3、校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 4、校验“短信验证码”：确保最后一步才进行，这样可以保证“短信验证码”的有效性，避免让用户重复的获取。
        if (!SMSUtil.verifySMSCaptcha(mobile, smsCaptcha, userAgent)) {
            // 验证失败，则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990010.getErrorCode());
        }

        // 5、执行“绑定手机号”操作
        // 5.1、绑定“手机号”
        Date date = new Date();
        user.setUpdateDateTime(date);
        user.setMobile(mobile);

        userRepository.save(user);

        // 5.2、找到对应的“认证信息”，如果是“系统注册用户”，则同步更新登录用的用户名，即达到“让用户使用新的手机号登录”的效果。
        OAuth2 oAuth2 = oAuth2Repository.findByUser_mobile(user.getMobile());
        if (OAuth2.OAuthChannel.LOCAL == oAuth2.getOauthChannel()) {
            oAuth2.setUpdateDateTime(date);
            oAuth2.setOauthId(mobile);

            oAuth2Repository.save(oAuth2);
        }
    }

    @Override
    public UserAccountView getMyAccount() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getUserAccountByUserId(user.getId());
    }

    @Override
    public UserAccountView getUserAccountByUserId(long userId) {
        // 校验“userId”
        User user = userRepository.findOne(userId);
        if (null == user) {
            // 没有找到指定“用户ID”对应的用户信息
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }

        // 获得“用户的个人账户”
        UserAccount userAccount = this.findUserAccountByUserId(user);

        // 构建返回数据
        UserAccountView view = new UserAccountView();
        view.setAmount(userAccount.getAmount().toString());
        return view;
    }

    @Override
    public void updateMyBirthday(String birthday) {
        // 校验“生日”
        Date birthdayDate = null;
        if (StringUtils.isNotBlank(birthday)) {
            birthdayDate = UserProfileHelper.validateBirthday(birthday);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 执行“更新生日”操作
        UserProfile profile = user.getProfile();
        if (null != birthdayDate) {
            profile.setBirthday(birthdayDate);
            profile.setUpdateDateTime(new Date());
        }

        userProfileRepository.save(profile);
    }

    @Override
    public void updateMyPersonalIntroduction(String personalIntroduction) {
        // 校验“personalIntroduction”
        if (StringUtils.isBlank(personalIntroduction)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010043.getErrorCode());
        } else {
            if (personalIntroduction.length() > APIErrorResponse.MAX_USER_PERSONAL_INTRODUCTION_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010043.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 执行“更新”操作
        UserProfile profile = user.getProfile();
        profile.setPersonalIntroduction(personalIntroduction);
        profile.setUpdateDateTime(new Date());

        userProfileRepository.save(profile);
    }

    @Override
    public void updateMyPersonalizedSignature(String personalizedSignature) {
        // 校验“personalizedSignature”
        if (StringUtils.isBlank(personalizedSignature)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010044.getErrorCode());
        } else {
            if (personalizedSignature.length() > APIErrorResponse.MAX_USER_PERSONALIZED_SIGNATURE_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010044.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 执行“更新”操作
        UserProfile profile = user.getProfile();
        profile.setPersonalizedSignature(personalizedSignature);
        profile.setUpdateDateTime(new Date());

        userProfileRepository.save(profile);
    }

    /**
     * 创建新用户的默认角色。
     *
     * @return 新用户的默认角色
     */
    private List<Role> generateDefaultUserRole() {
        return roleRepository.findByName("text.role.user");
    }

    /**
     * 将“User”转换为“ICentreView”。
     *
     * @param user 要访问的个人中心的所属用户
     * @return ICentreView
     */
    private ICentreView getICentreViewByUser(User user) {
        ICentreView iCentreView = new ICentreView();
        if (null != user) {
            iCentreView.setId(String.valueOf(user.getId()));
            iCentreView.setNickname(user.getProfile().getNickname());
            iCentreView.setSerialNumber(user.getSerialNumber());
            if (null != user.getProfile().getSex()) {
                iCentreView.setSex(user.getProfile().getSex().name());
            }

            iCentreView.setFollowers(String.valueOf(followRepository.countByI_id(user.getId())));
            iCentreView.setFans(String.valueOf(fansRepository.countByI_id(user.getId())));

            // “头像”地址
            if (StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
                iCentreView.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));
            }

            // 获得当前登录用户信息
            User currentLoginUser = userRepository.findByMobile(LoginUserUtil.getLoginUsername());
            // 判断：当前登录用户是否关注了该“个人中心的主人”
            List<MyFollow> myFollows = followRepository.findByI_idAndFollow_id(currentLoginUser.getId(), user.getId());
            if (null != myFollows && !myFollows.isEmpty()) {
                iCentreView.setIsFollowed("true");
            } else {
                iCentreView.setIsFollowed("false");
            }

            // “个人简介”
            if (StringUtils.isNotBlank(user.getProfile().getPersonalIntroduction())) {
                iCentreView.setPersonalIntroduction(user.getProfile().getPersonalIntroduction());
            }

            // “个性签名”
            if (StringUtils.isNotBlank(user.getProfile().getPersonalizedSignature())) {
                iCentreView.setPersonalizedSignature(user.getProfile().getPersonalizedSignature());
            }

            // “生日”
            if (null != user.getProfile().getBirthday()) {
                iCentreView.setBirthday(DateFormatUtils.format(user.getProfile().getBirthday(), DateTimeUtil.DATE_FORMAT_YEAR_MONTH_DAY));
            }
        }

        return iCentreView;
    }

    /**
     * 将“User”转换为“IView”。
     *
     * @param user User
     * @return IView
     */
    private IView getIViewByUser(User user) {
        IView iView = new IView();
        if (null != user) {
            iView.setId(String.valueOf(user.getId()));
            iView.setNickname(user.getProfile().getNickname());
            iView.setAge(String.valueOf(user.getProfile().getAge()));
            iView.setSerialNumber(user.getSerialNumber());

            if (null != user.getProfile().getSex()) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                iView.setSex(messageSource.getMessage(user.getProfile().getSex().getMessageKey(), null, request.getLocale()));
            }

            iView.setFollowers(String.valueOf(followRepository.countByI_id(user.getId())));
            iView.setFans(String.valueOf(fansRepository.countByI_id(user.getId())));
            iView.setMoments(String.valueOf(artMomentRepository.countByUser_id(user.getId())));

            // “头像”地址
            if (StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
                iView.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));
            }
        }

        return iView;
    }

    /**
     * 将“UserShowImage”转换为“UserShowImageView”
     *
     * @param userShowImage UserShowImage
     * @return UserShowImageView
     */
    private UserShowImageView getUserShowImageView(UserShowImage userShowImage) {
        UserShowImageView view = new UserShowImageView();
        if (null != userShowImage) {
            view.setImageId(String.valueOf(userShowImage.getId()));

            try {
                view.setImageUrl(ImageUtil.getUserShowImageUrlPrefix() + userShowImage.getUser().getSerialNumber() + "/" + userShowImage.getImageName());
                view.setThumbnailUrl(ImageUtil.getUserShowImageUrlPrefix() + userShowImage.getUser().getSerialNumber() + "/" + userShowImage.getThumbnailName());
            } catch (ConfigurationException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return view;
    }

    /**
     * todo:javadoc
     *
     * @param userShowImages
     * @return
     */
    private List<UserShowImageView> getUserShowImageViews(List<UserShowImage> userShowImages) {
        List<UserShowImageView> views = new ArrayList<>();
        if (null != userShowImages && !userShowImages.isEmpty()) {
            for (UserShowImage userShowImage : userShowImages) {
                views.add(this.getUserShowImageView(userShowImage));
            }
        }

        return views;
    }

    /**
     * todo:javadoc
     *
     * @param userShowImage
     */
    private void deleteUserShowImagesAndFile(UserShowImage userShowImage) {
        if (null == userShowImage) {
            return;
        }
        String path;// 图片的存储路径
        try {
            path = ImageUtil.geUserShowImageStorePath() + userShowImage.getUser().getSerialNumber() + File.separatorChar;
            LOG.debug("待上传图片的目标路径：{}", path);
        } catch (ConfigurationException e) {
            LOG.error("获取文件上传路径失败，文件上传操作终止！");
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
        }
        // 删除“原始图片”
        File toDeleteImage = new File(path + userShowImage.getImageName());
        if (toDeleteImage.delete()) {
            LOG.info("原始图片删除成功： {}", path + userShowImage.getImageName());
        } else {
            LOG.info("原始图片删除失败：： {}", path + userShowImage.getImageName());
        }
        // 删除“缩略图片”
        File toDeleteThumbnail = new File(path + userShowImage.getThumbnailName());
        if (toDeleteThumbnail.delete()) {
            LOG.info("缩略图片删除成功： {}", path + userShowImage.getThumbnailName());
        } else {
            LOG.info("缩略图片删除失败：： {}", path + userShowImage.getThumbnailName());
        }

        userShowImageRepository.delete(userShowImage.getId());
    }

    private User registerUser(String mobile, String password, String registerIp) {
        // 构建“用户的基本信息”的存储数据
        User user = new User();
        user.setSerialNumber(RandomUtil.generateSerialNumber());
        user.setMobile(mobile);
        user.setRegisterIp(registerIp);
        user.setRoles(this.generateDefaultUserRole());

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
        user.setCreateDateTime(date);
        user.setUpdateDateTime(date);

        user = userRepository.save(user);// 保存“用户的基本信息”

        // 构建“用户的附加信息”的存储数据
        UserProfile userProfile = new UserProfile();
        userProfile.setCreateDateTime(date);
        userProfile.setUpdateDateTime(date);
        userProfile.setNickname(user.getSerialNumber());
        userProfile.setAge(0);
        userProfile.setUser(user);
        userProfile.setAvatar(AvatarUtil.getDefaultAvatarFileName());
        userProfile.setAvatar3x(AvatarUtil.getDefaultAvatarFileName());

        userProfileRepository.save(userProfile);// 保存“用户的附加信息”

        // 构建“用户的认证信息”的存储数据
        OAuth2 oAuth2 = new OAuth2();
        oAuth2.setOauthId(mobile);
        oAuth2.setOauthAccessToken(WordEncoderUtil.encodePasswordWithBCrypt(password));// 密码加密
        oAuth2.setCreateDateTime(date);
        oAuth2.setUpdateDateTime(date);
        oAuth2.setOauthChannel(OAuth2.OAuthChannel.LOCAL);
        oAuth2.setUser(user);

        oAuth2Repository.save(oAuth2);

        return user;
    }

    private UserProfile updateMyAvatarFile(MultipartFile[] files) {
        // 校验新的“我的头像”
        List<MultipartFile> uploadedFiles = new ArrayList<>();// 真正提交过来准备上传的图片
        if (null == files) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010148.getErrorCode());
        } else {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    if (!ImageUtil.isPicture(file)) {
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990031.getErrorCode());
                    }
                    uploadedFiles.add(file);
                }
            }
            if (uploadedFiles.size() != APIErrorResponse.USER_AVATAR_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010148.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        MultipartFile file = uploadedFiles.iterator().next();// 获取上传的图像
        String path;// 头像的存储路径
        try {
            path = AvatarUtil.getStorePath() + user.getSerialNumber() + File.separatorChar;
        } catch (ConfigurationException e) {
            LOG.error("获取文件上传路径失败，文件上传操作终止！");
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
        }
        File folder = new File(path);// 用户头像的存储文件夹
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                LOG.error("文件夹创建失败： {}", path);
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
        }

        // 上传新的头像
        long fileNamePrefix = System.currentTimeMillis();
        String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
        fileName = ImageUtil.replaceFileNameSpecialCharacters(fileName);
        String commonFileNamePrefix = fileNamePrefix + "_" + user.getSerialNumber();// 文件名的公共前缀
        String tempFileName = commonFileNamePrefix + "_" + fileName + "_" + user.getSerialNumber() + "_temp.jpg";// 临时文件名
        String newFileName = commonFileNamePrefix + "_" + fileName + "_" + user.getSerialNumber() + "_original.jpg";// 生成存储到本地的文件名，生成的是压缩过后的“原始图片”，并且图片类型固定为“jpg”
        String newFile3XName = commonFileNamePrefix + "_" + fileName + "_" + user.getSerialNumber() + "_3x.jpg";// 生成存储到本地的文件名，生成的是压缩过后的“原始图片”，并且图片类型固定为“jpg”

        File tempFile = new File(path + tempFileName);// 将用户上传的文件存储起来，生成临时文件
        try {
            file.transferTo(tempFile);
            BufferedImage bufferedImage = ImageIO.read(tempFile);
            ImageUtil.zoomImageScale(bufferedImage, path + newFileName, ImageUtil.USER_AVATAR_WIDTH);// 重新(经过压缩)生成头像的“原始图片”
            ImageUtil.zoomImageScale(bufferedImage, path + newFile3XName, ImageUtil.USER_AVATAR_THUMBNAIL_WIDTH);// 重新(经过压缩)生成头像的“原始图片”

            // 删除临时文件
            if (tempFile.delete()) {
                LOG.info("头像的临时文件删除成功： {})", path + tempFileName);
            } else {
                LOG.info("头像的临时文件删除失败：： {})", path + tempFileName);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
        }

        // 如果不是系统默认的头像，则执行“删除原先头像”的操作
        if (!AvatarUtil.getDefaultAvatarFileName().equalsIgnoreCase(user.getProfile().getAvatar())) {
            File oldAvatar = new File(path + user.getProfile().getAvatar());
            if (oldAvatar.delete()) {
                LOG.info("原先的头像删除成功：{} (匠号： {})", path + user.getProfile().getAvatar(), user.getSerialNumber());
            } else {
                LOG.info("原先的头像删除失败：{} (匠号： {})", path + user.getProfile().getAvatar(), user.getSerialNumber());
            }
            File oldAvatar3x = new File(path + user.getProfile().getAvatar3x());
            if (oldAvatar3x.delete()) {
                LOG.info("原先的头像(3x图)删除成功：{} (匠号： {})", path + user.getProfile().getAvatar3x(), user.getSerialNumber());
            } else {
                LOG.info("原先的头像(3x图)删除失败：{} (匠号： {})", path + user.getProfile().getAvatar3x(), user.getSerialNumber());
            }
        }

        // 更新头像的文件名
        UserProfile profile = user.getProfile();
        profile.setUpdateDateTime(new Date());
        profile.setAvatar(newFileName);
        profile.setAvatar3x(newFile3XName);

        return profile;
    }

    /**
     * 校验“昵称”
     *
     * @param nickname 待校验的“昵称”
     */
    private void validateNickname(String nickname) {
        UserProfileHelper.validateNickname(nickname);
        if (!this.isNicknameNotExist(nickname)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010041.getErrorCode());
        }
    }

    /**
     * 判断指定的“昵称”是否不存在
     *
     * @param nickname 待判断的“昵称”
     * @return 当前仅当指定的“昵称”不存在时返回true，否则返回false
     */
    private boolean isNicknameNotExist(String nickname) {
        return userProfileRepository.countByNickname(nickname) == 0;
    }

    /**
     * 将“List<User>”转换为“SearchUserViewPaginationList”
     *
     * @param users          待转换的User列表
     * @param paginationList 转换的SearchUserViewPaginationList
     */
    private void buildSearchUserViewPaginationList(List<User> users, SearchUserViewPaginationList paginationList) {
        if (null != users) {
            List<SearchUserView> userViews = new ArrayList<>();
            for (User user : users) {
                SearchUserView view;
                try {
                    view = (SearchUserView) this.buildBaseUserView(user, SearchUserView.class);
                    userViews.add(view);
                } catch (IllegalAccessException | InstantiationException e) {
                    LOG.error(e.getMessage(), e);// 仅做日志记录，不需要做其他操作
                }
            }
            paginationList.getUserViews().addAll(userViews);
        }
    }

    /**
     * todo:javadoc
     *
     * @param specification
     * @param page
     * @return
     */
    private SearchUserViewPaginationList findUsersBySpecification(Specification<User> specification, int page) {
        PageUtil pageUtil = new PageUtil(PageUtil.USER_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<User> userPage = userRepository.findAll(specification, pageable);
        LOG.debug("本次查询条件总共 {} 页。", userPage.getTotalPages());

        List<User> users = userPage.getContent();
        SearchUserViewPaginationList paginationList = new SearchUserViewPaginationList();
        paginationList.setTotalPages(String.valueOf(userPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(userPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = userPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (userPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("userPage.getNumber(): {}", userPage.getNumber());
        LOG.debug("userPage.getNumberOfElements(): {}", userPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        this.buildSearchUserViewPaginationList(users, paginationList);
        return paginationList;
    }

    /**
     * todo:javadoc
     *
     * @param user
     * @param baseUserViewClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private BaseUserView buildBaseUserView(User user, Class<? extends BaseUserView> baseUserViewClass) throws IllegalAccessException, InstantiationException {
        BaseUserView view = baseUserViewClass.newInstance();
        view.setId(String.valueOf(user.getId()));
        view.setNickname(user.getProfile().getNickname());
        view.setSerialNumber(user.getSerialNumber());
        view.setMobile(UserHelper.getWrapperMobile(user.getMobile()));
        if (null != user.getProfile().getSex()) {
            view.setSex(user.getProfile().getSex().name());
            view.setMale(user.getProfile().getSex() == UserProfile.UserSex.MALE);
            view.setFemale(user.getProfile().getSex() == UserProfile.UserSex.FEMALE);

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view.setSexValue(messageSource.getMessage(user.getProfile().getSex().getMessageKey(), null, request.getLocale()));
        }

        view.setFans(String.valueOf(fansRepository.countByI_id(user.getId())));
        view.setArgBigShot(null != artBigShotRepository.findByUser_id(user.getId()));

        // “头像”地址
        if (StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
            view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));
        }

        // 获得当前登录用户信息
        User currentLoginUser = userRepository.findByMobile(LoginUserUtil.getLoginUsername());
        if (null != currentLoginUser) {
            // 判断：当前登录用户是否关注了该“个人中心的主人”
            List<MyFollow> myFollows = followRepository.findByI_idAndFollow_id(currentLoginUser.getId(), user.getId());
            if (null != myFollows && !myFollows.isEmpty()) {
                view.setIsFollowed("true");
            } else {
                view.setIsFollowed("false");
            }
        }
        return view;
    }

    private UserAccount findUserAccountByUserId(User user) {
        UserAccount userAccount = userAccountRepository.findByUser_id(user.getId());
        if (null == userAccount) {
            // 没有找到用户的“个人账户”，可能是之前没有创建，在此为用户创建做为容错
            userAccount = new UserAccount();
            userAccount.setUser(user);
            userAccount.setAmount(BigDecimal.ZERO);

            Date date = new Date();
            userAccount.setCreateDateTime(date);
            userAccount.setCreateDateTime(date);

            userAccount = userAccountRepository.save(userAccount);
        }
        return userAccount;
    }
}
