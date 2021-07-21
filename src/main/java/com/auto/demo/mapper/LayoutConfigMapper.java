package com.auto.demo.mapper;


import com.auto.demo.base.BaseMapper;
import com.auto.demo.entity.LayoutConfig;
import org.apache.ibatis.annotations.Param;

public interface LayoutConfigMapper extends BaseMapper<LayoutConfig> {
    /**
     * 新增的字段列表布局被选中，其他布局取消选中
     * @param belongEntity 所属实体
     * @param insert 新增的布局id
     * @return
     */
    Integer updateFieldSelected(@Param("eid") Integer belongEntity, @Param("lid") int insert);
    /**
     * 新增的菜单布局被选中，其他布局取消选中
     * @param id 新增的布局id
     * @return
     */
    Integer updateMenuSelected(@Param("id")Integer id);
}
