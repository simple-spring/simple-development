package com.spring.simple.development.demo.jobhandler;

import org.springframework.stereotype.Service;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * @author luke
 * @Date 2019年12月30日
 * @Description demo job
 */
@JobHandler("demoTask")
@Service
public class DemoTask extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("start demoTask");
        return this.doWork(param);
    }

    private ReturnT<String> doWork(String param) {
        XxlJobLogger.log("DemoTask param = " + param);
        System.out.println("XXL-JOB测试");
        return ReturnT.SUCCESS;
    }

}
