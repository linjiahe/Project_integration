package com.blockchain.commune.utils;

import org.bson.types.ObjectId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class IdUtil {

    public static String getMerchantCode(String agentCode, Long size) {
        String year = DateUtil.yearString(new Date()).substring(2);
        String merchantCode = year + agentCode + size;
        if (size < 100) {
            String str = "000";
            int a = str.length() - size.toString().length();
            String b = str.substring(0, a) + size;
            merchantCode = year + agentCode + b;
        }
        return merchantCode;
    }

    public static String getMerchantUserId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "MU" + id;
    }

    public static String getId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ID" + id;

    }


    public static String getRegionId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "RID" + id;

    }


    public static String getBrandId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "BID" + id;

    }



    public static String getCouponId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "CP" + id;

    }

    public static String getRefundId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "RI" + id;

    }

    public static String getOrdId() {
        Date now = new Date();
        String date = DateUtil.dateToString(now);
        Random random = new Random();
        String s = date.substring(2) + (int) (random.nextDouble() * (100 - 10) + 10) + NumberUtil.getId();
        return s;

    }

    public static String getPayId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "PN" + id;
    }

    public static String getCartId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ID" + id;

    }

    public static String getAccountId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "AI" + id;

    }


    public static String getPrepareId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "PI" + id;

    }

    public static String getUserId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "UI" + id;

    }

    public static String getMerchantId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "MI" + id;

    }

    public static String getStoreId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "SI" + id;

    }

    public static String getCatId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "CI" + id;
    }

    public static String getNewsId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "NI" + id;
    }

    public static String getSkuId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "SK" + id;
    }

    public static String getFileName() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "file" + id;
    }

    public static String getNoticeId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "NID" + id;
    }

    public static String getSubAccountId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "SAI" + id;
    }

    public static String getTransId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "TI" + id;
    }

    public static String getDailySummaryId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "DSI" + id;
    }


    public static String getAttendanceId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "AID" + id;
    }

    public static String getAdId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "AD" + id;
    }

    public static String getAdCommentId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ADC" + id;
    }

    public static String getAdReplyId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ADR" + id;
    }

    public static String getCommentId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "COI" + id;
    }

    public static String getCommentReplyId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "CRI" + id;
    }

    public static String getReportId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "RPI" + id;
    }

    public static String getHashMD5(String source) {

        return getHash(source, "MD5");
    }


    /**
     * 由于MD5 与SHA-1均是从MD4 发展而来，它们的结构和强度等特性有很多相似之处
     * SHA-1与MD5 的最大区别在于其摘要比MD5 摘要长 32 比特（1byte=8bit，相当于长4byte，转换16进制后比MD5多8个字符）。
     * 对于强行攻击，：MD5 是2128 数量级的操作，SHA-1 是2160数量级的操作。
     * 对于相同摘要的两个报文的难度：MD5是 264 是数量级的操作，SHA-1 是280 数量级的操作。
     * 因而，SHA-1 对强行攻击的强度更大。 但由于SHA-1 的循环步骤比MD5 多（80:64）且要处理的缓存大（160 比特:128 比特），SHA-1 的运行速度比MD5 慢。
     *
     * @param source   需要加密的字符串
     * @param hashType 加密类型 （MD5 和 SHA）
     * @return
     */

    public static String getHash(String source, String hashType) {
        // 用来将字节转换成 16 进制表示的字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            md.update(source.getBytes()); // 通过使用 update 方法处理数据,使指定的 byte数组更新摘要
            byte[] encryptStr = md.digest(); // 获得密文完成哈希计算,产生128 位的长整数
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
                byte byte0 = encryptStr[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(str); // 换后的结果转换为字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
