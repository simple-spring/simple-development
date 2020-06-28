package com.spring.simple.development.core.component.data.process.hander;


import com.spring.simple.development.core.component.data.process.annotation.external.SimpleDpo;
import com.spring.simple.development.core.component.data.process.executor.DataProcessExecutor;
import com.spring.simple.development.core.component.data.process.extension.ExtensionLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author luke
 */
@Component
public class DataProcessHelper<T> {
    /**
     * 数据组件助手
     *
     * @param dpoClass
     * @return
     */
    public List<T> executor(Object dpoClass, T t) {
        // is exist simpleDpo
        Annotation annotation = dpoClass.getClass().getAnnotation(SimpleDpo.class);
        if (annotation == null) {
            throw new RuntimeException("SimpleDpo annotation not found");
        }
        SimpleDpo simpleDpo = (SimpleDpo) annotation;
        // fond service
        ExtensionLoader<DataProcessExecutor> extensionLoader = ExtensionLoader.getExtensionLoader(DataProcessExecutor.class);
        DataProcessExecutor<T> dataProcessExecutor = extensionLoader.getExtension(simpleDpo.dataModelType());
        // invoke data process
        return dataProcessExecutor.invoke(dpoClass, t);
    }
}
