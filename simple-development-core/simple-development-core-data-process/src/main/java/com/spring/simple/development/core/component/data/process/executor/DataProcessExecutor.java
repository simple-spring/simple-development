package com.spring.simple.development.core.component.data.process.executor;

import com.spring.simple.development.core.component.data.process.annotation.external.SimpleDpo;
import com.spring.simple.development.core.component.data.process.annotation.internal.SimpleSPI;

/**
 * @author luke
 */
@SimpleSPI
public interface DataProcessExecutor {
    /**
     * 执行数据转换
     *
     * @param dpoClass
     * @param simpleDpo
     * @return
     */
    Object  invoke(Class dpoClass, SimpleDpo simpleDpo);
}
