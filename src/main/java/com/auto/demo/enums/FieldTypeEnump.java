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
public enum FieldTypeEnump {
    INT(" int(11)",1),
    VARCHAR(" varchar(255)",2),
    DATE(" date",3),
    DATETIME(" datetime",4),
    BIGINT(" bigint(13)",5),
    TEXT("text(3000)",6);
    /**
     * 描述
     */
    private String name;

    /**
     * 类型值
     */
    private Integer val;

    public static String getNameByVal(Integer val) {
        String res = null;
        for(FieldTypeEnump fte : FieldTypeEnump.values()){
            if(fte.val.equals(val)) {
                res = fte.name;
            }
        }
        return res;
    }
}
