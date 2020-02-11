package com.spring.simple.development.core.component.swagger;

import java.util.List;

/**
 * 模板数据
 *
 * @author liko wang
 */
public class CodeGenerationParams {
    /**
     * 类描述
     */
    private String classTags = "";
    /**
     * service类名
     */
    private String serviceName;

    /**
     * service日志类名
     */
    private String serviceNameLog;

    /**
     * 参数类型的包路径
     */
    private String paramTypePackagePath = "";
    /**
     * 方法参数
     */
    private List<CodeGenerationMethodParams> codeGenerationMethodParams;

    public String getClassTags() {
        return classTags;
    }

    public void setClassTags(String classTags) {
        this.classTags = classTags;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNameLog() {
        return serviceNameLog;
    }

    public void setServiceNameLog(String serviceNameLog) {
        this.serviceNameLog = serviceNameLog;
    }

    public List<CodeGenerationMethodParams> getCodeGenerationMethodParams() {
        return codeGenerationMethodParams;
    }

    public void setCodeGenerationMethodParams(List<CodeGenerationMethodParams> codeGenerationMethodParams) {
        this.codeGenerationMethodParams = codeGenerationMethodParams;
    }

    public String getParamTypePackagePath() {
        return paramTypePackagePath;
    }

    public void setParamTypePackagePath(String paramTypePackagePath) {
        this.paramTypePackagePath = paramTypePackagePath;
    }
}
