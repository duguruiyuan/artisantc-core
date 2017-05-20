package cn.artisantc.core.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by xinjie.li on 2016/12/19.
 *
 * @author xinjie.li
 * @since 1.2
 */
@RunWith(PowerMockRunner.class)
public class TestQRCodeUtil {

//    @Test
//    public void testToBufferedImage() throws Exception {
//        String text = "http://www.baidu.com"; // 二维码内容
//        int width = 300; // 二维码图片宽度
//        int height = 300; // 二维码图片高度
//        String format = "jpg";// 二维码的图片格式
//
//        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
//        File outputFile = new File("C:\\Users\\Administrator\\Desktop\\测试用图片\\二维码\\二维码_BitMatrix.png");
//        MatrixToImageWriter.writeToPath(bitMatrix, format, outputFile.toPath());
//    }

    @Test
    public void testGetQR_CODEBufferedImage() throws Exception {
        String logoPath = "D:\\xampp\\htdocs\\yjs\\avatar\\default_female_avatar.png";// 二维码中心的LOGO
        String content = "https://www.baidu.com/";
//        String productName = "跳转到百度的二维码 跳转到百度的二维码 跳转到百度的二维码 跳转到百度的二维码 跳转到百度的二维码";
//        String productName = "";
        try {
            String imageBase64QRCode = QRCodeUtil.getQRCode(content, logoPath, 300, 300);
            System.out.println("imageBase64QRCode(" + imageBase64QRCode.length() + "): " + imageBase64QRCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
