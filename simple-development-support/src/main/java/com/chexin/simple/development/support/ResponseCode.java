package com.chexin.simple.development.support;

/**
 * @author liko.wang
 * @Date 2019/12/18/018 15:31
 * @Description 业务code定义
 **/
public class ResponseCode extends GlobalResponseCode {

    /**
     * 业务错误状态码
     */
    /**
     * 请求参数为空
     */
    public static final int PARAM_IS_EMPTY = 6;
    /**
     * 数据已存在
     */
    public static final int DATA_EXIST = 7;
    /**
     * 数据不存在
     */
    public static final int DATA_NOT_EXIST = 8;
    /**
     * 请求参数无效
     */
    public static final int PARAM_INVALID = 9;

    /**
     * 用户被禁止登陆
     */
    public static final int USER_ARE_BANNED = 10;

    /**
     * 请重新登录
     */
    public static final int USER_RELOGIN = 11;

    /**
     * 密码无效
     */
    public static final int PWD_OR_CODE_INVALID = 12;

    /**
     * 设置基础信息错误
     */
    public static final int SET_BASE_EXCEPTION = 13;

    /**
     * 解密失败
     */
    public static final int DECRYPT_FAIL = 14;

    /**
     * 加密失败
     */
    public static final int ENCRYPT_FAIL = 15;

    /**
     * 点击太快了,请稍后重试
     */
    public static final int IDEMPOTENT_INVALID = 16;
    /**
     * 非法操作
     */
    public static final int ILLEGAL_OPERATION = 17;

    /**
     * 业务异常错误码
     */
    public static final String PARAM_IS_EMPTY_CODE = "PARAM_IS_EMPTY";
    public static final String DATA_EXIST_CODE = "DATA_EXIST";
    public static final String DATA_NOT_EXIST_CODE = "DATA_NOT_EXIST";
    public static final String PARAM_INVALID_CODE = "PARAM_INVALID";
    public static final String USER_ARE_BANNED_CODE = "USER_ARE_BANNED";
    public static final String USER_RELOGIN_CODE = "USER_RELOGIN";
    public static final String PWD_OR_CODE_INVALID_CODE = "PWD_OR_CODE_INVALID";
    public static final String SET_BASE_EXCEPTION_CODE = "SET_BASE_EXCEPTION";
    public static final String DECRYPT_FAIL_CODE = "DECRYPT_FAIL";
    public static final String ENCRYPT_FAIL_CODE = "ENCRYPT_FAIL";
    public static final String IDEMPOTENT_INVALID_CODE = "ENCRYPT_FAIL";
    public static final String ILLEGAL_OPERATION_CODE = "ILLEGAL OPERATION";


    public static final GlobalResponseCode RES_PARAM_IS_EMPTY = new GlobalResponseCode(PARAM_IS_EMPTY, PARAM_IS_EMPTY_CODE, "%s");
    public static final GlobalResponseCode RES_DATA_EXIST = new GlobalResponseCode(DATA_EXIST, DATA_EXIST_CODE, "%s");
    public static final GlobalResponseCode RES_DATA_NOT_EXIST = new GlobalResponseCode(DATA_NOT_EXIST, DATA_NOT_EXIST_CODE, "%s");
    public static final GlobalResponseCode RES_PARAM_INVALID = new GlobalResponseCode(PARAM_INVALID, PARAM_INVALID_CODE, "%s");
    public static final GlobalResponseCode RES_USER_ARE_BANNED = new GlobalResponseCode(USER_ARE_BANNED, USER_ARE_BANNED_CODE, "%s");
    public static final GlobalResponseCode RES_USER_RELOGIN = new GlobalResponseCode(USER_RELOGIN, USER_RELOGIN_CODE, "%s");
    public static final GlobalResponseCode RES_PWD_OR_CODE_INVALID = new GlobalResponseCode(PWD_OR_CODE_INVALID, PWD_OR_CODE_INVALID_CODE, "%s");
    public static final GlobalResponseCode RES_SET_BASE_EXCEPTION = new GlobalResponseCode(SET_BASE_EXCEPTION, SET_BASE_EXCEPTION_CODE, "%s");
    public static final GlobalResponseCode RES_DECRYPT_FAIL = new GlobalResponseCode(DECRYPT_FAIL, DECRYPT_FAIL_CODE, "%s");
    public static final GlobalResponseCode RES_ENCRYPT_FAIL = new GlobalResponseCode(ENCRYPT_FAIL, ENCRYPT_FAIL_CODE, "%s");
    public static final GlobalResponseCode RES_IDEMPOTENT_INVALID = new GlobalResponseCode(IDEMPOTENT_INVALID, IDEMPOTENT_INVALID_CODE, "%s");
    public static final GlobalResponseCode RES_ILLEGAL_OPERATION = new GlobalResponseCode(ILLEGAL_OPERATION, ILLEGAL_OPERATION_CODE, "%s");


    private ResponseCode(int status, String code, String message) {
        super(status, code, message);
    }

    public static ResponseCode newResponse(int status, String code, String message) {
        return new ResponseCode(status, code, message);
    }
}