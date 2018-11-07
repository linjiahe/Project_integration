package com.blockchain.commune.utils;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWTUtils {


    public final static String base64Secret = "zhenniuzhenniu@2018";


    public static boolean checkToken(String token, String userId) throws CommonException {
        if(userId.startsWith("UI")&&userId.length()==26) {
            if (RedisUtil.exists("token_" + userId)) {
                if (!RedisUtil.get("token_" + userId).equals(token)) {
                    throw new CommonException(ErrorCodeEnum.TOKENREPEATERROR, "您的账号在别处登陆");
                }
            }
        }

        Claims claims = JWTUtils.parseJWT(token);
        if (claims == null) {
            return false;
        }
        String parseUser = (String) claims.get("userId");
        if (userId.equals(parseUser)) {
            return true;
        }
        return false;
    }

    public static String checkToken(String token) {
        Claims claims = JWTUtils.parseJWT(token);
        if (claims == null) {
            return "";
        }
        String parseUser = (String) claims.get("userId");
        if (parseUser == null) {
            return "";
        }
        return parseUser;
    }


    /**
     * 解析jwt
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {

            Claims claims = Jwts.parser()

                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();

            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    static Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    /**
     * 构建jwt
     */
    public static String createJWT(String userId, String role,
                                   long TTLMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("role", role)
                .claim("userId", userId)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);

            String s = DateUtil.dateToString(exp);

            logger.info("date:" + s);

            builder.setExpiration(exp).setNotBefore(now);
        }
        if(userId.startsWith("UI")&&userId.length()==26) {
            //添加token在redis中，并设置失效时长
            RedisUtil.set("token_" + userId, builder.compact(), TTLMillis);
        }
        //生成JWT
        return builder.compact();
    }
}