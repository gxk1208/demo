package com.auto.demo.entity.design;

import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/7 9:03
 */
@Data
public class Design01 implements Design {



    @Override
    public String explain(){
        return "设计模式01";
    }
}
