package cn.artisantc.core.util;

import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.ArtMomentImage;
import cn.artisantc.core.persistence.entity.ArtMomentLike;
import cn.artisantc.core.persistence.entity.MerchantBankCard;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.web.rest.v1_0.convert.ArtMomentImageViewConverter;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentImageView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentLikeView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * 对“ArtMoment”所包含的集合类型的属性进行转换的工具类。
 * Created by xinjie.li on 2016/9/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ConverterUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ConverterUtil.class);

    private ConverterUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 将“ArtMoment”的“List<ArtMomentImage>”转换成“List<ArtMomentImageView>”。
     *
     * @param source ArtMoment，艺文(艺术圈)
     * @return 转换后的List<ArtMomentImageView>
     */
    public static List<ArtMomentImageView> getImages(ArtMoment source) {
        List<ArtMomentImageView> images = new ArrayList<>();

        if (null != source && null != source.getImages() && !source.getImages().isEmpty()) {
            Converter<ArtMomentImage, ArtMomentImageView> converter = new ArtMomentImageViewConverter();
            for (ArtMomentImage image : source.getImages()) {
                ArtMomentImageView imageResponse = converter.convert(image);
                images.add(imageResponse);
            }
        }

        return images;
    }

    /**
     * 获得“ArtMoment”的点赞次数，并转换成字符串。
     *
     * @param likeTimes 点赞次数
     * @return 该艺文的点赞次数
     */
    public static String getLikeTimes(long likeTimes) {
        return digitsToStringWithKilo(likeTimes);
    }

    /**
     * 获得“ArtMoment”的转发次数，并转换成字符串。
     *
     * @param forwardTimes 转发次数
     * @return 该艺文的转发次数
     */
    public static String getForwardTimes(long forwardTimes) {
        return digitsToStringWithKilo(forwardTimes);
    }

    /**
     * 获得“ArtMoment”的浏览次数，并转换成字符串。
     *
     * @param browseTimes 浏览次数
     * @return 该艺文的浏览次数
     */
    public static String getBrowseTimes(long browseTimes) {
        return digitsToStringWithTenThousand(browseTimes);
    }

    /**
     * 获得“ArtMoment”的所有评论次数，并转换成字符串。
     *
     * @param commentTimes 评论次数
     * @return 该艺文的评论次数
     */
    public static String getCommentTimes(long commentTimes) {
        return digitsToStringWithKilo(commentTimes);
    }

    /**
     * 获得“ArtMoment”的所有收藏次数，并转换成字符串。
     *
     * @param favoritesTimes 收藏次数
     * @return 该艺文的收藏次数
     */
    public static String getFavoritesTimes(long favoritesTimes) {
        return digitsToStringWithKilo(favoritesTimes);
    }

    /**
     * 获得最近点赞的用户头像。并将“List<ArtMomentLike>”转换成“List<ArtMomentLikeView>”。
     *
     * @param likes ArtMoment，艺文(艺术圈)
     * @return 最近点赞的用户头像
     */
    public static List<ArtMomentLikeView> getLatestAvatars(List<ArtMomentLike> likes) {
        List<ArtMomentLikeView> likeResponses = new ArrayList<>();

        if (null != likes && !likes.isEmpty()) {
            for (ArtMomentLike like : likes) {
                ArtMomentLikeView likeResponse = new ArtMomentLikeView();
                // “头像”和“头像缩略图”地址
                likeResponse.setAvatarUrl(UserHelper.getAvatar3xUrl(like.getUser()));
                likeResponse.setCreateDateTime(DateTimeUtil.getPrettyDescription(like.getCreateDateTime()));
                likeResponse.setId(String.valueOf(like.getId()));

                likeResponses.add(likeResponse);
            }
        }

        return likeResponses;
    }

    /**
     * 获得“商家的数字签名”。
     *
     * @param createDateTime   商家的创建时间
     * @param realName         真实姓名
     * @param identityNumber   身份证号
     * @param userId           用户ID
     * @param userSerialNumber 用户匠号
     * @return 商家的数字签名
     */
    public static String getMerchantEncodeString(String createDateTime, String realName, String identityNumber, long userId, String userSerialNumber) {
        String word = "|createDateTime=" + createDateTime +
                "|realName=" + realName +
                "|identityNumber=" + identityNumber +
                "|userId=" + userId +
                "|userSerialNumber=" + userSerialNumber;
        String salt = StringUtils.replaceChars(word, "|", "_");
        String encodeWord = WordEncoderUtil.encodeWord(word, salt);

        LOG.debug("getMerchantEncodeString: {}", encodeWord);

        return encodeWord;
    }

    public static String getArtMomentEncodeString() {
// TODO：优化艺文图片的存储路径，考虑将“艺文ID”、“发布者匠号”、“发布者ID”、“发布时间”作为加密字段，生成图片的私有路径
        return "";
    }

    /**
     * 对银行卡的信息进行包装处理，处理规则如下：银行卡所属银行名称 + (银行卡尾号4位) + 银行卡持有人姓名，例如：中国建设银行 (4687) XXX，
     *
     * @param bankCard 要包装的银行卡
     * @return 对银行卡包装过后的信息
     */
    public static String getWrapperBankCardInfo(MerchantBankCard bankCard) {
        if (null == bankCard) {
            return "";
        }

        String wrapperBankAccount = getLastFourNumber(bankCard.getBankAccount());
        return bankCard.getBankName() + " (" + wrapperBankAccount + ") " + bankCard.getRealName();
    }

    /**
     * 对银行卡的信息进行包装处理，处理规则如下：银行卡所属银行名称 + (银行卡号) + 银行卡持有人姓名。
     *
     * @param bankCard 要包装的银行卡
     * @return 对银行卡包装过后的信息
     */
    public static String getFullBankCardInfo(MerchantBankCard bankCard) {
        if (null == bankCard) {
            return "";
        }

        int count = 4;// 位数，定义每多少位增加一个空格
        String bankAccount = bankCard.getBankAccount();
        StringBuilder wrapperBankAccount = new StringBuilder();
        while (bankAccount.length() > count) {
            wrapperBankAccount.append(bankAccount.substring(0, count)).append(" ");
            bankAccount = bankAccount.substring(count);
        }
        wrapperBankAccount.append(bankAccount);
        return bankCard.getBankName() + " (" + wrapperBankAccount.toString() + ") " + bankCard.getRealName();
    }

    /**
     * 对银行卡的信息进行包装处理，处理规则如下：银行卡所属银行名称 + 银行卡尾号4位，例如：中国建设银行 4687。
     *
     * @param bankCard 要包装的银行卡
     * @return 对银行卡包装过后的信息
     */
    public static String getWrapperBankCardWithBankName(MerchantBankCard bankCard) {
        if (null == bankCard) {
            return "";
        }

        String wrapperBankAccount = getLastFourNumber(bankCard.getBankAccount());
        return bankCard.getBankName() + " " + wrapperBankAccount;
    }

    /**
     * 对银行卡的卡号进行包装处理，处理规则如下：只显示银行卡尾号4位，其余位数均用*代替 例如：**** **** **** **** 4687。
     *
     * @param bankAccount 要包装的银行卡号
     * @return 包装过后的银行卡卡号
     */
    public static String getWrapperBankCardAccount(String bankAccount) {
        if (null == bankAccount) {
            return "";
        }

        return "**** **** **** " + getLastFourNumber(bankAccount);
    }

    /**
     * 对公钥、私钥等一些敏感信息进行包装处理。
     *
     * @param key 要包装的公钥、私钥等一些敏感信息
     * @return 包装过后的公钥、私钥等一些敏感信息
     */
    public static String getWrapperKey(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }

        String prefix = "";
        String suffix = "";
        if (key.length() > 4) {
            prefix = key.substring(0, 4);
        }
        if (key.length() > 8) {
            suffix = key.substring(key.length() - 4);
        }
        return prefix + "********" + suffix;
    }

    /**
     * 将数字转换成String类型，同时如果传入的数字大于“1000”，则转换为K的描述。例如：52013 -> 52K。
     *
     * @param digits 要转换的数字
     * @return 转换后的字符串
     */
    static String digitsToStringWithKilo(long digits) {
        if (digits >= 1000) {
            return (digits / 1000) + "K";
        }
        return String.valueOf(digits);
    }

    /**
     * 将数字转换成String类型，同时如果传入的数字大于“10000”，则转换为W的描述。例如：52013 -> 5W。
     *
     * @param digits 要转换的数字
     * @return 转换后的字符串
     */
    private static String digitsToStringWithTenThousand(long digits) {
        if (digits >= 10000) {
            return (digits / 10000) + "W";
        }
        return String.valueOf(digits);
    }

    /**
     * 获得传入字符串的最后4位字符。
     *
     * @param number 待截取的字符串
     * @return 字符串的后4位
     */
    private static String getLastFourNumber(String number) {
        if (null == number) {
            return "";
        }
        if (number.length() < 4) {
            return number;
        }

        return number.substring(number.length() - 4);
    }
}
