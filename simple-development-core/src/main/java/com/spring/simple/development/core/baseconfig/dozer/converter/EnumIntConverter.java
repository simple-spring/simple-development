package com.spring.simple.development.core.baseconfig.dozer.converter;

import com.spring.simple.development.support.utils.DeclaredFieldsUtil;
import org.dozer.CustomConverter;

import java.lang.reflect.Method;

/**
 * dozer自定义转换器
 * enum与int互转
 * <p>
 * 注：
 * enum转int：枚举类中必须提供getNum()方法。
 * <p>
 * int转enum：枚举类中必须提供valueOf()方法，
 * valueOf()这个方法的参数必须与待转换的对象类型一致，Integer不能替换int
 */
public class EnumIntConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }

        if (source instanceof Enum) {
            Object obj = DeclaredFieldsUtil.getFieldValueByName("num", source);
            if (obj == null) {
                return null;
            }

            if (destClass.isAssignableFrom(Byte.class)) {
                return Byte.valueOf(obj.toString());
            }
            if (destClass.isAssignableFrom(Short.class)) {
                return Short.valueOf(obj.toString());
            }

            return Integer.valueOf(obj.toString());
        }

        if (source instanceof Integer || source instanceof Byte || source instanceof Short) {
            Enum enum1 = (Enum) getObjByMethodName("valueOf", destClass, source);
            if (enum1 == null) {
                return null;
            }
            return enum1;
        }

        return null;
    }

    public Object getObjByMethodName(String methodName, Class<?> clsClass, Object paramObj) {
        try {
            Method[] methods = clsClass.getMethods();
            Method method = null;
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals(methodName)) {
                    // 跳过原生的valueOf（string）
                    if (methods[i].getParameterTypes().length > 0 && methods[i].getParameterTypes()[0].isAssignableFrom(String.class)) {
                        continue;
                    }
                    method = methods[i];
                    break;
                }
            }
            Object value = method.invoke(clsClass, paramObj);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
