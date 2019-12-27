package com.spring.simple.development.core.dozer.converter;

import com.spring.simple.development.support.utils.DeclaredFieldsUtil;
import org.dozer.CustomConverter;

/**
 * dozer自定义转换器
 * enum与String互转
 * <p>
 * 注：
 * enum转String：枚举类中必须提供getValue()的方法。
 * String转enum：枚举类中必须提供valueOfStr()的方法。
 */
public class EnumStringConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }

        if (source instanceof Enum) {
            Object obj = DeclaredFieldsUtil.getFieldValueByName("value", source);
            if (obj == null) {
                return null;
            }
            return obj.toString();
        }

        if (source instanceof String) {
            Enum enum1 = (Enum) DeclaredFieldsUtil.getObjByMethodName("valueOfStr", destClass, source);
            if (enum1 == null) {
                return null;
            }
            return enum1;
        }

        return null;
    }

}
