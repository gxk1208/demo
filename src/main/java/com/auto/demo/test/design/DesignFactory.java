package com.auto.demo.test.design;

import com.auto.demo.entity.design.Design;
import com.auto.demo.entity.design.Design01;
import com.auto.demo.entity.design.Design02;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/7 9:20
 */
public class DesignFactory {

    public static Design designFactory01简单工厂(String str) {
        switch (str){
            case "design01" : return new Design01();
            case "design02" : return new Design02();
            default: throw new IllegalArgumentException("无设计模式");
        }
    }
}
