package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableElasticSearch;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;

/**
 * @author ys
 * @Date 2019年12月27日
 * @Description TODO
 */
@Spi(configName="EnableElasticSearch")
public class ElasticSearchSpiConfig implements SimpleSpiConfig<EnableElasticSearch> {

	@Override
	public Class getConfigClass(EnableElasticSearch t) {
		try {
            return Class.forName("component.es.ElasticSearchConfig");
        } catch (Exception ex) {
            throw new RuntimeException("ElasticSearchConfig initialization failed", ex);
        }
	}

	
}
