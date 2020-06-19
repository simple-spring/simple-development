package com.spring.simple.development.core.component.mvc.utils;

import com.alibaba.fastjson.JSON;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 描述:
 * 文件上传工具
 *
 * @author liko
 * @create 2018-11-01 下午1:25
 */
public class FileUploadUtils {
    private static final Logger logger = LogManager.getLogger(FileUploadUtils.class);


    static List<ResponseMsg> responseMsgs = null;

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static String downLoadFromUrl(String urlStr, String fileName, String savePath) {
        try {

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(5 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);

            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            // System.out.println("info:"+url+" download success");
            return saveDir + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    public static void fileUpload(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outNet = null;
        ResBody resBody = ResBody.buildFailResBody();
        try {
            responseMsgs = new ArrayList<>();
            // 向客户端发送响应正文
            outNet = response.getWriter();

            // 创建一个基于硬盘的FileItem工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置向硬盘写数据时所用的缓冲区的大小，此处为4M
            factory.setSizeThreshold(40 * 1024);
            // 设置临时目录
            // 创建一个文件上传处理器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 设置允许上传的文件的最大尺寸，此处为10M
            upload.setSizeMax(1024 * 1024 * 1024 * 10);
            List<FileItem> items = upload.parseRequest(request);
            if (CollectionUtils.isEmpty(items)) {
                return;
            }
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (!item.isFormField()) {
                    // 七牛云实现
                    processQiuniuUploadedFile(item); // 处理上传文件
                }
            }

            outNet.flush();
            // 响应结果
            if (CollectionUtils.isEmpty(responseMsgs) == false) {
                resBody = new ResBody().buildSuccessResBody(responseMsgs, null, GlobalResponseCode.SYS_SUCCESS);
            }
        } catch (Exception e) {
            logger.error("上传文件出现异常：" + e.getMessage());
            resBody = ResBody.buildFailResBody();
        } finally {
            if (null != outNet) {
                outNet.println(JSON.toJSONString(resBody));
                outNet.close();
            }
        }
    }

    /**
     * 处理七牛云上传文件
     *
     * @param item
     * @throws Exception
     */
    private static void processQiuniuUploadedFile(FileItem item) throws Exception {
        String filename = item.getName();
        long fileSize = item.getSize();

        if (filename.equals("") || fileSize == 0) {
            return;
        }

        InputStream inputStream = item.getInputStream();
        String realFileName = UUID.randomUUID().toString().replace("-", "") + filename;
        QiniuUploadUtils.uploadImg(inputStream, realFileName);
        // 组装响应对象
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRealFileName(realFileName);
        responseMsg.setNewFileName(filename);
        responseMsg.setSize(fileSize);
        responseMsg.setFileUrl(PropertyConfigurer.getProperty("resourceImageUrl") + "/" + realFileName);
        responseMsgs.add(responseMsg);
    }

    /**
     * 响应对象
     */
    static class ResponseMsg {
        private String realFileName;
        private Long size;
        private String newFileName;
        private String fileUrl;

        public String getRealFileName() {
            return realFileName;
        }

        public void setRealFileName(String realFileName) {
            this.realFileName = realFileName;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public String getNewFileName() {
            return newFileName;
        }

        public void setNewFileName(String newFileName) {
            this.newFileName = newFileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }


    /**
     * 处理七牛云上传文件
     *
     * @param inputStream
     * @throws Exception
     */
    public static String processQiuniuUploadedFileTwo(InputStream inputStream, String fileName) {
        QiniuUploadUtils.uploadImg(inputStream, fileName);
        return PropertyConfigurer.getProperty("resourceImageUrl") + "/" + fileName;
    }

}