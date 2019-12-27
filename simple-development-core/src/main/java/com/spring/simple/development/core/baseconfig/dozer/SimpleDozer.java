package com.spring.simple.development.core.baseconfig.dozer;

import com.spring.simple.development.core.baseconfig.dozer.converter.EnumIntConverter;
import com.spring.simple.development.core.baseconfig.dozer.converter.EnumStringConverter;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liko
 * @Date 2019-12-21 15:12
 * @DESCRIPTION TODO
 */
@Component
public class SimpleDozer extends DozerBeanMapper {
    public SimpleDozer() {
        Map<String, CustomConverter> customConvertersWithId = new HashMap<>();
        customConvertersWithId.put("enumIntConverterId", new EnumIntConverter());
        customConvertersWithId.put("enumStringConverterId", new EnumStringConverter());
        this.setCustomConvertersWithId(customConvertersWithId);
    }
}