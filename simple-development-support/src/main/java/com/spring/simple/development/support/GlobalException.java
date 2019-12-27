package com.spring.simple.development.support;

/**
 * @author liko.wang
 * @Date 2019/12/18/018 14:19
 * @Description 全局异常定义
 **/
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public GlobalException() {
        this.code = GlobalResponseCode.SYS_FAILED;
    }

    public GlobalException(GlobalResponseCode code) {
        this.code = code;
    }

    public GlobalException(GlobalResponseCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public GlobalException(GlobalResponseCode code, String... formatArgs) {
        super();
        this.code = code;
        this.formatArgs = formatArgs;
    }

    public GlobalException(GlobalResponseCode code, Throwable cause, String... formatArgs) {
        super(cause);
        this.code = code;
        this.formatArgs = formatArgs;
    }
	/**
	 * @author liko.wang
	 * @Date 2019/12/18/018 14:15
	 * @param  
	 * @return java.lang.String
	 * @Description //TODO 
	 **/
    public String getMessage() {
        String errorCode = getErrorCode();
        if (formatArgs != null)
            return (new StringBuilder()).append("[").append(errorCode).append("] ").append(String.format(code.getMessage(), (Object[]) formatArgs)).toString();
        else
            return (new StringBuilder()).append("[").append(errorCode).append("] ").append(code.getMessage()).toString();
    }

    public String getContent() {
        if (formatArgs != null)
            return (new StringBuilder()).append(String.format(code.getMessage(), (Object[]) formatArgs)).toString();
        else
            return (new StringBuilder()).append(code.getMessage()).toString();
    }

    public String getErrorCode() {
        return code.getCode();
    }


    public int getErrorStatus() {
        return code.getStatus();
    }

    private final GlobalResponseCode code;
    private String formatArgs[];

}
