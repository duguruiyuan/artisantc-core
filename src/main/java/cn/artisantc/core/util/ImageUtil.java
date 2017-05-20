package cn.artisantc.core.util;

import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片文件处理的工具类。
 * Created by xinjie.li on 2016/9/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ImageUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);

    private ImageUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 用户头像的“原始图片”宽度。
     */
    public static final int USER_AVATAR_WIDTH = 1200;

    /**
     * 用户头像的“3x图”宽度。
     */
    public static final int USER_AVATAR_THUMBNAIL_WIDTH = 150;

    /**
     * 店铺头像的宽度。
     */
    public static final int SHOP_AVATAR_WIDTH = 150;

    /**
     * 艺文图片的“原始图片”的宽度。
     */
    public static final int ART_MOMENT_IMAGE_WIDTH = 1200;

    /**
     * 艺文图片的“缩略图片”的宽度。
     */
    public static final int ART_MOMENT_IMAGE_THUMBNAIL_WIDTH = 300;

    /**
     * 拍品图片的“原始图片”的宽度。
     */
    public static final int ITEM_IMAGE_WIDTH = 1200;

    /**
     * 拍品图片的“缩略图片”的宽度。
     */
    public static final int ITEM_IMAGE_THUMBNAIL_WIDTH = 300;

    /**
     * 资讯图片的“原始图片”的宽度。
     */
    public static final int INFORMATION_IMAGE_WIDTH = 1200;

    /**
     * 资讯图片的“缩略图片”的宽度。
     */
    public static final int INFORMATION_THUMBNAIL_WIDTH = 300;

    /**
     * 广告图片的“原始图片”的宽度。
     */
    public static final int ADVERTISEMENT_IMAGE_WIDTH = 1200;

    /**
     * “用户的个人展示”图片的“原始图片”的宽度。
     */
    public static final int USER_SHOW_IMAGE_WIDTH = 1200;

    /**
     * “用户的个人展示”图片的“缩略图片”的宽度。
     */
    public static final int USER_SHOW_IMAGE_THUMBNAIL_WIDTH = 300;

    /**
     * “原始图片”中“水印”的尺寸大小，百分比，例如：100.00，表示“水印”的尺寸大小为原始水印的100.00%。
     */
    public static final double ORIGINAL_WATERMARK_SIZE_PERCENTAGE = 100.00;

    /**
     * “缩略图片”中“水印”的尺寸大小，百分比，例如：30.00，表示“水印”的尺寸大小为原始水印的30.00%。
     */
    public static final double THUMBNAIL_WATERMARK_SIZE_PERCENTAGE = 30.00;

    /**
     * “艺文图片”存储到服务器的本地路径。
     *
     * @return 图片文件存储的路径
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getMomentImageStorePath() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("image.upload.path.windows");
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("image.upload.path.linux");// 根据操作系统进行path的生成
        }
        return storePath;
    }

    /**
     * “资讯图片”存储到服务器的本地路径。
     *
     * @return 图片文件存储的路径
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getInformationImageStorePath() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("information.image.upload.path.windows");
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("information.image.upload.path.linux");// 根据操作系统进行path的生成
        }
        return storePath;
    }

    /**
     * “资讯图片”存储到服务器的本地路径。
     *
     * @return 图片文件存储的路径
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getAdvertisementImageStorePath() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("advertisement.image.upload.path.windows");
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("advertisement.image.upload.path.linux");// 根据操作系统进行path的生成
        }
        return storePath;
    }

    /**
     * “用户的个人展示的图片”存储到服务器的本地路径。
     *
     * @return 图片文件存储的路径
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String geUserShowImageStorePath() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("user.show.image.upload.path.windows");
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("user.show.image.upload.path.linux");// 根据操作系统进行path的生成
        }
        return storePath;
    }

    /**
     * “商家”图片存储到服务器的本地路径。
     *
     * @param createDateTime   “商家”的创建时间
     * @param realName         真实姓名
     * @param identityNumber   身份证号
     * @param userId           用户ID
     * @param userSerialNumber 用户匠号
     * @return 图片文件存储的路径
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getMerchantStorePath(String createDateTime, String realName, String identityNumber, long userId, String userSerialNumber) throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("merchant.upload.path.windows") + ConverterUtil.getMerchantEncodeString(createDateTime, realName, identityNumber, userId, userSerialNumber) + "\\";
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("merchant.upload.path.linux") + ConverterUtil.getMerchantEncodeString(createDateTime, realName, identityNumber, userId, userSerialNumber) + "/";// 根据操作系统进行path的生成
        }
        return storePath;
    }

    /**
     * “实名认证”图片存储到服务器的本地路径。
     *
     * @param createDateTime   “实名认证”的创建时间
     * @param realName         真实姓名
     * @param identityNumber   身份证号
     * @param userId           用户ID
     * @param userSerialNumber 用户匠号
     * @return 图片文件存储的路径
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getRealNameStorePath(String createDateTime, String realName, String identityNumber, long userId, String userSerialNumber) throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("real.name.upload.path.windows") + ConverterUtil.getMerchantEncodeString(createDateTime, realName, identityNumber, userId, userSerialNumber) + "\\";
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("real.name.upload.path.linux") + ConverterUtil.getMerchantEncodeString(createDateTime, realName, identityNumber, userId, userSerialNumber) + "/";// 根据操作系统进行path的生成
        }
        return storePath;
    }

    /**
     * “艺文的图片”的URL地址的前缀，包含协议、地址、端口号和路，例如：http://192.168.0.188/yjs/topic_image/art_moment/yjs/topic_image/art_moment/。
     *
     * @return 图片的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getMomentImageUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("image.url.address") + config.getString("image.url.path");
    }

    /**
     * “资讯的图片”的URL地址的前缀，包含协议、地址、端口号和路，例如：http://192.168.0.188/yjs/topic_image/art_moment/yjs/topic_image/art_moment/。
     *
     * @return 图片的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getInformationImageUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("information.image.url.address") + config.getString("information.image.url.path");
    }

    /**
     * “广告的图片”的URL地址的前缀，包含协议、地址、端口号和路，例如：http://192.168.0.188/yjs/topic_image/art_moment/yjs/topic_image/art_moment/。
     *
     * @return 图片的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getAdvertisementImageUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("advertisement.image.url.address") + config.getString("advertisement.image.url.path");
    }

    /**
     * “用户的个人展示的图片”的URL地址的前缀，包含协议、地址、端口号和路，例如：http://192.168.0.188/yjs/topic_image/art_moment/yjs/topic_image/art_moment/。
     *
     * @return 图片的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getUserShowImageUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("user.show.image.url.address") + config.getString("user.show.image.url.path");
    }

    /**
     * 图片的缩略图的URL地址的前缀，包含协议、地址、端口号和路径，例如：http://192.168.0.188/yjs/topic_image/art_moment/yjs/topic_image/art_moment/。
     *
     * @return 图片的缩略图的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getThumbnailUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("image.url.address") + config.getString("thumbnail.url.path");
    }

    /**
     * 判断所传文件是否是图片文件。
     *
     * @param file 需要校验的文件
     * @return 如果是图片则返回true，否则返回false
     */
    public static boolean isPicture(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.indexOf(".") + 1);
        List<String> permitFormats = new ArrayList<>();
        permitFormats.add("jpg");
        permitFormats.add("jpeg");
        permitFormats.add("png");
        permitFormats.add("gif");

        return permitFormats.contains(suffix.toLowerCase());
    }

    /**
     * 拍品分类图标的URL地址的前缀，包含协议、地址、端口号和路径，例如：http://192.168.0.188/yjs/item_category/。
     *
     * @return 拍品分类图标的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getItemCategoryIconPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("item.category.icon.address") + config.getString("item.category.icon.path");
    }

    /**
     * 获取商家图片的URL地址的前缀，包含协议、地址、端口号和路径，例如：http://192.168.0.188/yjs/items/。
     *
     * @return 商家图片的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getMerchantImageUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("merchant.image.url.address") + config.getString("merchant.image.url.path");
    }

    public static String getRealNameImageUrlPrefix() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        return config.getString("real.name.image.url.address") + config.getString("real.name.image.url.path");
    }

    /**
     * 获取拍品图片的URL地址的前缀，包含协议、地址、端口号和路径，例如：http://192.168.0.188/yjs/items/。
     *
     * @param createDateTime   商家的创建时间
     * @param realName         真实姓名
     * @param identityNumber   身份证号
     * @param userId           用户ID
     * @param userSerialNumber 用户匠号
     * @return 商家图片的URL地址的前缀
     * @throws ConfigurationException 读取配置文件时发生的异常
     */
    public static String getItemImageUrlPrefix(String createDateTime, String realName, String identityNumber, long userId, String userSerialNumber) throws ConfigurationException {
        return getMerchantImageUrlPrefix() + ConverterUtil.getMerchantEncodeString(createDateTime, realName, identityNumber, userId, userSerialNumber);
    }

    /**
     * @return
     * @throws ConfigurationException
     */
    public static String getWatermarkStorePath() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("upload-file.properties"));
        String storePath = config.getString("image.watermark.path.windows");
        if (SystemUtils.IS_OS_LINUX) {
            storePath = config.getString("image.watermark.path.linux");// 根据操作系统进行path的生成
        }
        return storePath;
    }

    /**
     * todo: Javadoc
     *
     * @param file
     * @param path
     * @param fileName
     */
    public static String uploadImage(MultipartFile file, String path, String fileName) {
        return uploadImage(file, path, fileName, null);
    }

    /**
     * todo: Javadoc
     *
     * @param file
     * @param path
     * @param fileName
     * @param errorMessage
     */
    private static String uploadImage(MultipartFile file, String path, String fileName, String errorMessage) {
        try {
            byte[] bytes = file.getBytes();

            fileName = replaceFileNameSpecialCharacters(fileName);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)));
            stream.write(bytes);
            stream.close();

            return fileName;
        } catch (IOException e) {
            if (StringUtils.isBlank(errorMessage)) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * todo：javadoc
     *
     * @param fileName
     * @return
     */
    public static String replaceFileNameSpecialCharacters(String fileName) {
        LOG.debug("待处理的文件全名：{}", fileName);
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        String suffix = StringUtils.substringAfterLast(fileName, ".");
        LOG.debug("文件名后缀：{}", suffix);

        String newFileName = StringUtils.substringBeforeLast(fileName, ".");
        LOG.debug("文件名：{}", newFileName);

        newFileName = newFileName.replaceAll(" ", "_")
                .replaceAll("\\(", "_")
                .replaceAll("\\)", "_")
                .replaceAll("（", "_")
                .replaceAll("）", "_")
                .replaceAll("\\+", "_")
                .replaceAll("/", "_")
                .replaceAll("\\?", "_")
                .replaceAll("%", "_")
                .replaceAll("#", "_")
                .replaceAll("&", "_")
                .replaceAll("=", "_");
        LOG.debug("替换特殊字符后的文件名：{}", newFileName);

        // 生成随机字符串，增加到文件名尾部
        int count = RandomUtils.nextInt(6, 15);
        String randomString = RandomStringUtils.random(count, 0, 0, true, true, null, new SecureRandom());

        if (StringUtils.isNotBlank(suffix)) {
            // 如果传入的“文件名”包含后缀，则在新的文件名后面添加上原文件名的后缀
            newFileName = newFileName + "_" + randomString + "." + suffix;
        } else {
            newFileName = newFileName + "_" + randomString;
        }

        LOG.debug("最终文件名：{}", newFileName);
        return newFileName;
    }


    public enum PlacementPosition {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT, MIDDLE_LEFT, MIDDLE_CENTER, MIDDLE_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }

    /**
     * Generate a watermarked image.
     *
     * @param originalImage
     * @param watermarkImage
     * @param position
     * @return image with watermark
     * @throws IOException
     */
    public static BufferedImage watermark(BufferedImage originalImage, BufferedImage watermarkImage, PlacementPosition position, double watermarkSizeMaxPercentage) throws IOException {
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();

        int watermarkWidth = getWatermarkWidth(originalImage, watermarkImage, watermarkSizeMaxPercentage);
        int watermarkHeight = getWatermarkHeight(originalImage, watermarkImage, watermarkSizeMaxPercentage);

        // We create a new image because we want to keep the originalImage
        // object intact and not modify it.
        BufferedImage bufferedImage = new BufferedImage(imageWidth,
                imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 0;
        int y = 0;

        if (position != null) {
            switch (position) {
                case TOP_LEFT:
                    x = 10;
                    y = 10;
                    break;
                case TOP_CENTER:
                    x = (imageWidth / 2) - (watermarkWidth / 2);
                    y = 10;
                    break;
                case TOP_RIGHT:
                    x = imageWidth - watermarkWidth - 10;
                    y = 10;
                    break;

                case MIDDLE_LEFT:
                    x = 10;
                    y = (imageHeight / 2) - (watermarkHeight / 2);
                    break;
                case MIDDLE_CENTER:
                    x = (imageWidth / 2) - (watermarkWidth / 2);
                    y = (imageHeight / 2) - (watermarkHeight / 2);
                    break;
                case MIDDLE_RIGHT:
                    x = imageWidth - watermarkWidth - 10;
                    y = (imageHeight / 2) - (watermarkHeight / 2);
                    break;

                case BOTTOM_LEFT:
                    x = 10;
                    y = imageHeight - watermarkHeight - 10;
                    break;
                case BOTTOM_CENTER:
                    x = (imageWidth / 2) - (watermarkWidth / 2);
                    y = imageHeight - watermarkHeight - 10;
                    break;
                case BOTTOM_RIGHT:
                    x = imageWidth - watermarkWidth - 10;
                    y = imageHeight - watermarkHeight - 10;
                    break;

                default:
                    break;
            }
        }

        g2d.drawImage(Scalr.resize(watermarkImage, Scalr.Method.ULTRA_QUALITY, watermarkWidth, watermarkHeight), x, y, null);

        return bufferedImage;

    }

    /**
     * @param originalImage
     * @param watermarkImage
     * @param maxPercentage
     * @return
     */
    public static int getWatermarkWidth(BufferedImage originalImage, BufferedImage watermarkImage, double maxPercentage) {
        return calculateWatermarkDimensions(originalImage, watermarkImage, maxPercentage).getLeft().intValue();
    }

    /**
     * @param originalImage
     * @param watermarkImage
     * @param maxPercentage
     * @return
     */
    public static int getWatermarkHeight(BufferedImage originalImage, BufferedImage watermarkImage, double maxPercentage) {
        return calculateWatermarkDimensions(originalImage, watermarkImage, maxPercentage).getRight().intValue();
    }

    /**
     * @param originalImage
     * @param watermarkImage
     * @param maxPercentage
     * @return
     */
    private static Pair<Double, Double> calculateWatermarkDimensions(BufferedImage originalImage, BufferedImage watermarkImage, double maxPercentage) {
        double imageWidth = originalImage.getWidth();
        double imageHeight = originalImage.getHeight();

        double maxWatermarkWidth = imageWidth / 100.0 * maxPercentage;
        double maxWatermarkHeight = imageHeight / 100.0 * maxPercentage;

        double watermarkWidth = watermarkImage.getWidth();
        double watermarkHeight = watermarkImage.getHeight();

        if (watermarkWidth > maxWatermarkWidth) {
            double aspectRatio = watermarkWidth / watermarkHeight;
            watermarkWidth = maxWatermarkWidth;
            watermarkHeight = watermarkWidth / aspectRatio;
        }

        if (watermarkHeight > maxWatermarkHeight) {
            double aspectRatio = watermarkWidth / watermarkHeight;
            watermarkHeight = maxWatermarkHeight;
            watermarkWidth = watermarkHeight / aspectRatio;
        }

        return Pair.of(watermarkWidth, watermarkHeight);
    }


    public static BufferedImage convertTextToGraphic(String text) {

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        Font font = new Font("微软雅黑", Font.BOLD, 18);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0f)); // 设置透明度，1.0f为透明度 ，值从0-1.0，依次变得不透明
        g2d.setComposite(AlphaComposite.Clear);

        g2d.setComposite(AlphaComposite.Src);

        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        return img;
    }


    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    /**
     * join two BufferedImage
     * you can add a orientation parameter to control direction
     * you can use a array to join more BufferedImage
     */

    public static BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2, Orientation orientation) {
        assert null != orientation && null != img1 && null != img2;

        //do some calculate first
        int offset = 5;
        // 默认“水平(横向)”拼接图片
        int width = img1.getWidth() + img2.getWidth() + offset;
        int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
        if (Orientation.VERTICAL == orientation) {
            // “垂直(纵向)”拼接图片
            width = Math.max(img1.getWidth(), img2.getWidth()) + offset;
            height = img1.getHeight() + img2.getHeight() + offset;
        }

        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImage.createGraphics();
        Color oldColor = g2d.getColor();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0f)); // 设置透明度，1.0f为透明度 ，值从0-1.0，依次变得不透明

        //fill background
        g2d.setPaint(Color.WHITE);
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);
        g2d.setComposite(AlphaComposite.Src);

        //draw image
        g2d.setColor(oldColor);
        if (Orientation.HORIZONTAL == orientation) {
            // “水平(横向)”拼接图片
            g2d.drawImage(img1, null, 0, 0);
            g2d.drawImage(img2, null, img1.getWidth() + offset, 0);
        } else if (Orientation.VERTICAL == orientation) {
            // “垂直(纵向)”拼接图片
            g2d.drawImage(img1, null, 0, 0);
            g2d.drawImage(img2, null, 0, img1.getHeight() + offset);
        }

        g2d.dispose();
        return newImage;
    }


    /**
     * 按指定高度 等比例缩放图片
     *
     * @param bufferedImage
     * @param newPath
     * @param newWidth      新图的宽度
     * @return 压缩处理后的图片的高度
     * @throws IOException
     */
    public static int zoomImageScale(BufferedImage bufferedImage, String newPath, int newWidth) throws IOException {
        assert null != bufferedImage;

        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();
        double scale = (double) originalWidth / (double) newWidth;    // 缩放的比例

        int newHeight = (int) (originalHeight / scale);

        zoomImageUtils("jpg", newPath, bufferedImage, newWidth, newHeight);
        return newHeight;
    }

    private static void zoomImageUtils(String suffix, String newPath, BufferedImage bufferedImage, int width, int height) throws IOException {
        // 处理 png 背景变黑的问题
        if (suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))) {
            BufferedImage to = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2d.dispose();

            g2d = to.createGraphics();
            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();

            ImageIO.write(to, suffix, new File(newPath));
        } else {
            // 高质量压缩，其实对清晰度而言没有太多的帮助
//            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            tag.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
//
//            FileOutputStream out = new FileOutputStream(newPath);    // 将图片写入 newPath
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//            jep.setQuality(1f, true);    //压缩质量, 1 是最高值
//            encoder.encode(tag, jep);
//            out.close();

            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(newImage, suffix, new File(newPath));
        }
    }
}
