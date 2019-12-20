package com.chexin.simple.development.support;


public class NoPermissionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPermissionException() {
		this.code = GlobalResponseCode.NO_PERMISSION;
	}

	public NoPermissionException(GlobalResponseCode code) {
		this.code = code;
	}

	public NoPermissionException(GlobalResponseCode code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public NoPermissionException(GlobalResponseCode code, String... formatArgs) {
		super();
		this.code = code;
		this.formatArgs = formatArgs;
	}

	public NoPermissionException(GlobalResponseCode code, Throwable cause, String... formatArgs) {
		super(cause);
		this.code = code;
		this.formatArgs = formatArgs;
	}

	public String getMessage() {
		String errorCode = getErrorCode();
		if (formatArgs != null)
			return (new StringBuilder()).append("[").append(errorCode).append("] ").append(String.format(code.getMessage(), (Object[]) formatArgs)).toString();
		else
			return (new StringBuilder()).append("[").append(errorCode).append("] ").append(code.getMessage()).toString();
	}

	public String getContent(){
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
