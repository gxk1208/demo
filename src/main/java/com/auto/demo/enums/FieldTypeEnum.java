package com.auto.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 15:54
 */
@Getter
@AllArgsConstructor
public enum FieldTypeEnum {
    NUMBER("整数", 1,"int(11)"),
    TEXT("文本", 2,"varchar(255)"),
    DATE("日期", 3,"date"),
    DATETIME("日期时间", 4,"timestamp"),
    BIGINT("时间戳",5,"bigint(13)"),
    NTEXT("多行文本",6,"text(3000)"),
    AVATAR("头像", 7, "varchar(255)"),
    PICKLIST("列表",8,"varchar(255)"),
    SERIES("自动编号", 9, "varchar(255)"),
    IMAGE("图片", 10, "varchar(700)"),
    FILE("附件", 11, "varchar(700)"),
    REFERENCE("引用", 12,"int(11)"),
    EMAIL("邮箱",13, "varchar(255)"),
    URL("链接", 14, "varchar(255)"),
    PHONE("电话", 15, "varchar(255)"),
    DECIMAL("货币", 16, "decimal(29,8)"),
    MULTISELECT("多选", 17,"varchar(255)"),
    BOOL("布尔", 18, "tinyint(1)"),;
    /**
     * 描述
     */
    private String name;

    /**
     * 类型值
     */
    private Integer val;

    /**
     * 类型值
     */
    private String type;

    public static String getNameByVal(Integer val) {
        String res = null;
        for(FieldTypeEnum fte : FieldTypeEnum.values()){
            if(fte.val.equals(val)) {
                res = fte.name;
            }
        }
        return res;
    }

    public static String getTypeByVal(Integer val) {
        String res = null;
        for(FieldTypeEnum fte : FieldTypeEnum.values()){
            if(fte.val.equals(val)) {
                res = fte.type;
            }
        }
        return res;
    }
}
