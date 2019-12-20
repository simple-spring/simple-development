package com.chexin.simple.development.core.init;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 15:24
 * @Description //TODO
 **/

import com.chexin.simple.development.core.constant.ValueConstant;
import com.chexin.simple.development.core.jdbc.DruidDataSourceConfig;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.*;
import com.chexin.simple.development.core.dozer.converter.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.chexin.simple.development.core.handler","com.chexin.simple.development.core.jdbc"})
@Import(DruidDataSourceConfig.class)
public class RootConfig {
    @Bean
    public BeanNameAutoProxyCreator proxycreate() {
        BeanNameAutoProxyCreator proxycreate = new BeanNameAutoProxyCreator();
        proxycreate.setProxyTargetClass(true);
        proxycreate.setBeanNames("*ServiceImpl");
        proxycreate.setInterceptorNames("transactionInterceptor");
        return proxycreate;
    }
    @Bean
    public DozerBeanMapper dozer() {
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        Map<String, CustomConverter> customConvertersWithId = new HashMap<>();
        customConvertersWithId.put("enumIntConverterId", new EnumIntConverter());
        customConvertersWithId.put("enumStringConverterId", new EnumStringConverter());
        dozerBean.setCustomConvertersWithId(customConvertersWithId);
        return dozerBean;
    }
}