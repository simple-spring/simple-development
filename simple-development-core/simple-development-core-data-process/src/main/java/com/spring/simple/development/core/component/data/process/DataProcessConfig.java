package com.spring.simple.development.core.component.data.process;

import com.spring.simple.development.core.component.data.process.hander.DataProcessHelper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: luke
 * @time: 2020/6/22 0022 11:29
 */
@Configurable
public class DataProcessConfig {
    public DataProcessConfig() {
        System.out.println("DataProcessConfig init");
    }

    @Bean
    public DataProcessHelper getDataProcessHelper() {
        return new DataProcessHelper();
    }
}
