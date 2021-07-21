package com.auto.demo.test.design;

import com.auto.demo.entity.design.*;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/7 11:37
 */


public class IDesignFactory implements AbsFactory {

  @Override
  public Design getDesign() {
    return new Design01();
  }

  @Override
  public Devise getDevise() {
    return null;
  }

}
