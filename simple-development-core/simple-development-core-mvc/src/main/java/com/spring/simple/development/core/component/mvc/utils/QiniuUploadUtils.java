package com.spring.simple.development.core.component.mvc.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

/**
 * 描述:
 * 七牛云上传图片
 *
 * @author liko
 * @create 2018-09-27 上午11:29
 */
public class QiniuUploadUtils {
    private static final Logger logger = LogManager.getLogger(QiniuUploadUtils.class);

    public static void uploadImg(InputStream inputStream, String fileNme) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = PropertyConfigurer.getProperty("accessKey");
        String secretKey = PropertyConfigurer.getProperty("secretKey");
        String bucket = PropertyConfigurer.getProperty("bucket");
        if (StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(secretKey) || StringUtils.isEmpty(bucket)) {
            throw new RuntimeException("获取配置信息失败");
        }
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileNme;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            logger.info("上传成功" + JSON.toJSON(putRet) + fileNme + "时间：" + DateUtils.getCurrentTime());
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
            throw new RuntimeException("上传失败," + "时间：" + DateUtils.getCurrentTime());
        }
    }

    /**
     * 获取上传的token
     * @return
     */
    public static String getToken() {
        //...生成上传凭证，然后准备上传
        String accessKey = PropertyConfigurer.getProperty("accessKey");
        String secretKey = PropertyConfigurer.getProperty("secretKey");
        String bucket = PropertyConfigurer.getProperty("bucket");
        if (StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(secretKey) || StringUtils.isEmpty(bucket)) {
            throw new RuntimeException("获取配置信息失败");
        }
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }
}