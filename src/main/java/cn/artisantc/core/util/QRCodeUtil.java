package cn.artisantc.core.util;

import cn.artisantc.core.exception.QRCodeException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * “二维码”的工具类。
 * Created by xinjie.li on 2016/12/19.
 *
 * @author xinjie.li
 * @since 1.2
 */
public class QRCodeUtil {

    private static final Logger LOG = LoggerFactory.getLogger(QRCodeUtil.class);

    private QRCodeUtil() {
    }

    private static final int QR_CODE_COLOR = 0xFF000000;   // 二维码的颜色，默认是黑色
    private static final int QR_CODE_BG_COLOR = 0xFFFFFFFF;   // 二维码图片背景颜色，默认是白色

    private static final int QR_CODE_DEFAULT_WIDTH = 300;// 二维码图片的默认宽度
    private static final int QR_CODE_DEFAULT_HEIGHT = 300;// 二维码图片的默认高度

    public static final int USER_QR_CODE_WIDTH = 750;// 用户的“二维码名片”的高度

    /**
     * 生成二维码图片，图片的高度等同于指定的宽度。
     *
     * @param content  二维码的内容
     * @param logoPath 二维码图片上的LOGO图片的路径
     * @param width    二维码图片的宽度
     * @return 二维码图片的对应的imageBase64字符串
     */
    public static String getQRCode(String content, String logoPath, int width) {
        return getQRCode(content, logoPath, width, width);
    }

    /**
     * 生成二维码图片。
     *
     * @param content  二维码的内容
     * @param logoPath 二维码图片上的LOGO图片的路径
     * @param width    二维码图片的宽度
     * @param height   二维码图片的高度
     * @return 二维码图片的对应的imageBase64字符串
     */
    public static String getQRCode(String content, String logoPath, int width, int height) {
        // 校验参数
        if (StringUtils.isBlank(content)) {
            throw new QRCodeException("“content”参数不能为空！");
        }
        if (width <= 0) {
            LOG.info("指定的二维码图片的宽度不合法：{}，使用默认宽度：{}", width, QR_CODE_DEFAULT_WIDTH);
            width = QR_CODE_DEFAULT_WIDTH;
        }
        if (height <= 0) {
            LOG.info("指定的二维码图片的高度不合法：{}，使用默认高度：{}", height, QR_CODE_DEFAULT_HEIGHT);
            height = QR_CODE_DEFAULT_HEIGHT;
        }

        // 调整高度，使之与宽度一致，保证生成的图片为正方形
        if (height != width) {
            height = width;
        }

        // 生成二维码
        try {
            return generateQRCode(content, width, height, logoPath, null);
        } catch (IOException e) {
            throw new QRCodeException(e.getMessage(), e);
        }
    }

