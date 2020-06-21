package com.spring.simple.development.core.component.data.process.hander;


import com.spring.simple.development.core.component.data.process.annotation.external.SimpleDpo;
import com.spring.simple.development.core.component.data.process.executor.DataProcessExecutor;
import com.spring.simple.development.core.component.data.process.extension.ExtensionLoader;

import java.lang.annotation.Annotation;

/**
 * @author luke
 */
public class DataProcessHelper {
    /**
     * 数据组件助手
     *
     * @param dpoClass
     * @return
     */
    public static Object executor(Class dpoClass) {

        Annotation annotation = dpoClass.getAnnotation(SimpleDpo.class);
        if (annotation == null) {
            throw new RuntimeException("SimpleDpo annotation not found");
        }
        SimpleDpo simpleDpo = (SimpleDpo) annotation;
        // 执行数据转换
        ExtensionLoader<DataProcessExecutor> extensionLoader = ExtensionLoader.getExtensionLoader(DataProcessExecutor.class);
        DataProcessExecutor dataProcessExecutor = extensionLoader.getExtension(simpleDpo.dataModelType());
        return dataProcessExecutor.invoke(dpoClass, simpleDpo);
    }
}
