package cn.artisantc.core.util;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;

/**
 * “头像”的处理的工具类。
 * Created by xinjie.li on 2016/9/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AvatarUtil {

    private AvatarUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * “用户头像的默认名称”。
     *
     * @return “用户头像的默认名称”
     */
    public static String getDefaultAvatarFileName() {
        return "default_male_avatar.png";
    }

    /**
     * “用户头像”的URL地址前缀。
     *
     * @return “用户头像”的URL地址前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getAvatarUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("avatar.url.address") + config.getString("avatar.url.path");
    }

    /**
     * @return “店铺头像”的URL地址前缀。
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getShopAvatarUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("merchant.image.url.address") + config.getString("merchant.image.url.path");
    }

    /**
     * “用户头像”存储到服务器的本地路径。
     *
     * @return “用户头像”存储的路径
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getStorePath() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("avatar.upload.path.windows");
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("avatar.upload.path.linux");// 根据操作系统进行path的生成
        }
        return storePath;
    }
}
