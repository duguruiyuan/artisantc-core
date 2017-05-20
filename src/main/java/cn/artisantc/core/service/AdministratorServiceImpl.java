package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.persistence.repository.AdministratorPermissionRepository;
import cn.artisantc.core.persistence.repository.AdministratorRepository;
import cn.artisantc.core.persistence.repository.AdministratorRoleRepository;
import cn.artisantc.core.persistence.repository.BankRepository;
import cn.artisantc.core.persistence.repository.ExpressCompanyRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginRepository;
import cn.artisantc.core.persistence.repository.PermissionRepository;
import cn.artisantc.core.persistence.repository.RoleRepository;
import cn.artisantc.core.service.payment.ALiPayTool;
import cn.artisantc.core.util.AvatarUtil;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.RegexUtil;
import cn.artisantc.core.util.RongCloudUtil;
import cn.artisantc.core.util.SMSUtil;
import cn.artisantc.core.util.WordEncoderUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.EnvironmentView;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

/**
 * “AdministratorService”接口的实现类。
 * Created by xinjie.li on 2016/10/5.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service(value = "administratorServiceImpl")
@Transactional
public class AdministratorServiceImpl implements AdministratorService {

    private static final Logger LOG = LoggerFactory.getLogger(AdministratorServiceImpl.class);

    private AdministratorRepository administratorRepository;

    private BankRepository bankRepository;

    private ExpressCompanyRepository expressCompanyRepository;

    private AdministratorPermissionRepository administratorPermissionRepository;

    private AdministratorRoleRepository administratorRoleRepository;

    private PermissionRepository permissionRepository;

    private RoleRepository roleRepository;

    private MerchantMarginRepository merchantMarginRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository, BankRepository bankRepository, ExpressCompanyRepository expressCompanyRepository,
                                    AdministratorPermissionRepository administratorPermissionRepository, AdministratorRoleRepository administratorRoleRepository,
                                    PermissionRepository permissionRepository, RoleRepository roleRepository, MerchantMarginRepository merchantMarginRepository) {
        this.administratorRepository = administratorRepository;
        this.bankRepository = bankRepository;
        this.expressCompanyRepository = expressCompanyRepository;
        this.administratorPermissionRepository = administratorPermissionRepository;
        this.administratorRoleRepository = administratorRoleRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.merchantMarginRepository = merchantMarginRepository;
    }

    @Override
    public Administrator findWithRoleByUsername(String username) {
        Administrator administrator = administratorRepository.findByUsername(username);
        if (null != administrator) {
            administrator.getRoles().size();
        }
        return administrator;
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

        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        if (null != administrator) {
            // 找到用户才执行下面密码更新的逻辑，否则忽略
            if (WordEncoderUtil.matchesWithBCrypt(oldPassword, administrator.getPassword())) {// 校验“旧密码”
                administrator.setPassword(WordEncoderUtil.encodePasswordWithBCrypt(newPassword));
                administrator.setUpdateDateTime(new Date());

                administratorRepository.save(administrator);// 保存“用户的基本信息”
            } else {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010024.getErrorCode());
            }
        }
    }

    @Override
    public EnvironmentView getEnvironments() {
        EnvironmentView view = new EnvironmentView();

        // “短信”
        view.setSmsAppKeyIOS(ConverterUtil.getWrapperKey(SMSUtil.SMS_APP_KEY_IOS));
        view.setSmsAppKeyAndroid(ConverterUtil.getWrapperKey(SMSUtil.SMS_APP_KEY_ANDROID));

        // “即时消息”
        view.setMessageAppKey(ConverterUtil.getWrapperKey(RongCloudUtil.APP_KEY));
        view.setMessageAppSecret(ConverterUtil.getWrapperKey(RongCloudUtil.APP_SECRET));
        view.setMessageDomain(RongCloudUtil.DOMAIN);
        view.setMessageUri(RongCloudUtil.URI);

        // “服务器”
        view.setOs(SystemUtils.OS_NAME + " (" + SystemUtils.OS_VERSION + ", " + SystemUtils.OS_ARCH + ")");
        view.setJavaVersion(SystemUtils.JAVA_VERSION);

        // “支付宝”
        view.getaLiPaySetting().setAppId(ConverterUtil.getWrapperKey(ALiPayTool.APP_ID));
        view.getaLiPaySetting().setUrl(ALiPayTool.URL);
        view.getaLiPaySetting().setPrivateKey(ConverterUtil.getWrapperKey(ALiPayTool.PRIVATE_KEY));
        view.getaLiPaySetting().setPublicKey(ConverterUtil.getWrapperKey(ALiPayTool.PUBLIC_KEY));
        view.getaLiPaySetting().setSignType(ALiPayTool.SIGN_TYPE);
        view.getaLiPaySetting().setVersion(ALiPayTool.VERSION);
        view.getaLiPaySetting().setNotifyUrl(ALiPayTool.getNotifyUrl());
        view.getaLiPaySetting().setProductCode(ALiPayTool.PRODUCT_CODE);
        view.getaLiPaySetting().setSellerEmail(ALiPayTool.SELLER_EMAIL);
        view.getaLiPaySetting().setSellerId(ConverterUtil.getWrapperKey(ALiPayTool.SELLER_ID));

        // “上传文件”
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("upload-file.properties"));

            view.getUploadFileSetting().setAdvertisementStorePath(ImageUtil.getAdvertisementImageStorePath());
            view.getUploadFileSetting().setAdvertisementUrlAddress(config.getString("advertisement.image.url.address"));
            view.getUploadFileSetting().setAdvertisementUrlPath(config.getString("advertisement.image.url.path"));

            view.getUploadFileSetting().setAvatarStorePath(AvatarUtil.getStorePath());
            view.getUploadFileSetting().setAvatarUrlAddress(config.getString("avatar.url.address"));
            view.getUploadFileSetting().setAvatarUrlPath(config.getString("avatar.url.path"));

            view.getUploadFileSetting().setInformationStorePath(ImageUtil.getInformationImageStorePath());
            view.getUploadFileSetting().setInformationUrlAddress(config.getString("information.image.url.address"));
            view.getUploadFileSetting().setInformationUrlPath(config.getString("information.image.url.path"));

            view.getUploadFileSetting().setItemCategoryUrlAddress(config.getString("item.category.icon.address"));
            view.getUploadFileSetting().setItemCategoryUrlPath(config.getString("item.category.icon.path"));

            String merchantStorePath = config.getString("merchant.upload.path.windows");
            if (SystemUtils.IS_OS_LINUX) {
                merchantStorePath = config.getString("merchant.upload.path.linux");// 根据操作系统进行path的生成
            }
            view.getUploadFileSetting().setMerchantStorePath(merchantStorePath);
            view.getUploadFileSetting().setMerchantUrlAddress(config.getString("merchant.image.url.address"));
            view.getUploadFileSetting().setMerchantUrlPath(config.getString("merchant.image.url.path"));

            view.getUploadFileSetting().setMomentStorePath(ImageUtil.getMomentImageStorePath());
            view.getUploadFileSetting().setMomentUrlAddress(config.getString("image.url.address"));
            view.getUploadFileSetting().setMomentUrlPath(config.getString("image.url.path"));

            String realNameStorePath = config.getString("real.name.upload.path.windows");
            if (SystemUtils.IS_OS_LINUX) {
                realNameStorePath = config.getString("real.name.upload.path.linux");// 根据操作系统进行path的生成
            }
            view.getUploadFileSetting().setRealNameStorePath(realNameStorePath);
            view.getUploadFileSetting().setRealNameUrlAddress(config.getString("real.name.image.url.address"));
            view.getUploadFileSetting().setRealNameUrlPath(config.getString("real.name.image.url.path"));

            view.getUploadFileSetting().setUserShowStorePath(ImageUtil.geUserShowImageStorePath());
            view.getUploadFileSetting().setUserShowUrlAddress(config.getString("user.show.image.url.address"));
            view.getUploadFileSetting().setUserShowUrlPath(config.getString("user.show.image.url.path"));

            view.getUploadFileSetting().setWatermarkStorePath(ImageUtil.getWatermarkStorePath());
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }

        // “初始化数据”
        view.getInitializationData().setBankTotalRecords(String.valueOf(bankRepository.count()));
        view.getInitializationData().setExpressCompanyTotalRecords(String.valueOf(expressCompanyRepository.count()));
        view.getInitializationData().setAdministratorPermissionTotalRecords(String.valueOf(administratorPermissionRepository.count()));
        view.getInitializationData().setAdministratorRoleTotalRecords(String.valueOf(administratorRoleRepository.count()));
        view.getInitializationData().setUserPermissionTotalRecords(String.valueOf(permissionRepository.count()));
        view.getInitializationData().setUserRoleTotalRecords(String.valueOf(roleRepository.count()));
        view.getInitializationData().setMerchantMarginTotalRecords(String.valueOf(merchantMarginRepository.count()));

        return view;
    }
}
