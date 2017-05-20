package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “系统”的部分设置信息，主要是包括部分需要根据不同环境（开发环境、测试环境、生产环境等）进行切换的设置。
 * Created by xinjie.li on 2016/11/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class EnvironmentView {

    private String smsAppKeyIOS = "";// 短信功能的App Key，适用于IOS

    private String smsAppKeyAndroid = "";// 短信功能的App Key，适用于Android

    private String messageAppKey = "";// 即时消息功能的App Key

    private String messageAppSecret = "";// 即时消息功能的App Secret

    private String messageDomain = "";// 即时消息服务器的域名

    private String messageUri = "";// 即时消息功能的URI

    private String os = "";// 服务器的操作系统

    private String javaVersion = "";// 服务器的Java版本

    private ALiPaySetting aLiPaySetting = new ALiPaySetting();// “支付宝”的设置信息

    private UploadFileSetting uploadFileSetting = new UploadFileSetting();// “上传文件”的设置信息

    private InitializationData initializationData = new InitializationData();// “初始化数据”的设置信息

    public class ALiPaySetting {

        private String appId = "";// “支付宝”调用接口时使用的APP ID

        private String url = "";// “支付宝”调用接口时的请求地址

        private String privateKey = "";//  “支付宝”调用接口时使用的私钥

        private String publicKey = "";//  “支付宝”调用接口时使用的公钥

        private String signType = "";//  “支付宝”调用接口时使用的签名算法

        private String version = "";//  “支付宝”调用接口时使用的版本号

        private String notifyUrl = "";//  给“支付宝”的异步通知使用的回调地址

        private String productCode = "";// “支付宝”调用接口时使用的销售产品码

        private String sellerEmail = "";// “卖家支付宝用户号”

        private String sellerId = "";// “卖家支付宝账号”

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getSellerEmail() {
            return sellerEmail;
        }

        public void setSellerEmail(String sellerEmail) {
            this.sellerEmail = sellerEmail;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }
    }

    public class UploadFileSetting {

        /*
        “广告图片”
         */
        private String advertisementStorePath;// “广告图片”的存储路径

        private String advertisementUrlAddress;// “广告图片”的访问URL地址

        private String advertisementUrlPath;// “广告图片”的访问URL路径

        /*
        “用户头像”
         */
        private String avatarStorePath;// “用户头像”的存储路径

        private String avatarUrlAddress;// “用户头像”的访问URL地址

        private String avatarUrlPath;// “用户头像”的访问URL路径

        /*
        “资讯图片”
         */
        private String informationStorePath;// “资讯图片”的存储路径

        private String informationUrlAddress;// “资讯图片”的访问URL地址

        private String informationUrlPath;// “资讯图片”的访问URL路径

        /*
        “拍品分类图片”
         */
        private String itemCategoryUrlAddress;// “拍品分类图片”的访问URL地址

        private String itemCategoryUrlPath;// “拍品分类图片”的访问URL路径

        /*
        “商户图片”
         */
        private String merchantStorePath;// “商户图片”的存储路径

        private String merchantUrlAddress;// “商户图片”的访问URL地址

        private String merchantUrlPath;// “商户图片”的访问URL路径

        /*
        “艺文图片”
         */
        private String momentStorePath;// “艺文图片”的存储路径

        private String momentUrlAddress;// “艺文图片”的访问URL地址

        private String momentUrlPath;// “艺文图片”的访问URL路径

        /*
        “实名认证图片”
         */
        private String realNameStorePath;// “实名认证图片”的存储路径

        private String realNameUrlAddress;// “实名认证图片”的访问URL地址

        private String realNameUrlPath;// “实名认证图片”的访问URL路径

        /*
        “用户个人展示图片”
         */
        private String userShowStorePath;// “用户个人展示图片”的存储路径

        private String userShowUrlAddress;// “用户个人展示图片”的访问URL地址

        private String userShowUrlPath;// “用户个人展示图片”的访问URL路径

        /*
        “水印图片”
         */
        private String watermarkStorePath;// “水印图片”的存储路径

        public String getAdvertisementStorePath() {
            return advertisementStorePath;
        }

        public void setAdvertisementStorePath(String advertisementStorePath) {
            this.advertisementStorePath = advertisementStorePath;
        }

        public String getAdvertisementUrlAddress() {
            return advertisementUrlAddress;
        }

        public void setAdvertisementUrlAddress(String advertisementUrlAddress) {
            this.advertisementUrlAddress = advertisementUrlAddress;
        }

        public String getAdvertisementUrlPath() {
            return advertisementUrlPath;
        }

        public void setAdvertisementUrlPath(String advertisementUrlPath) {
            this.advertisementUrlPath = advertisementUrlPath;
        }

        public String getAvatarStorePath() {
            return avatarStorePath;
        }

        public void setAvatarStorePath(String avatarStorePath) {
            this.avatarStorePath = avatarStorePath;
        }

        public String getAvatarUrlAddress() {
            return avatarUrlAddress;
        }

        public void setAvatarUrlAddress(String avatarUrlAddress) {
            this.avatarUrlAddress = avatarUrlAddress;
        }

        public String getAvatarUrlPath() {
            return avatarUrlPath;
        }

        public void setAvatarUrlPath(String avatarUrlPath) {
            this.avatarUrlPath = avatarUrlPath;
        }

        public String getInformationStorePath() {
            return informationStorePath;
        }

        public void setInformationStorePath(String informationStorePath) {
            this.informationStorePath = informationStorePath;
        }

        public String getInformationUrlAddress() {
            return informationUrlAddress;
        }

        public void setInformationUrlAddress(String informationUrlAddress) {
            this.informationUrlAddress = informationUrlAddress;
        }

        public String getInformationUrlPath() {
            return informationUrlPath;
        }

        public void setInformationUrlPath(String informationUrlPath) {
            this.informationUrlPath = informationUrlPath;
        }

        public String getItemCategoryUrlAddress() {
            return itemCategoryUrlAddress;
        }

        public void setItemCategoryUrlAddress(String itemCategoryUrlAddress) {
            this.itemCategoryUrlAddress = itemCategoryUrlAddress;
        }

        public String getItemCategoryUrlPath() {
            return itemCategoryUrlPath;
        }

        public void setItemCategoryUrlPath(String itemCategoryUrlPath) {
            this.itemCategoryUrlPath = itemCategoryUrlPath;
        }

        public String getMerchantStorePath() {
            return merchantStorePath;
        }

        public void setMerchantStorePath(String merchantStorePath) {
            this.merchantStorePath = merchantStorePath;
        }

        public String getMerchantUrlAddress() {
            return merchantUrlAddress;
        }

        public void setMerchantUrlAddress(String merchantUrlAddress) {
            this.merchantUrlAddress = merchantUrlAddress;
        }

        public String getMerchantUrlPath() {
            return merchantUrlPath;
        }

        public void setMerchantUrlPath(String merchantUrlPath) {
            this.merchantUrlPath = merchantUrlPath;
        }

        public String getMomentStorePath() {
            return momentStorePath;
        }

        public void setMomentStorePath(String momentStorePath) {
            this.momentStorePath = momentStorePath;
        }

        public String getMomentUrlAddress() {
            return momentUrlAddress;
        }

        public void setMomentUrlAddress(String momentUrlAddress) {
            this.momentUrlAddress = momentUrlAddress;
        }

        public String getMomentUrlPath() {
            return momentUrlPath;
        }

        public void setMomentUrlPath(String momentUrlPath) {
            this.momentUrlPath = momentUrlPath;
        }

        public String getRealNameStorePath() {
            return realNameStorePath;
        }

        public void setRealNameStorePath(String realNameStorePath) {
            this.realNameStorePath = realNameStorePath;
        }

        public String getRealNameUrlAddress() {
            return realNameUrlAddress;
        }

        public void setRealNameUrlAddress(String realNameUrlAddress) {
            this.realNameUrlAddress = realNameUrlAddress;
        }

        public String getRealNameUrlPath() {
            return realNameUrlPath;
        }

        public void setRealNameUrlPath(String realNameUrlPath) {
            this.realNameUrlPath = realNameUrlPath;
        }

        public String getUserShowStorePath() {
            return userShowStorePath;
        }

        public void setUserShowStorePath(String userShowStorePath) {
            this.userShowStorePath = userShowStorePath;
        }

        public String getUserShowUrlAddress() {
            return userShowUrlAddress;
        }

        public void setUserShowUrlAddress(String userShowUrlAddress) {
            this.userShowUrlAddress = userShowUrlAddress;
        }

        public String getUserShowUrlPath() {
            return userShowUrlPath;
        }

        public void setUserShowUrlPath(String userShowUrlPath) {
            this.userShowUrlPath = userShowUrlPath;
        }

        public String getWatermarkStorePath() {
            return watermarkStorePath;
        }

        public void setWatermarkStorePath(String watermarkStorePath) {
            this.watermarkStorePath = watermarkStorePath;
        }
    }

    public class InitializationData {

        private String bankTotalRecords = "0";// “银行”的总记录数

        private String expressCompanyTotalRecords = "0";// “快递公司”的总记录数

        private String administratorRoleTotalRecords = "0";// “管理员角色”的总记录数

        private String administratorPermissionTotalRecords = "0";// “管理员权限”的总记录数

        private String userPermissionTotalRecords = "0";// “用户权限”的总记录数

        private String userRoleTotalRecords = "0";// “用户角色”的总记录数

        private String merchantMarginTotalRecords = "0";// “商家的保证金场”的总记录数

        public String getBankTotalRecords() {
            return bankTotalRecords;
        }

        public void setBankTotalRecords(String bankTotalRecords) {
            this.bankTotalRecords = bankTotalRecords;
        }

        public String getExpressCompanyTotalRecords() {
            return expressCompanyTotalRecords;
        }

        public void setExpressCompanyTotalRecords(String expressCompanyTotalRecords) {
            this.expressCompanyTotalRecords = expressCompanyTotalRecords;
        }

        public String getAdministratorRoleTotalRecords() {
            return administratorRoleTotalRecords;
        }

        public void setAdministratorRoleTotalRecords(String administratorRoleTotalRecords) {
            this.administratorRoleTotalRecords = administratorRoleTotalRecords;
        }

        public String getAdministratorPermissionTotalRecords() {
            return administratorPermissionTotalRecords;
        }

        public void setAdministratorPermissionTotalRecords(String administratorPermissionTotalRecords) {
            this.administratorPermissionTotalRecords = administratorPermissionTotalRecords;
        }

        public String getUserPermissionTotalRecords() {
            return userPermissionTotalRecords;
        }

        public void setUserPermissionTotalRecords(String userPermissionTotalRecords) {
            this.userPermissionTotalRecords = userPermissionTotalRecords;
        }

        public String getUserRoleTotalRecords() {
            return userRoleTotalRecords;
        }

        public void setUserRoleTotalRecords(String userRoleTotalRecords) {
            this.userRoleTotalRecords = userRoleTotalRecords;
        }

        public String getMerchantMarginTotalRecords() {
            return merchantMarginTotalRecords;
        }

        public void setMerchantMarginTotalRecords(String merchantMarginTotalRecords) {
            this.merchantMarginTotalRecords = merchantMarginTotalRecords;
        }
    }

    public String getSmsAppKeyIOS() {
        return smsAppKeyIOS;
    }

    public void setSmsAppKeyIOS(String smsAppKeyIOS) {
        this.smsAppKeyIOS = smsAppKeyIOS;
    }

    public String getSmsAppKeyAndroid() {
        return smsAppKeyAndroid;
    }

    public void setSmsAppKeyAndroid(String smsAppKeyAndroid) {
        this.smsAppKeyAndroid = smsAppKeyAndroid;
    }

    public String getMessageAppKey() {
        return messageAppKey;
    }

    public void setMessageAppKey(String messageAppKey) {
        this.messageAppKey = messageAppKey;
    }

    public String getMessageAppSecret() {
        return messageAppSecret;
    }

    public void setMessageAppSecret(String messageAppSecret) {
        this.messageAppSecret = messageAppSecret;
    }

    public ALiPaySetting getaLiPaySetting() {
        return aLiPaySetting;
    }

    public void setaLiPaySetting(ALiPaySetting aLiPaySetting) {
        this.aLiPaySetting = aLiPaySetting;
    }

    public UploadFileSetting getUploadFileSetting() {
        return uploadFileSetting;
    }

    public void setUploadFileSetting(UploadFileSetting uploadFileSetting) {
        this.uploadFileSetting = uploadFileSetting;
    }

    public InitializationData getInitializationData() {
        return initializationData;
    }

    public void setInitializationData(InitializationData initializationData) {
        this.initializationData = initializationData;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getMessageDomain() {
        return messageDomain;
    }

    public void setMessageDomain(String messageDomain) {
        this.messageDomain = messageDomain;
    }

    public String getMessageUri() {
        return messageUri;
    }

    public void setMessageUri(String messageUri) {
        this.messageUri = messageUri;
    }
}
