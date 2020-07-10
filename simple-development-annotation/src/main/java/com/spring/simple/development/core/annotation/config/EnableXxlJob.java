package com.spring.simple.development.core.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启job
 *
 * @author ys
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleComponent
public @interface EnableXxlJob {

	/**
	 * 指定任务Handler所在包路径
	 * @return
	 */
    String jobPackage() default "";
    
    /**
     * 执行器日志路径
     * @return
     */
    String logPath() default "/data/logs/xxl-job";
    
    /**
     * 日志保存天数
     * @return
     */
    String logRetentionDays() default "-1";
    
    /**
     * 访问令牌
     * @return
     */
    String accessToken() default "";
}
