package com.spring.simple.development.support.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
/**
 * @Author luke
 * @Description MD5加密工具类
 * @Date 12:33 2020/6/16 0016
 * @Param 
 * @return 
 **/
public class Md5Utils {

    public static String md5EncryptAndBase64(String str) {
        return encodeBase64(md5Encrypt(str));
    }

    private static byte[] md5Encrypt(String encryptStr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(encryptStr.getBytes("utf8"));
            return messageDigest.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeBase64(byte[] b) {
        BASE64Encoder base64Encode = new BASE64Encoder();
        String str = base64Encode.encode(b);
        return str;
    }

    /**
     * 获取md5加密字符串
     *
     * @param str
     * @return
     * @author liko.wang
     * @date 2017年12月9日下午3:11:16
     */
    public static String getMD5Str(String str) {
        byte[] byteArray = md5Encrypt(str);

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }

        return md5StrBuff.toString();
    }

    /**
     * 两次MD5密码
     *
     * @param str
     * @return
     * @author liko.wang
     * @date 2017年12月9日下午3:12:19
     */
    public static String getTwoMD5Str(String str) {
        return getMD5Str(getMD5Str(str));
    }


}
