package com.spring.simple.development.core.annotation.base.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义swagger @ApiImplicitParam
 *
 * @author liko wang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiImplicitParam {
    /**
     * 参数名
     *
     * @return
     */
    String name() default "";

    /**
     * 参数的汉字说明、解释
     */
    String description() default "";

    /**
     * 参数是否必须传
     * @return
     */
    boolean required() default true;

    /**
     *   · header --> 请求参数的获取：@RequestHeader
     *             · query --> 请求参数的获取：@RequestParam
     *             · path（用于restful接口）--> 请求参数的获取：@PathVariable
     *             · body（不常用）
     *             · form（不常用）

     * @return
     */
    String paramType() default "body";

    /**
     * 参数类型，默认String
     * @return
     */
    String dataType() default "String";

}
