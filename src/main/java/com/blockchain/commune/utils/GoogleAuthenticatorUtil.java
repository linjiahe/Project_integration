package com.blockchain.commune.utils;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;


/**
 * 谷歌身份验证的Java服务器端
 */
public class GoogleAuthenticatorUtil {

        // 来自谷歌文档，不用修该
        public static final int SECRET_SIZE = 10;

        public static final String SEED = "g8GjEvTbW5oVSV7avLBdwIHqGlUYNzKFI7izOF8GwLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";

        public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";//安全哈希算法（Secure Hash Algorithm）

        int window_size = 3; //默认 3 - 最大值17 (from google docs)多可偏移的时间--3*30秒的验证时间（手机客户端验证为30秒变化次）

        /**
         * 设置偏移量，最大值17
         * 默认 3 - 最大值17 (from google docs)多可偏移的时间--3*30秒的验证时间（手机客户端验证为30秒变化次）
         * @param s
         */
        public void setWindowSize(int s) {
            if (s >= 1 && s <= 17)
                window_size = s;
        }

        /**秘钥
         * 随机生成1个秘钥，这个秘钥必须在服务器上保存，用户在手机Google身份验证器上配置账号时也要这个秘钥
         * @return secret key
         */
        public static String generateSecretKey() {
            SecureRandom sr = null;
            try {
                sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
                byte[] seedBytes = SEED.getBytes();
                sr.setSeed(Base64.decodeBase64(seedBytes));
                byte[] buffer = sr.generateSeed(SECRET_SIZE);
                Base32 codec = new Base32();
                byte[] bEncodedKey = codec.encode(buffer);
                String encodedKey = new String(bEncodedKey);
                return encodedKey;
            }catch (NoSuchAlgorithmException e) {
                // should never occur... configuration error
            }
            return null;
        }


        /**
         * 查用户输入的6位码是否有效
         * @param secret 秘钥
         * @param code 6位码
         * @param timeMsec 偏移时间
         * @return
         */
        public boolean check_code(String secret, long code, long timeMsec) {
            Base32 codec = new Base32();
            byte[] decodedKey = codec.decode(secret);
            // convert unix msec time into a 30 second "window"
            // this is per the TOTP spec (see the RFC for details)
            long t = (timeMsec / 1000L) / 30L;
            // window是用来检验之前生成的6位码
            // 可以用这个window_size来调整允许6位码生效的时间
            for (int i = -window_size; i <= window_size; ++i) {
                long hash;
                try {
                    hash = verify_code(decodedKey, t + i);
                }catch (Exception e) {
                    // Yes, this is bad form - but
                    // the exceptions thrown would be rare and a static configuration problem
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                    //return false;
                }
                if (hash == code) {
                    return true;
                }
            }
            // The validation code is invalid.
            return false;
        }
        /**
         * 生成验证码
         * @param key
         * @param t
         * @return
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         */

        private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
            byte[] data = new byte[8];
            long value = t;
            for (int i = 8; i-- > 0; value >>>= 8) {
                data[i] = (byte) value;
            }
            SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signKey);
            byte[] hash = mac.doFinal(data);
            int offset = hash[20 - 1] & 0xF;
            // We're using a long because Java hasn't got unsigned int.
            long truncatedHash = 0;
            for (int i = 0; i < 4; ++i) {
                truncatedHash <<= 8;
                // We are dealing with signed bytes:
                // we just keep the first byte.
                truncatedHash |= (hash[offset + i] & 0xFF);
            }
            truncatedHash &= 0x7FFFFFFF;
            truncatedHash %= 1000000;
            return (int) truncatedHash;
        }


}