    /**
     * 给二维码图片添加Logo
     */
    private static String generateQRCode(String content, int width, int height, String logoPath, String text) throws IOException {
        BufferedImage bufferedImage = prepareQRCodeBufferedImage(content, width, height);

        Graphics2D g = bufferedImage.createGraphics();

        // 读取Logo图片
        File logoPic = new File(logoPath);
        BufferedImage logo = ImageIO.read(logoPic);

        // 设置logo的大小，因为过大会遮挡住二维码
        int logoWidth = logo.getWidth(), logoHeight = logo.getHeight();
        int resizeWidth = bufferedImage.getWidth() * 25 / 100;
        int resizeHeight = bufferedImage.getHeight() * 25 / 100;
        if (logoWidth > resizeWidth) {
            logoWidth = resizeWidth;
        }
        if (logoHeight > resizeHeight) {
            logoHeight = resizeHeight;
        }

        // 将LOGO放在二维码的中间位置
        int x = (bufferedImage.getWidth() - logoWidth) / 2;// x坐标
        int y = (bufferedImage.getHeight() - logoHeight) / 2;// y坐标

        // 开始绘制图片
        g.drawImage(logo, x, y, logoWidth, logoHeight, null);
//        LogoConfig logoConfig = new LogoConfig();
//            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
//            g.setStroke(new BasicStroke(logoConfig.getBorder()));
//            g.setColor(logoConfig.getBorderColor());
//            g.drawRect(x, y, widthLogo, heightLogo);
        g.dispose();

        // 把对二维码的文字描述添加上去，文字不要太长，这里最多支持两行，太长就会自动截取
        if (StringUtils.isNotBlank(text)) {
            int xCoordinate = 0, yCoordinate = 0;// 定义文字的坐标
            // 新的图片，把带logo的二维码下面加上文字
            BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outG = outImage.createGraphics();

            // 画二维码到新的面板
            outG.drawImage(bufferedImage, xCoordinate, yCoordinate, bufferedImage.getWidth(), bufferedImage.getHeight(), null);

            // 画文字到新的面板
            outG.setColor(Color.BLACK);

            // 设置“字体、字型、字号”
            Font font = new Font("宋体", Font.BOLD, 30);
            outG.setFont(font);
            int strWidth = outG.getFontMetrics().stringWidth(text);
            int halfWidth = width / 2;

            // 换行
            if (strWidth > (width - 1)) {
                // 长度过长就截取前面部分，长度过长就换行
                String textLineOne = text.substring(0, text.length() / 2);
                String textLineTwo = text.substring(text.length() / 2, text.length());
                int strWidth1 = outG.getFontMetrics().stringWidth(textLineOne);
                int strWidth2 = outG.getFontMetrics().stringWidth(textLineTwo);
                outG.drawString(textLineOne, halfWidth - strWidth1 / 2, bufferedImage.getHeight() + (outImage.getHeight() - bufferedImage.getHeight()) / 2 + 12);

                // 第二行
                BufferedImage outImageLineTwo = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outG2 = outImageLineTwo.createGraphics();
                outG2.drawImage(outImage, xCoordinate, yCoordinate, outImage.getWidth(), outImage.getHeight(), null);
                outG2.setColor(Color.BLACK);
                outG2.setFont(font);
                outG2.drawString(textLineTwo, halfWidth - strWidth2 / 2, outImage.getHeight() + (outImageLineTwo.getHeight() - outImage.getHeight()) / 2 + 5);
                outG2.dispose();
                outImageLineTwo.flush();
                outImage = outImageLineTwo;
            } else {
                outG.drawString(text, halfWidth - strWidth / 2, bufferedImage.getHeight() + (outImage.getHeight() - bufferedImage.getHeight()) / 2 + 12); // 画文字
            }
            outG.dispose();
            outImage.flush();
            bufferedImage = outImage;
        }
        logo.flush();
        bufferedImage.flush();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.flush();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        ImageIO.write(bufferedImage, "png", new File("C:\\Users\\Administrator\\Desktop\\测试用图片\\二维码\\二维码_zxing.png")); // 生成二维码图片文件

        String imageBase64QRCode = org.apache.commons.codec.binary.StringUtils.newStringUtf8(Base64.encodeBase64(byteArrayOutputStream.toByteArray()));

        byteArrayOutputStream.close();
        return imageBase64QRCode;
    }

    /**
     * 准备“生成二维码的BufferedImage对象”。
     *
     * @param content 二维码的内容
     * @param width   二维码图片的宽度
     * @param height  二维码图片的高度
     * @return “生成二维码的BufferedImage对象”
     */
    private static BufferedImage prepareQRCodeBufferedImage(String content, int width, int height) {
        MultiFormatWriter multiFormatWriter;
        BitMatrix bitMatrix;
        BufferedImage image = null;

        try {
            multiFormatWriter = new MultiFormatWriter();
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, getDecodeHintType());

            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? QR_CODE_COLOR : QR_CODE_BG_COLOR);
                }
            }
        } catch (WriterException e) {
            LOG.error("准备“生成二维码的BufferedImage对象”失败！", e);
        }
        return image;
    }

    /**
     * 二维码的格式参数。
     *
     * @return 二维码的格式参数
     */
    private static Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<>();

        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");// 设置编码方式
        hints.put(EncodeHintType.MARGIN, 0);
//        hints.put(EncodeHintType.QR_VERSION, 1);

        return hints;
    }

}
