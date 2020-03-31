package com.spring.simple.development.core.component.alertsdk.handler;

import com.spring.simple.development.core.component.alertsdk.log.AlertFileAppender;
import com.spring.simple.development.core.component.alertsdk.log.AlertLogger;
import com.spring.simple.development.support.utils.DateUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private LinkedBlockingQueue<String> alertQueue = new LinkedBlockingQueue<>();

    private boolean toStop = false;

    private boolean running = false;    // if running AlertThread


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
            String exceptionMessage = alertQueue.poll(3L, TimeUnit.SECONDS);
            if (StringUtils.isEmpty(exceptionMessage)) {
                break;
            }
            AlertLogger.log(exceptionMessage);
        }
        this.toStop = true;
    }

    public void joinQueue(String message){
		alertQueue.add(message);
	}

    @SneakyThrows
    @Override
    public void run() {
        // 重启先读取本地保存的文件
        errorMessageJoinQueue();
        // execute
        while (!toStop) {
            running = false;
            String message = null;
            try {
                // to check toStop signal, we need cycle, so wo cannot use queue.take(), instand of poll(timeout)
                message = alertQueue.poll(3L, TimeUnit.SECONDS);
                if (!StringUtils.isEmpty(message)) {
                    running = true;
                    // log filename, like "logPath/yyyy-MM-dd/9999.log"
                    String logFileName = AlertFileAppender.makeLogFileName(new Date(), Integer.valueOf(DateUtils.getCurrentDate()));
                    AlertFileAppender.contextHolder.set(logFileName);
                    // execute
                    AlertLogger.log("<br>-----------  execute start -----------<br>-----------date:" + DateUtils.getCurrentTime() + " Param:" + message);
                    // invoke http
                    AlertLogger.log("<br>-----------  execute end -----------<br>----------- date:" + DateUtils.getCurrentTime());
                } else {
                    errorMessageJoinQueue();
                }
            } catch (Throwable e) {

                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                String errorMsg = stringWriter.toString();

                AlertLogger.log("<br>----------- AlertThread Exception:" + errorMsg + "<br>-----------AlertThread job execute end(error) -----------");

                // 保存错误日志重试
                AlertFileAppender.contextHolder.set(errorLogFileName);
                AlertLogger.log(message);
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
            alertQueue.add(message);
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
