package com.auto.demo.mapper;


import com.auto.demo.base.BaseMapper;
import com.auto.demo.dto.SelfEntityDto;
import com.auto.demo.entity.SelfEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface SelfEntityMapper extends BaseMapper<SelfEntity> {
    /**
     * 获取主实体列表
     * @return
     */
    List<SelfEntity> getMasterEntity();
    /**
     * 获取所有列表
     * @return
     */
    List<SelfEntityDto> selectAllEntity();

    /**
     *
     * @param id
     * @return
     */
    SelfEntity getEntityByparentId(@Param("id") Integer id);

    /**
     * 获取含有附件类型字段的的实体
     * @return
     */
    Set<SelfEntity> getAttaEntity();

    /**
     * 当前账户是否已位内建实体创建字段（内建实体是否已有映射表）
     * @param name 实体名称
     * @param pname 实体表名称
     * @param i 创建人
     * @return
     */
    SelfEntity getInEntity(String name, String pname, Integer accountId);

    List<Map<String, String>> getEasyMap(Object test);
}
