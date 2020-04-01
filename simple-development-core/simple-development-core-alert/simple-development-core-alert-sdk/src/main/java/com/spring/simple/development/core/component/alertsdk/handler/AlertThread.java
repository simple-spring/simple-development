package com.spring.simple.development.core.component.alertsdk.handler;

import com.alibaba.fastjson.JSONObject;
import com.spring.simple.development.core.component.alertsdk.log.AlertFileAppender;
import com.spring.simple.development.core.component.alertsdk.log.AlertLogger;
import com.spring.simple.development.core.component.alertsdk.utls.HttpClientUtils;
import com.spring.simple.development.support.constant.CommonConstant;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.GzipUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * handler thread
 *
 * @author liko wang
 */
public class AlertThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(AlertThread.class);


    private String errorLogFileName = "alertError";

    private String applicationCode;
    private String applicationToken;
    private String alertUrl;
    private String logBasePath;
    // 存储队列
    private LinkedBlockingQueue<MessageDto> alertQueue = new LinkedBlockingQueue<>();

    private boolean toStop = false;


    public AlertThread(String applicationToken, String applicationCode, String alertUrl, String logBasePath) {
        this.applicationToken = applicationToken;
        this.applicationCode = applicationCode;
        this.alertUrl = alertUrl;
        this.logBasePath = logBasePath;
        AlertFileAppender.logBasePath = logBasePath;
    }


    /**
     * kill job thread
     *
     * @param
     */
    public void toStop() throws InterruptedException {
        // 保存错误日志重试
        AlertFileAppender.contextHolder.set(errorLogFileName);
        while (true) {
            MessageDto exceptionMessage = alertQueue.poll(3L, TimeUnit.SECONDS);
            if (StringUtils.isEmpty(exceptionMessage)) {
                break;
            }
            AlertLogger.log(JSONObject.toJSONString(exceptionMessage));
        }
        this.toStop = true;
    }

    public void joinQueue(MessageDto messageDto) {
        alertQueue.add(messageDto);
    }

    @SneakyThrows
    @Override
    public void run() {
        // 重启先读取本地保存的文件
        errorMessageJoinQueue();

        Long time = System.currentTimeMillis();
        // execute
        while (!toStop) {
            MessageDto message = null;
            try {
                // to check toStop signal, we need cycle, so wo cannot use queue.take(), instand of poll(timeout)
                message = alertQueue.poll(3L, TimeUnit.SECONDS);
                if (!StringUtils.isEmpty(message)) {
                    // log filename, like "logPath/yyyy-MM-dd/9999.log"
                    String logFileName = AlertFileAppender.makeLogFileName(new Date(), Integer.valueOf(DateUtils.getStringByDate(new Date())));
                    AlertFileAppender.contextHolder.set(logFileName);
                    // execute
                    AlertLogger.log("  execute start -----------date:" + DateUtils.getCurrentTime() + " Param:" + message);
                    // invoke http
                    Map<String, String> headers = new HashMap<>();
                    headers.put("applicationCode", applicationCode);
                    headers.put("applicationToken", applicationToken);
                    String resultData = HttpClientUtils.doPostHttp(alertUrl, headers, GzipUtil.compressBase64(JSONObject.toJSONString(message)));
                    JSONObject jsonObject = JSONObject.parseObject(resultData);
                    String code = jsonObject.getString("code");
                    if(!CommonConstant.CODE1.equals(code)) {
                        throw new RuntimeException(DateUtils.getCurrentTime()+"data:"+message+ "message send fail");
                    }
                    AlertLogger.log("  execute end ----------- date:" + DateUtils.getCurrentTime());
                }
                // 重试
                if (System.currentTimeMillis() - time > 60 * 100 * 5) {
                    time = System.currentTimeMillis();
                    errorMessageJoinQueue();
                }
            } catch (Throwable e) {

                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                String errorMsg = stringWriter.toString();

                AlertLogger.log(" AlertThread Exception:" + errorMsg + "AlertThread job execute end(error) -----------");

                // 保存错误日志重试
                AlertFileAppender.appendLog(this.logBasePath + "/" + errorLogFileName + ".log", JSONObject.toJSONString(message));
            }
        }
    }

    private void errorMessageJoinQueue() {
        String logFilePath = this.logBasePath + "/" + errorLogFileName + ".log";
        File file = new File(logFilePath);
        if (file.exists() == false) {
            return;
        }
        List<String> messages = readLines(file);
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        for (String message : messages) {
            alertQueue.add(JSONObject.parseObject(message, MessageDto.class));
        }
        // 清楚本地文件
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write("");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param logFile
     * @return java.util.List<java.lang.String>
     * @author liko.wang
     * @Date 2020/3/31/031 16:09
     * @Description 读取失败的信息
     **/
    public List<String> readLines(File logFile) {
        BufferedReader reader = null;
        List<String> errorMessages = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "utf-8"));
            if (reader != null) {

                String line = null;
                while ((line = reader.readLine()) != null) {
                    errorMessages.add(line);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return errorMessages;
    }
}
