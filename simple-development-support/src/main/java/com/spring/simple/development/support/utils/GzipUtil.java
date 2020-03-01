package com.spring.simple.development.support.utils;

import com.spring.simple.development.support.exception.GlobalException;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static com.spring.simple.development.support.exception.ResponseCode.RES_DECRYPT_FAIL;
import static com.spring.simple.development.support.exception.ResponseCode.RES_ENCRYPT_FAIL;

/**
 * 数据压缩
 * @author li'ko wang
 */
public class GzipUtil {
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            return out.toString("ISO-8859-1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String uncompress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(str
                    .getBytes("ISO-8859-1"));
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
        return null;
    }

    public static String compressBase64(String str) {
        GZIPOutputStream gzip = null;
        ByteArrayOutputStream out = null;
        try {
            if (str == null || str.length() == 0) {
                return str;
            }
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            String outStr = new String(Base64.encodeBase64(out.toByteArray()));
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(RES_ENCRYPT_FAIL, "压缩失败");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static  String unCompressBase64(String str) throws IOException {
        GZIPInputStream gis = null;
        ByteArrayOutputStream out = null;
        try {
            if (str == null || str.length() == 0) {
                return str;
            }
            gis = new GZIPInputStream(new ByteArrayInputStream(Base64.decodeBase64(str)));
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int n;
            while ((n = gis.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            byte[] bytes = out.toByteArray();
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(RES_DECRYPT_FAIL, "解压缩失败");
        } finally {
            if (gis != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
