package com.spring.simple.development.core.component.data.process.enums;

/**
 * sql条件
 *
 * @author luke
 */
public enum ConditionTypeEnum {
    /**
     * 并且
     */
    AND("add"),
    /**
     * 或者
     */
    OR("or");

    private String type;

    ConditionTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String match(String name, ConditionTypeEnum defaultItem) {
        if (name != null) {
            for (ConditionTypeEnum item : ConditionTypeEnum.values()) {
                if (item.name().equals(name)) {
                    return item.name();
                }
            }
        }
        throw new RuntimeException("数据组件操作类型未找到");
    }
}
