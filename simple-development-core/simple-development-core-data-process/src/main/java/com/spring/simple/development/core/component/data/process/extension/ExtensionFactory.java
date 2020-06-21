package com.spring.simple.development.core.component.data.process.extension;


import com.spring.simple.development.core.component.data.process.annotation.internal.SimpleSPI;

/**
 * ExtensionFactory
 */
@SimpleSPI
public interface ExtensionFactory {

    /**
     * Get extension.
     *
     * @param type object type.
     * @param name object name.
     * @return object instance.
     */
    <T> T getExtension(Class<T> type, String name);

}