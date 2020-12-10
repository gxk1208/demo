package com.auto.demo.test.design;

import com.auto.demo.entity.design.Design;
import com.auto.demo.entity.design.Devise;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/7 17:24
 */
public interface AbsFactory {
    Design getDesign();

    Devise getDevise();
}
