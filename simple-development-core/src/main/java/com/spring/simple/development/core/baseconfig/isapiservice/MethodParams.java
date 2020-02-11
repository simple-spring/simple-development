package com.spring.simple.development.core.baseconfig.isapiservice;

/**
 * api方法参数集合
 * @author liko wang
 */
public class MethodParams {
    /**
     * 方法参数key
     *
     * @return
     */
    private String[] key;

    /**
     * 方法参数类型
     *
     * @return
     */
    Class<?>[] methodClass;

    public String[] getKey() {
        return key;
    }

    public void setKey(String[] key) {
        this.key = key;
    }

    public Class<?>[] getMethodClass() {
        return methodClass;
    }

    public void setMethodClass(Class<?>[] methodClass) {
        this.methodClass = methodClass;
    }
}
