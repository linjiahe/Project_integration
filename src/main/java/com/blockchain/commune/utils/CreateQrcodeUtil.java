package com.blockchain.commune.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CreateQrcodeUtil {

    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    static Logger logger = LoggerFactory.getLogger(CreateQrcodeUtil.class);

    public static String createQrcode(String url, String path, String subFolder) {

        String filePostfix = "png";
        String id = IdUtil.getFileName();
        String newFilePath = path + "/qrcode/" + subFolder + "/";
        String fileName = id + ".png";


        File checkFile = new File(newFilePath);
        if (!checkFile.exists() && !checkFile.isDirectory()) {
            checkFile.mkdirs();
        }

        String filePath = newFilePath + fileName;

        File file = new File(filePath);
        encode(url, file, filePostfix, BarcodeFormat.QR_CODE, 500, 500, null);


        return "qrcode/" + subFolder + "/" + fileName;


    }

    public static void encode(String contents, File file, String filePostfix, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        try {
            contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
            writeToFile(bitMatrix, filePostfix, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, file);
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }
}
