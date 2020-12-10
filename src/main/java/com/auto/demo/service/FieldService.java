package com.auto.demo.service;

import com.auto.demo.base.BaseService;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.dto.SelfFieldDto;
import com.auto.demo.dto.SimpFieldDto;
import com.auto.demo.entity.PickList;
import com.auto.demo.entity.SelfField;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 14:29
 */
public interface FieldService  {
    /**
     * 新增实体字段
     * @param field 字段对象
     * @return
     */
    Integer addField(SelfField field);

    /**
     *查看所有指定实体的字段
     * @param id 实体对象id
     * @param query 模糊查询参数
     * @return
     */
    List<SelfField> getAllField(Integer id,String query);

    /**
     * 修改实体字段
     * @param field 字段对象
     * @return
     */
    Integer updateField(SelfField field);

    /**
     * 修改实体字段列表布局
     * @param layoutConfigDto 字段展示列表布局对象
     * @return
     */
    Integer updateFieldLayout(LayoutConfigDto layoutConfigDto);

    /**
     * 获取实体字段
     * @param id 字段id
     * @return
     */
    SelfFieldDto getField(Integer id);

    /**
     * 删除实体字段
     * @param id 字段id
     * @return
     */
    Integer deleteField(Integer id);

    /**
     * 获取指定实体可以作为名称字段的字段集合
     * @param id
     * @return
     */
    List<SelfField> getNameFields(Integer id);

    /**
     * 添加下拉列表类型字段的选项
     * @param pickLists
     * @return
     */
    Integer addPickList(Integer entityId,Integer fieldId,List<PickList> pickLists);

    /**
     * 获取字段下拉列表选线
     * @param entityId 实体id
     * @param fieldId 字段id
     * @return
     */
    List<PickList> getPickList(Integer entityId, Integer fieldId);



    /**
     *添加字段显示列表布局
     * @param layoutConfigDto 字段展示布局配置对象
     * @return
     */
    Integer saveFieldLayout(LayoutConfigDto layoutConfigDto);

    /**
     * 获取字段显示列表布局
     * @param entityId 实体id
     * @return
     */
    List<LayoutConfigDto> getFieldLayouts(Integer entityId);

    /**
     * 获取指定布局
     * @param layoutId 字段列表布局id
     * @return
     */
    LayoutConfigDto getFieldLayout(Integer layoutId);

    /**
     * 获取实体默认字段布局
     * @param id 实体id
     * @return
     */
    List<SelfField> getDefaultLayout(Integer id);

    /**
     * 获取实体可以展示的字段
     * @param id 实体id
     * @return
     */
    List<SelfField> getAllShowField(Integer id);

    /**
     * 获取实体自定义字段
     * @param entityId 实体id
     * @param accountId 创建人
     * @return
     */
    List<SelfField> getCustomField(Integer entityId, Integer accountId);

    List<SelfField> getTargetField(Integer entityId);
}
