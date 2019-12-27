package com.spring.simple.development.support.utils.qrCode;

/**
 * 描述:
 * 二维码异常
 *
 * @author liko
 * @create 2018-11-27 13:19
 */
public class QRParamsException extends Exception {
    private static final long serialVersionUID = 8837582301762730656L;

    //用来创建无参数对象
    public QRParamsException() {
    }

    //用来创建指定参数对象
    public QRParamsException(String message) {
        super(message);//调用超类构造器
    }
}