package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.persistence.entity.UserProfile;

/**
 * REST接口所使用的响应结果的<b>错误信息</b>的封装对象。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class APIErrorResponse {

    /**
     * 用户的“昵称”输入的最大长度。
     */
    public static final int MAX_USER_NICKNAME_LENGTH = 10;

    /**
     * “用户头像”的数量。
     */
    public static final int USER_AVATAR_LENGTH = 1;

    /**
     * “艺文内容”和“评论内容”输入的最大长度。
     */
    public static final int MAX_MOMENT_AND_COMMENT_LENGTH = 140;

    /**
     * “拍品名称”输入的最大长度。
     */
    public static final int MAX_ITEM_NAME_LENGTH = 30;

    /**
     * “拍品详情”输入的最大长度。
     */
    public static final int MAX_ITEM_DETAIL_LENGTH = 2000;

    /**
     * “拍品图片”的最小数量。
     */
    public static final int MIN_ITEM_IMAGE_LENGTH = 4;

    /**
     * “拍品图片”的最大数量。
     */
    public static final int MAX_ITEM_IMAGE_LENGTH = 9;

    /**
     * “店铺头像”的数量。
     */
    public static final int SHOP_AVATAR_LENGTH = 1;

    /**
     * “店铺名称”输入的最大长度。
     */
    public static final int MAX_SHOP_NAME_LENGTH = 10;

    /**
     * “收货、退货地址”的“联系人”输入的最大长度。
     */
    public static final int MAX_ADDRESS_NAME_LENGTH = 15;

    /**
     * “收货、退货地址”的“联系电话”输入的最大长度。
     */
    public static final int MAX_ADDRESS_MOBILE_LENGTH = 30;

    /**
     * “收货、退货地址”的“所在省份”输入的最大长度。
     */
    public static final int MAX_ADDRESS_PROVINCE_LENGTH = 10;

    /**
     * “收货、退货地址”的“所在城市”输入的最大长度。
     */
    public static final int MAX_ADDRESS_CITY_LENGTH = 20;

    /**
     * “收货、退货地址”的“所在地区”输入的最大长度。
     */
    public static final int MAX_ADDRESS_DISTRICT_LENGTH = 40;

    /**
     * “收货、退货地址”的“详细地址”输入的最大长度。
     */
    public static final int MAX_ADDRESS_ADDRESS_LENGTH = 100;

    /**
     * “收货、退货地址”的“备注”输入的最大长度。
     */
    public static final int MAX_ADDRESS_REMARK_LENGTH = 20;

    /**
     * “收货、退货地址”的“邮编”输入的最大长度。
     */
    public static final int MAX_ADDRESS_POSTCODE_LENGTH = 10;

    /**
     * “意见反馈”的“内容”输入的最大长度。
     */
    public static final int MAX_SUGGESTION_CONTENT_LENGTH = 100;

    /**
     * “资讯”的“标题”输入的最大长度。
     */
    public static final int MAX_INFORMATION_TITLE_LENGTH = 100;

    /**
     * “资讯”的“来源”输入的最大长度。
     */
    public static final int MAX_INFORMATION_SOURCE_LENGTH = 20;

    /**
     * “广告”的图片的数量。
     */
    public static final int MAX_ADVERTISEMENT_IMAGE_LENGTH = 1;

    /**
     * “资讯封面”的图片的数量。
     */
    public static final int MAX_INFORMATION_COVER_IMAGE_LENGTH = 1;

    /**
     * “个人展示图片”的最小数量。
     */
    public static final int MIN_USER_SHOW_IMAGE_LENGTH = 1;

    /**
     * “个人展示图片”的最大数量。
     */
    public static final int MAX_USER_SHOW_IMAGE_LENGTH = 4;

    /**
     * “大咖简介”输入的最大长度。
     */
    public static final int MAX_ART_BIG_SHOT_OVERVIEW_LENGTH = 30;

    /**
     * “大咖介绍”输入的最大长度。
     */
    public static final int MAX_ART_BIG_SHOT_INTRODUCE_LENGTH = 200;

    /**
     * “资讯评论内容”输入的最大长度。
     */
    public static final int MAX_INFORMATION_COMMENT_LENGTH = 140;

    /**
     * “标签名称”输入的最大长度。
     */
    public static final int MAX_TAG_LENGTH = 10;

    /**
     * “资讯次要标签”的最大数量。
     */
    public static final int MAX_INFORMATION_SECONDARY_TAGS_LENGTH = 3;

    /**
     * 用户的“个人简介”输入的最大长度。
     */
    public static final int MAX_USER_PERSONAL_INTRODUCTION_LENGTH = 140;

    /**
     * 用户的“个性签名”输入的最大长度。
     */
    public static final int MAX_USER_PERSONALIZED_SIGNATURE_LENGTH = 30;

    public APIErrorResponse(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public enum ErrorCode {
        /**
         * E01X的错误。
         */
        E010010("010010", "validation.error.api.E010010"),
        E010011("010011", "validation.error.api.E010011"),
        E010012("010012", "validation.error.api.E010012"),
        E010013("010013", "validation.error.api.E010013", new String[]{String.valueOf(MAX_ART_BIG_SHOT_OVERVIEW_LENGTH)}),
        E010014("010014", "validation.error.api.E010014", new String[]{String.valueOf(MAX_ART_BIG_SHOT_INTRODUCE_LENGTH)}),
        E010015("010015", "validation.error.api.E010015"),
        /**
         * E02X的错误。
         */
        E010020("010020", "validation.error.api.E010020"),
        E010021("010021", "validation.error.api.E010021"),
        E010022("010022", "validation.error.api.E010022"),
        E010023("010023", "validation.error.api.E010023"),
        E010024("010024", "validation.error.api.E010024"),
        E010025("010025", "validation.error.api.E010025"),
        /**
         * E03X的错误。
         */
        E010030("010030", "validation.error.api.E010030", new String[]{UserProfile.UserSex.MALE.name(), UserProfile.UserSex.FEMALE.name()}),
        E010031("010031", "validation.error.api.E010031"),
        E010032("010032", "validation.error.api.E010032"),
        E010033("010033", "validation.error.api.E010033"),
        E010034("010034", "validation.error.api.E010034", new String[]{String.valueOf(MAX_USER_NICKNAME_LENGTH)}),
        E010035("010035", "validation.error.api.E010035"),
        E010036("010036", "validation.error.api.E010036", new String[]{String.valueOf(MAX_SUGGESTION_CONTENT_LENGTH)}),
        E010037("010037", "validation.error.api.E010037", new String[]{String.valueOf(MIN_USER_SHOW_IMAGE_LENGTH)}),
        E010038("010038", "validation.error.api.E010038", new String[]{String.valueOf(MAX_USER_SHOW_IMAGE_LENGTH)}),
        E010039("010039", "validation.error.api.E010039"),
        /**
         * E04X的错误。
         */
        E010040("010040", "validation.error.api.E010040"),
        E010041("010041", "validation.error.api.E010041"),
        E010042("010042", "validation.error.api.E010042"),
        E010043("010043", "validation.error.api.E010043", new String[]{String.valueOf(MAX_USER_PERSONAL_INTRODUCTION_LENGTH)}),
        E010044("010044", "validation.error.api.E010044", new String[]{String.valueOf(MAX_USER_PERSONALIZED_SIGNATURE_LENGTH)}),
        /**
         * E05X的错误。
         */
        E010050("010050", "validation.error.api.E010050"),
        E010051("010051", "validation.error.api.E010051", new String[]{String.valueOf(MAX_INFORMATION_COMMENT_LENGTH)}),
        E010052("010052", "validation.error.api.E010052", new String[]{String.valueOf(MAX_TAG_LENGTH)}),
        E010053("010053", "validation.error.api.E010053"),
        E010054("010054", "validation.error.api.E010054"),
        E010055("010055", "validation.error.api.E010055"),
        E010056("010056", "validation.error.api.E010056", new String[]{String.valueOf(MAX_MOMENT_AND_COMMENT_LENGTH)}),
        E010057("010057", "validation.error.api.E010057", new String[]{String.valueOf(MAX_INFORMATION_SECONDARY_TAGS_LENGTH)}),
        E010058("010058", "validation.error.api.E010058"),
        /**
         * E06X的错误。
         */
        E010060("010060", "validation.error.api.E010060"),
        E010061("010061", "validation.error.api.E010061"),
        E010062("010062", "validation.error.api.E010062"),
        E010063("010063", "validation.error.api.E010063"),
        E010064("010064", "validation.error.api.E010064"),
        E010065("010065", "validation.error.api.E010065"),
        E010066("010066", "validation.error.api.E010066"),
        /**
         * E07X的错误。
         */
        E010070("010070", "validation.error.api.E010070"),
        E010071("010071", "validation.error.api.E010071"),
        E010072("010072", "validation.error.api.E010072"),
        E010073("010073", "validation.error.api.E010073"),
        E010074("010074", "validation.error.api.E010074"),
        E010075("010075", "validation.error.api.E010075"),
        E010076("010076", "validation.error.api.E010076"),
        E010077("010077", "validation.error.api.E010077"),
        E010078("010078", "validation.error.api.E010078"),
        /**
         * E1XX的错误。
         */
        E010101("010101", "validation.error.api.E010101", new String[]{String.valueOf(MAX_SHOP_NAME_LENGTH)}),
        E010102("010102", "validation.error.api.E010102"),
        E010103("010103", "validation.error.api.E010103"),
        E010104("010104", "validation.error.api.E010104"),
        E010105("010105", "validation.error.api.E010105"),
        E010106("010106", "validation.error.api.E010106"),
        E010107("010107", "validation.error.api.E010107"),
        E010108("010108", "validation.error.api.E010108"),
        E010109("010109", "validation.error.api.E010109"),
        E010110("010110", "validation.error.api.E010110"),
        E010111("010111", "validation.error.api.E010111"),
        E010112("010112", "validation.error.api.E010112"),
        E010113("010113", "validation.error.api.E010113"),
        E010114("010114", "validation.error.api.E010114", new String[]{String.valueOf(MIN_ITEM_IMAGE_LENGTH)}),
        E010115("010115", "validation.error.api.E010115", new String[]{String.valueOf(MAX_ITEM_NAME_LENGTH)}),
        E010116("010116", "validation.error.api.E010116", new String[]{String.valueOf(MAX_ITEM_DETAIL_LENGTH)}),
        E010117("010117", "validation.error.api.E010117", new String[]{String.valueOf(MAX_ITEM_IMAGE_LENGTH)}),
        E010118("010118", "validation.error.api.E010118", new String[]{String.valueOf(SHOP_AVATAR_LENGTH)}),
        E010119("010119", "validation.error.api.E010119"),
        E010120("010120", "validation.error.api.E010120"),
        E010121("010121", "validation.error.api.E010121"),
        E010122("010122", "validation.error.api.E010122"),
        E010123("010123", "validation.error.api.E010123"),
        E010124("010124", "validation.error.api.E010124"),
        E010125("010125", "validation.error.api.E010125"),
        E010126("010126", "validation.error.api.E010126"),
        E010127("010127", "validation.error.api.E010127"),
        E010128("010128", "validation.error.api.E010128"),
        E010129("010129", "validation.error.api.E010129"),
        E010130("010130", "validation.error.api.E010130"),
        E010131("010131", "validation.error.api.E010131"),
        E010132("010132", "validation.error.api.E010132"),
        E010133("010133", "validation.error.api.E010133", new String[]{String.valueOf(MAX_ADDRESS_NAME_LENGTH)}),
        E010134("010134", "validation.error.api.E010134", new String[]{String.valueOf(MAX_ADDRESS_MOBILE_LENGTH)}),
        E010135("010135", "validation.error.api.E010135", new String[]{String.valueOf(MAX_ADDRESS_PROVINCE_LENGTH)}),
        E010136("010136", "validation.error.api.E010136", new String[]{String.valueOf(MAX_ADDRESS_CITY_LENGTH)}),
        E010137("010137", "validation.error.api.E010137", new String[]{String.valueOf(MAX_ADDRESS_DISTRICT_LENGTH)}),
        E010138("010138", "validation.error.api.E010138", new String[]{String.valueOf(MAX_ADDRESS_ADDRESS_LENGTH)}),
        E010139("010139", "validation.error.api.E010139", new String[]{String.valueOf(MAX_ADDRESS_REMARK_LENGTH)}),
        E010140("010140", "validation.error.api.E010140", new String[]{String.valueOf(MAX_ADDRESS_POSTCODE_LENGTH)}),
        E010141("010141", "validation.error.api.E010141"),
        E010142("010142", "validation.error.api.E010142"),
        E010143("010143", "validation.error.api.E010143"),
        E010144("010143", "validation.error.api.E010144"),
        E010145("010145", "validation.error.api.E010145"),
        E010146("010146", "validation.error.api.E010146"),
        E010147("010147", "validation.error.api.E010147"),
        E010148("010148", "validation.error.api.E010148", new String[]{String.valueOf(USER_AVATAR_LENGTH)}),
        E010149("010149", "validation.error.api.E010149"),
        /**
         * E2XX的错误。
         */
        E010200("010200", "validation.error.api.E010200"),
        E010201("010201", "validation.error.api.E010201"),
        E010202("010202", "validation.error.api.E010202"),
        E010203("010203", "validation.error.api.E010203"),
        E010204("010204", "validation.error.api.E010204"),
        E010205("010205", "validation.error.api.E010205"),
        E010206("010206", "validation.error.api.E010206"),
        E010207("010207", "validation.error.api.E010207"),
        E010208("010208", "validation.error.api.E010208"),
        E010209("010209", "validation.error.api.E010209"),
        E010210("010210", "validation.error.api.E010210"),
        E010211("010211", "validation.error.api.E010211"),
        E010212("010212", "validation.error.api.E010212"),
        E010213("010213", "validation.error.api.E010213"),
        E010214("010214", "validation.error.api.E010214"),
        E010215("010215", "validation.error.api.E010215"),
        E010216("010216", "validation.error.api.E010216"),
        E010217("010217", "validation.error.api.E010217"),
        E010218("010218", "validation.error.api.E010218"),
        /**
         * E3XX的错误，管理端使用。
         */
        E010300("010300", "validation.error.api.E010300", new String[]{String.valueOf(MAX_INFORMATION_TITLE_LENGTH)}),
        E010301("010301", "validation.error.api.E010301", new String[]{String.valueOf(MAX_INFORMATION_SOURCE_LENGTH)}),
        E010302("010302", "validation.error.api.E010302"),
        E010303("010303", "validation.error.api.E010303"),
        /**
         * E9XXX的错误。
         */
        E990010("990010", "validation.error.api.E990010"),
        E990020("990020", "validation.error.api.E990020"),
        E990030("990030", "validation.error.api.E990030"),
        E990031("990031", "validation.error.api.E990031"),
        E990032("990032", "validation.error.api.E990032"),
        E999999("999999", "validation.error.api.E999999");

        private String errorCode;
        private String errorDesc;
        private String[] args;

        public String getErrorCode() {
            return errorCode;
        }

        public String getErrorDesc() {
            return errorDesc;
        }

        public String[] getArgs() {
            return args;
        }

        public void setArgs(String[] args) {
            this.args = args;
        }

        ErrorCode(String errorCode, String errorDesc) {
            this(errorCode, errorDesc, null);
        }

        ErrorCode(String errorCode, String errorDesc, String[] args) {
            this.errorCode = errorCode;
            this.errorDesc = errorDesc;
            this.args = args;
        }
    }

    private String errorCode;// 错误代码

    private String errorDesc;// 错误信息

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
