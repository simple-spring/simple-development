package com.spring.simple.development.support.utils;

import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESUtils {
    private static final Logger logger = LogManager.getLogger(AESUtils.class);


    public static String coupon_key = "32b2ecd3f766da7f";

    public static String decrypt(String sKey, String sSrc){
        try {
            // 判断Key是否正确
            if (StringUtils.isBlank(sKey)) {
                throw new GlobalException(ResponseCode.RES_DECRYPT_FAIL, "Key为空null");
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                throw new GlobalException(ResponseCode.RES_DECRYPT_FAIL, "Key长度不是16位");
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;

        } catch (Exception ex) {
            logger.error(DateUtils.getCurrentTime(), ex);
            throw new GlobalException(ResponseCode.RES_DECRYPT_FAIL, "解密失败");
        }
    }


    public static String encrypt(String sKey, String sSrc){
        if (StringUtils.isBlank(sKey)) {
            throw new GlobalException(ResponseCode.RES_ENCRYPT_FAIL, "Key为空null");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new GlobalException(ResponseCode.RES_ENCRYPT_FAIL, "Key长度不是16位");
        }
        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            //encodeToString(encrypted, Base64.NO_WRAP);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new BASE64Encoder().encodeBuffer(encrypted);
        }catch (Exception ex){
            logger.error(DateUtils.getCurrentTime(), ex);
            throw new GlobalException(ResponseCode.RES_ENCRYPT_FAIL, "加密失败失败");
        }
    }
}
