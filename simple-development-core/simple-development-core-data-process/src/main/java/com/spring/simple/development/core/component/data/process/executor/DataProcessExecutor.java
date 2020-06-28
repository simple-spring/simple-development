package com.spring.simple.development.core.component.data.process.executor;

import com.spring.simple.development.core.component.data.process.annotation.internal.SimpleSPI;

import java.util.List;

/**
 * @author luke
 */
@SimpleSPI
public interface DataProcessExecutor<T> {
    /**
     * 执行数据转换
     *
     * @param dpoClass
     * @param returnClass
     * @return
     */
    List<T> invoke(Object dpoClass, T returnClass);
}
