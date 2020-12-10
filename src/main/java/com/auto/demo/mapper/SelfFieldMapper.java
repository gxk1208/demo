package com.auto.demo.mapper;


import com.auto.demo.base.BaseMapper;
import com.auto.demo.entity.SelfField;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface SelfFieldMapper extends BaseMapper<SelfField> {
    /**
     * 根据实体id查询该实体的字段名称集合
     * @param id 实体id
     * @return
     */
    List<SelfField> getVisibleField(@Param("id") Integer id);

    /**
     *  查询要新增的字段名称是否已经存在
     * @param pname 字段内部名称
     * @param entityId 实体id
     * @return
     */
    Integer getRepeatFieldCount(@Param("pname") String pname, @Param("entityId") Integer entityId);

    /**
     * 查询指定实体不可存在重复值的列
     * @param id 实体id
     * @return
     */
    List<SelfField> selectRepeatField(@Param("id") Integer id);

    /**
     * 查询指定实体不可为null的列
     * @param id 实体id
     * @return
     */
    List<SelfField> selectNullField(@Param("id") Integer id);
    /**
     * 查询指定实体不可修改的列
     * @param id 实体id
     * @return
     */
    List<SelfField> selectUpdateField(@Param("id") Integer id);
    /**
     * 查询指定实体类型为自增编号的列
     * @param id 实体id
     * @return
     */
    List<SelfField> selectAutoNoField(@Param("id") Integer id);
    /**
     * 查询指定实体类型为日期的列
     * @param id 实体id
     * @return
     */
    List<SelfField> selectDateField(@Param("id") Integer id);
    /**
     * 查询指定实体类型为图片的列
     * @param id 实体id
     * @return
     */
    List<SelfField> selectImgField(@Param("id") Integer id);
    /**
     * 查询指定实体类型为文件的列
     * @param id 实体id
     * @return
     */
    List<SelfField> selectFileField(@Param("id") Integer id);

    /**
     * 根据实体id获取字段集合
     * @param entityId 实体id
     * @return
     */
    List<SelfField> selectFieldsByEntityId(@Param("entityId") Integer entityId);

    /**
     * selectFieldByType
     * @param type 类型值
     * @return
     */
    List<SelfField> selectQuoteFieldByType(@Param("type") Integer type);

    /**
     * 查询指定实体指定类型的列
     * @param type 字段类型
     * @param id 实体id
     * @return
     */
    List<SelfField> selectFieldByType(@Param("type")Integer type, @Param("id")Integer id);

    /**
     *
     * @param entityIds 实体id集合
     * @param sign 标记（0 查找可展示 1查找所有）
     * @return
     */
    List<SelfField> getFieldsByEntityIds(@Param("entityIds") ArrayList<Integer> entityIds, @Param("sign") Integer sign);

    /**
     * 批量修改实体字段的排序和是否可见
     * @param fields 字段集合
     * @return
     */
    Integer updateFieldsSortAndIsVisible(@Param("fields") List<SelfField> fields);

    /**
     * 获取实体的字段集合
     * @param id 实体id
     * @param query 查询参数
     * @return
     */
    List<SelfField> getFields(@Param("id") Integer id, @Param("query") String query);

    /**
     * 获取实体的整数小数类型字段
     * @param id
     * @return
     */
    List<SelfField> selectNumField(@Param("id") Integer id);

    /**
     * 获取实体可以作为字段名称的集合
     * @param id
     * @return
     */
    List<SelfField> getNameFields(@Param("id") Integer id);

    /**
     * 获取实体自定义字段
     * @param entityId 实体id
     * @param accountId 常见人
     * @param pname 内建实体表名称
     * @return
     */
    List<SelfField> getCustomField(@Param("entityId")Integer entityId, @Param("accountId")Integer accountId, @Param("pname")String pname);
}
