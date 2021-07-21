/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package com.auto.demo.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

/**
 * Mapper for PostgreSql
 *
 * @author yuan
 * @version 1.0
 * @since 1.0
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
    /**
     * 插入实体返回主键id
     * @param record
     * @return
     */
    @Override
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @InsertProvider(type = BaseInsertProvider.class, method = "dynamicSQL")
    int insert(T record);

    /**
     * 插入实体返回主键id
     *
     * @param record
     * @return
     */
    @Override
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @InsertProvider(type = BaseInsertProvider.class, method = "dynamicSQL")
    int insertSelective(T record);
}
