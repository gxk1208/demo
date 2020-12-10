package com.auto.demo.common;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
//excel表格注解
public @interface Excel {

    @AliasFor("name")
    String value() default "";

    //字段excel表头名称
    @AliasFor("value")
    String name() default "";

    //是否为日期格式
    String dateFormat() default "";

    //字段是否为必填项
    boolean require() default false;

    //导入字段
    boolean imp() default true;

    //导出字段
    boolean exp() default true;

    //列索引
    int index() default -1;

    //正则校验
    String regexp() default "";

    ExcelFormat format() default ExcelFormat.STRING;
}
