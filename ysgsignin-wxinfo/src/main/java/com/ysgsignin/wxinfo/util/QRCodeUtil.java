package com.ysgsignin.wxinfo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ysgsignin.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 叶双贵
 * @version: 1.0
 * @编辑时间：2023/7/26 11:49
 *  二维码生成工具类
 **/
public class QRCodeUtil {
    private static final Logger log= LoggerFactory.getLogger(QRCodeUtil.class);

    //CODE_WIDTH：二维码宽度，单位像素
    private static final int CODE_WIDTH = 400;
    //CODE_HEIGHT：二维码高度，单位像素
    private static final int CODE_HEIGHT = 400;
    //FRONT_COLOR：二维码前景色，0x000000 表示黑色
    private static final int FRONT_COLOR = 0x000000;
    //BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
    //演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
    private static final int BACKGROUND_COLOR = 0xFFFFFF;

    public static void createCodeToFile(String content, File codeImgFileSaveDir, String fileName) {
        try {
            if (StringUtils.isBlank(content) || StringUtils.isBlank(fileName)) {
                return;
            }
            content = content.trim();
            if (codeImgFileSaveDir==null || codeImgFileSaveDir.isFile()) {
                //二维码图片存在目录为空，默认放在桌面...
                codeImgFileSaveDir = FileSystemView.getFileSystemView().getHomeDirectory();
            }
            if (!codeImgFileSaveDir.exists()) {
                //二维码图片存在目录不存在，开始创建...
                codeImgFileSaveDir.mkdirs();
            }

            //核心代码-生成二维码
            BufferedImage bufferedImage = getBufferedImage(content);

            File codeImgFile = new File(codeImgFileSaveDir, fileName);
            ImageIO.write(bufferedImage, "png", codeImgFile);

            log.info("二维码图片生成成功：" + codeImgFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码并输出到输出流, 通常用于输出到网页上进行显示，输出到网页与输出到磁盘上的文件中，区别在于最后一句 ImageIO.write
     * write(RenderedImage im,String formatName,File output)：写到文件中
     * write(RenderedImage im,String formatName,OutputStream output)：输出到输出流中
     * @param content  ：二维码内容
     * @param outputStream ：输出流，比如 HttpServletResponse 的 getOutputStream
     */
    public static void createCodeToOutputStream(String content, OutputStream outputStream) {
        try {
            if (StringUtils.isBlank(content)) {
                return;
            }
            content = content.trim();
            //核心代码-生成二维码
            BufferedImage bufferedImage = getBufferedImage(content);

            //区别就是这一句，输出到输出流中，如果第三个参数是 File，则输出到文件中
            ImageIO.write(bufferedImage, "png", outputStream);

            log.info("二维码图片生成到输出流成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //核心代码-生成二维码
    private static BufferedImage getBufferedImage(String content) throws WriterException {

        //com.google.zxing.EncodeHintType：编码提示类型,枚举类型
        Map<EncodeHintType, Object> hints = new HashMap();

        //EncodeHintType.CHARACTER_SET：设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        //EncodeHintType.ERROR_CORRECTION：设置误差校正
        //ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
        //不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        //EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
        hints.put(EncodeHintType.MARGIN, 1);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);
        BufferedImage bufferedImage = new BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < CODE_WIDTH; x++) {
            for (int y = 0; y < CODE_HEIGHT; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? FRONT_COLOR : BACKGROUND_COLOR);
            }
        }
        return bufferedImage;
    }
}
