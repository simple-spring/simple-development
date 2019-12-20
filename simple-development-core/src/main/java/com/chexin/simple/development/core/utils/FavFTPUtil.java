package com.chexin.simple.development.core.utils;

import com.chexin.simple.development.core.properties.PropertyConfigurer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;


public class FavFTPUtil {

    /**
     * 上传文件（可供Action/Controller层使用）
     *
     * @param hostname    FTP服务器地址
     * @param port        FTP服务器端口号
     * @param username    FTP登录帐号
     * @param password    FTP登录密码
     * @param pathname    FTP服务器保存目录
     * @param fileName    上传到FTP服务器后的文件名称
     * @param inputStream 输入文件流
     * @return
     */
    public static boolean uploadFile(String hostname, int port,
                                     String username, String password, String pathname, String fileName,
                                     InputStream inputStream) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            // 连接FTP服务器
            ftpClient.connect(hostname, port);
            // 登录FTP服务器
            boolean bo = ftpClient.login(username, password);
            // 是否成功登录FTP服务器
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            //下面的目录地址是FTP服务器地址,admin就是你设定的用户名(别名)/pic是你需要访问文件的父文件夹名
            //		 ftpClient.makeDirectory("/home/nfyg/pic/xx");
            //  ftpClient.changeWorkingDirectory("/home/nfyg/pic/xx");

            flag = ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 下载文件到本地
     *
     * @param remoteUrl 被下载的文件地址
     * @param filename  本地文件名
     * @throws Exception 各种异常
     */
    public static boolean download(String remoteUrl, String filename) {
        boolean flag = true;
        // 构造URL
        InputStream is = null;
        OutputStream os = null;
        try {
            if (remoteUrl.startsWith("//")) {
                remoteUrl = "http:" + remoteUrl;
            }
            URL url = new URL(remoteUrl);
            // 打开连接
            URLConnection con = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) con;
            httpConnection.setRequestProperty("Referer", "http://www.toutiao.com");
            httpConnection.connect();
            // 输入流
            is = httpConnection.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            os = new FileOutputStream(filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
            return flag;
        }

    }


    /**
     * 上传文件（可对文件进行重命名）
     *
     * @param hostname       FTP服务器地址
     * @param port           FTP服务器端口号
     * @param username       FTP登录帐号
     * @param password       FTP登录密码
     * @param pathname       FTP服务器保存目录
     * @param filename       上传到FTP服务器后的文件名称
     * @param originfilename 待上传文件的名称（绝对地址）
     * @return
     */
    public static boolean uploadFileFromProduction(String hostname, int port,
                                                   String username, String password, String pathname, String filename,
                                                   String originfilename) {
        boolean flag = false;
        try {
            InputStream inputStream = new FileInputStream(new File(originfilename));
            flag = uploadFile(hostname, port, username, password, pathname, filename, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传文件（不可以进行文件的重命名操作）
     *
     * @param hostname       FTP服务器地址
     * @param port           FTP服务器端口号
     * @param username       FTP登录帐号
     * @param password       FTP登录密码
     * @param pathname       FTP服务器保存目录
     * @param originfilename 待上传文件的名称（绝对地址）
     * @return
     */
    public static boolean uploadFileFromProduction(String hostname, int port,
                                                   String username, String password, String pathname,
                                                   String originfilename) {
        boolean flag = false;
        try {
            String fileName = new File(originfilename).getName();
            InputStream inputStream = new FileInputStream(new File(originfilename));
            flag = uploadFile(hostname, port, username, password, pathname, fileName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除文件
     *
     * @param hostname FTP服务器地址
     * @param port     FTP服务器端口号
     * @param username FTP登录帐号
     * @param password FTP登录密码
     * @param pathname FTP服务器保存目录
     * @param filename 要删除的文件名称
     * @return
     */
    public static boolean deleteFile(String hostname, int port,
                                     String username, String password, String pathname, String filename) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            // 连接FTP服务器
            ftpClient.connect(hostname, port);
            // 登录FTP服务器
            ftpClient.login(username, password);
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {

                }
            }
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param hostname  FTP服务器地址
     * @param port      FTP服务器端口号
     * @param username  FTP登录帐号
     * @param password  FTP登录密码
     * @param pathname  FTP服务器文件目录
     * @param filename  文件名称
     * @param localpath 下载后的文件路径
     * @return
     */
    public static boolean downloadFile(String hostname, int port,
                                       String username, String password, String pathname, String filename,
                                       String localpath) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            // 连接FTP服务器
            ftpClient.connect(hostname, port);
            // 登录FTP服务器
            ftpClient.login(username, password);
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    OutputStream os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {

                }
            }
        }
        return flag;
    }

    /**
     * 流转化文件
     *
     * @param ins
     * @param file
     * @return
     */
    public static File inputstreamtofile(InputStream ins, File file) {
        OutputStream os;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return file;
    }

    public static String uploadFile(String picture) {
        // 解码
        byte[] asBytes = Base64.getDecoder().decode(picture);
        // 上传到ftp
        BufferedInputStream fis = null;
        String fileName = DateUtils.getCurrentTimestamp() + PrimaryKeyGenerator.getInstance().nextId();
        String path = "image/" + DateUtils.getDay() + "/";
        try {
            fis = new BufferedInputStream(new ByteArrayInputStream(asBytes));
            if (FavFTPUtil.uploadFile(PropertyConfigurer.getProperty("FTP_HOST_NAME"), 21,
                    PropertyConfigurer.getProperty("FTP_USER_NAME"), PropertyConfigurer.getProperty("FTP_USER_PASSWORD"),
                    PropertyConfigurer.getProperty("FTP_LOCAL_PATH") + path, fileName, fis)) {
                return "/uploads/" + path + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return null;
    }
}
