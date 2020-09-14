package com.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

@Component
public class ErweimaUtils {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private ErweimaUtils() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format,
                                     OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static String writeToLocal(String fileName,double lat,double lnt) throws Exception {
        String text = "  https://apis.map.qq.com/uri/v1/geocoder?coord="+lat+","+lnt+"+"+"&referer=XWPBZ-JM7CX-URG4R-75EBV-HCJNZ-VGFBQ"; // 二维码内容（重要）
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度
        String format = "jpg";// 二维码的图片格式
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();//EncodeHintType枚举了各种编码格式，大小等等
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                BarcodeFormat.QR_CODE, width, height, hints);//BarcodeFormat枚举了输出类型
        StringBuffer stringBuffer = new StringBuffer(fileName).append(".jpg");
        // 生成二维码
//        C:\Users\syz06\WebstormProjects\tourism-detail\src\assets
        File outputFile = new File("C:" + File.separator + "Users" + File.separator+"syz06"+ File.separator+"WebstormProjects"+File.separator+"tourism-detail"+File.separator+"src"+ File.separator+"assets"+File.separator +stringBuffer);//文件名，重要
        //File.separator可以兼容各种单线双线，斜线，爽

        ErweimaUtils.writeToFile(bitMatrix, format, outputFile);//已过时还能用，按照API提示,使用MatrixToImageWriter.writeToPath(BitMatrix matrix, String format, Path file)方法替代
        return fileName;
    }
}
