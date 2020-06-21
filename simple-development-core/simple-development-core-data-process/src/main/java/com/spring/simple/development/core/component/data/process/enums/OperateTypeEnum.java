package com.spring.simple.development.core.component.data.process.enums;

/**
 * @author luke
 */
public enum OperateTypeEnum {
    /**
     * 查询
     */
    SELECT("select"),
    /**
     * 删除
     */
    DELETE("delete"),
    /**
     * 新增
     */
    insert("insert"),
    /**
     * 修改
     */
    update("update");

    private String type;

    OperateTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String match(String name, OperateTypeEnum defaultItem) {
        if (name != null) {
            for (OperateTypeEnum item : OperateTypeEnum.values()) {
                if (item.name().equals(name)) {
                    return item.name();
                }
            }
        }
        throw new RuntimeException("数据组件操作类型未找到");
    }
}
