package cn.artisantc.core.util;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 加密工具类。
 * Created by xinjie.li on 2016/8/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class WordEncoderUtil {

    private WordEncoderUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 使用“SHA-256”数字签名算法对给定的字符串进行数字签名，64位。
     *
     * @param word 需要进行签名的字符
     * @param salt 签名时使用的进行混淆用的字符
     * @return 签名后的生成的消息摘要
     */
    public static String encodeWord(String word, String salt) {
        // 使用Spring Security提供的sha-256加密算法
        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        return shaPasswordEncoder.encodePassword(word, salt);
    }

    /**
     * 使用“SHA-1”数字签名算法对给定的字符串进行数字签名，64位。
     *
     * @param word 需要进行签名的字符
     * @param salt 签名时使用的进行混淆用的字符
     * @return 签名后的生成的消息摘要
     */
    public static String encodeWordWithSHA1(String word, String salt) {
        // 使用Spring Security提供的sha-1加密算法
        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(1);
        return shaPasswordEncoder.encodePassword(word, salt);
    }

    /**
     * 使用“MD5”数字签名算法对给定的字符串进行数字签名，32位。
     *
     * @param word 需要进行签名的字符
     * @param salt 签名时使用的进行混淆用的字符
     * @return 签名后的生成的消息摘要
     */
    public static String encodeWordWithMD5(String word, String salt) {
        // 使用Spring Security提供的sha-1加密算法
        MessageDigestPasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("MD5");
        return passwordEncoder.encodePassword(word, salt);
    }

    /**
     * 使用“BCrypt”方式对密码进行加密。
     *
     * @param password 需要进行加密的密码
     * @return 加密后的密码
     */
    public static String encodePasswordWithBCrypt(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 使用“BCrypt”方式校验指定的“明文密码”和“加密后的字符串”是否一致
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的字符串
     * @return 一致则返回true，否则返回false
     */
    public static boolean matchesWithBCrypt(CharSequence rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
