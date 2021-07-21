package com.auto.demo.service;

import com.auto.demo.common.JsonResult;
import com.auto.demo.dto.SelfEntityDto;
import com.auto.demo.entity.SelfEntity;

import java.util.List;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 14:09
 */
public interface EntityService {
    /**
     * 添加实体
     * @param entity
     * @return
     */
    Integer add(SelfEntity entity);



    /**
     * 获取所有实体
     * @return
     */
    List<SelfEntityDto> getAllEntity();

    /**
     * 获取指定实体
     * @param id 实体ID
     * @return
     */
    SelfEntityDto getEntity(Integer id);

    /**
     * 更新实体
     * @param entity 实体信息
     * @return
     */
    Integer updateEntity(SelfEntity entity);

    /**
     * 删除实体
     * @param id 实体ID
     * @return
     */
    Integer deleteEntity(Integer id);


    /**
     * 添加详细实体
     * @param entity 详细实体
     * @return
     */
    Integer addDetailEntity(SelfEntity entity);

    /**
     * 获取可以绑定的实体列表
     * @return
     */
    List<SelfEntity> getMasterEntity();

    /**
     * 获取含有附件类型字段的的实体
     * @return
     */
    List<SelfEntity> getAttaEntity();
}
