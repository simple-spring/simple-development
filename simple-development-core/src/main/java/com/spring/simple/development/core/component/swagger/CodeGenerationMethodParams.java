package com.spring.simple.development.core.component.swagger;

/**
 * 模板方法参数
 *
 * @author liko wang
 */
public class CodeGenerationMethodParams {
    /**
     * 是否需要登录
     */
    private String isLogin;

    /**
     * 方法名
     */
    private String methodName;
    /**
     * 地址
     */
    private String mappingUrl;
    /**
     * control方法简述
     */
    private String apiOperationValue = "";
    /**
     * control方法详细说明
     */
    private String apiOperationValueNotes = "";
    /**
     * control方法参数名
     */
    private String apiImplicitParamName = "";

    /**
     * control方法参数描述
     */
    private String apiImplicitParamValue = "";

    /**
     * control方法参数描述
     */
    private String apiImplicitParamDataType = "string";

    /**
     * control方法参数类型
     */
    private String requestBodyType = "ReqBody ";
    /**
     * control方法参数变量名
     */
    private String requestBodyName = "reqBody";

    /**
     * 调用的方法名
     */
    private String invokeMethodName;
    /**
     * 返回值类型名称
     */
    private String resultDataType;
    /**
     * 返回值类型包路径
     */
    private String resultDataTypePackagePath;

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMappingUrl() {
        return mappingUrl;
    }

    public void setMappingUrl(String mappingUrl) {
        this.mappingUrl = mappingUrl;
    }

    public String getApiOperationValue() {
        return apiOperationValue;
    }

    public void setApiOperationValue(String apiOperationValue) {
        this.apiOperationValue = apiOperationValue;
    }

    public String getApiOperationValueNotes() {
        return apiOperationValueNotes;
    }

    public void setApiOperationValueNotes(String apiOperationValueNotes) {
        this.apiOperationValueNotes = apiOperationValueNotes;
    }

    public String getApiImplicitParamName() {
        return apiImplicitParamName;
    }

    public void setApiImplicitParamName(String apiImplicitParamName) {
        this.apiImplicitParamName = apiImplicitParamName;
    }

    public String getApiImplicitParamValue() {
        return apiImplicitParamValue;
    }

    public void setApiImplicitParamValue(String apiImplicitParamValue) {
        this.apiImplicitParamValue = apiImplicitParamValue;
    }

    public String getApiImplicitParamDataType() {
        return apiImplicitParamDataType;
    }

    public void setApiImplicitParamDataType(String apiImplicitParamDataType) {
        this.apiImplicitParamDataType = apiImplicitParamDataType;
    }

    public String getRequestBodyType() {
        return requestBodyType;
    }

    public void setRequestBodyType(String requestBodyType) {
        this.requestBodyType = requestBodyType;
    }

    public String getRequestBodyName() {
        return requestBodyName;
    }

    public void setRequestBodyName(String requestBodyName) {
        this.requestBodyName = requestBodyName;
    }

    public String getInvokeMethodName() {
        return invokeMethodName;
    }

    public void setInvokeMethodName(String invokeMethodName) {
        this.invokeMethodName = invokeMethodName;
    }

    public String getResultDataType() {
        return resultDataType;
    }

    public void setResultDataType(String resultDataType) {
        this.resultDataType = resultDataType;
    }

    public String getResultDataTypePackagePath() {
        return resultDataTypePackagePath;
    }

    public void setResultDataTypePackagePath(String resultDataTypePackagePath) {
        this.resultDataTypePackagePath = resultDataTypePackagePath;
    }
}
