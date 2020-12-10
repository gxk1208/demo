package com.auto.demo.test.design;

import com.auto.demo.entity.design.Design;
import com.auto.demo.entity.design.Design01;
import com.auto.demo.entity.design.Devise;
import com.auto.demo.entity.design.Devise01;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/7 17:20
 */
public class IDeviseFactory implements AbsFactory {


    @Override
    public Design getDesign() {
        return null;
    }

    @Override
    public Devise getDevise() {
        return new Devise01();
    }

}
