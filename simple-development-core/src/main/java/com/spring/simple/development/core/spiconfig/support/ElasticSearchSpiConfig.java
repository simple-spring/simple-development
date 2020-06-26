package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableElasticSearch;
import com.spring.simple.development.core.init.SpringBootAppInitializer;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;

/**
 * @author ys
 * @Date 2019年12月27日
 * @Description TODO
 */
@Spi(configName = "EnableElasticSearch")
public class ElasticSearchSpiConfig implements SimpleSpiConfig<EnableElasticSearch> {

    @Override
    public Class getConfigClass(EnableElasticSearch t) {
        try {
            Class<?> aClass = Class.forName("component.es.ElasticSearchConfig");
            SpringBootAppInitializer.packageNames.add(aClass.getPackage().getName());
            return aClass;
        } catch (Exception ex) {
            throw new RuntimeException("ElasticSearchConfig initialization failed", ex);
        }
    }


}
