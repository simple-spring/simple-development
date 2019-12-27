package com.chexin.simple.development.core.config.support;

import com.chexin.simple.development.core.annotation.Spi;
import com.chexin.simple.development.core.annotation.config.EnableElasticSearch;
import com.chexin.simple.development.core.config.SimpleSpiConfig;
import com.chexin.simple.development.core.elasticsearch.ElasticSearchConfig;

/**
 * @author ys
 * @Date 2019年12月27日
 * @Description TODO
 */
@Spi(configName="EnableElasticSearch")
public class ElasticSearchSpiConfig implements SimpleSpiConfig<EnableElasticSearch, ElasticSearchConfig> {

	@Override
	public Class<ElasticSearchConfig> getConfigClass(EnableElasticSearch t) {
		try {
            return ElasticSearchConfig.class;
        } catch (Exception ex) {
            throw new RuntimeException("ElasticSearchConfig initialization failed", ex);
        }
	}

	
}
