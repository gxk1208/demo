package com.auto.demo.entity;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/23 8:28
 */
public class Config {
   private String name;
   private List<Config> sonConfigs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Config> getSonConfigs() {
        return sonConfigs;
    }

    public void setSonConfigs(List<Config> sonConfigs) {
        this.sonConfigs = sonConfigs;
    }
}
