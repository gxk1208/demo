
package com.auto.demo.base;


import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.utils.OrderParamUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import java.io.Serializable;
import java.util.List;

/**
 * 基本Service类
 *
 * @author 袁贵
 * @version 1.0
 * @since 1.0
 */
public interface BaseService<T, ID extends Serializable> {

    BaseMapper<T> baseMapper();

    /**
     * 主键查询
     * @param id 主键
     * @return
     */
    default T get(ID id) {
        return baseMapper().selectByPrimaryKey(id);
    }

    /**
     * 条件查询（单条）
     * @param data 条件
     * @return
     */
    default T one(T data){
        return baseMapper().selectOne(data);
    }

    /**
     * 所有列表
     * @return
     */
    default List<T> list() {
        return baseMapper().selectAll();
    }

    default List<T> list(T data) {
        return baseMapper().select(data);
    }

    /**
     * 数量
     * @param data
     * @return
     */
    default Integer count(T data){
        return baseMapper().selectCount(data);
    }

    /**
     * 默认分页
     * @return
     */
    default PagedList<T> page() {
        List<T> list = baseMapper().selectAll();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        PagedList<T> pagedList = new PagedList<>();
        pagedList.setData(list);
        pagedList.setPageSize(pageInfo.getPageSize());
        pagedList.setPageNum(pageInfo.getPageNum());
        pagedList.setTotal(pageInfo.getTotal());
        return pagedList;
    }

    /**
     * 分页
     * @param pageParam 参数
     * @return
     */
    default PagedList<T> page(PageParam pageParam) {
        String orderBy = OrderParamUtil.toString(pageParam.getOrder(), pageParam.getSort());
        PageHelper.startPage(pageParam.getPage(), pageParam.getPageSize(), orderBy);
        List<T> list = baseMapper().selectAll();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        PagedList<T> pagedList = new PagedList<>();
        pagedList.setData(list);
        pagedList.setPageSize(pageInfo.getPageSize());
        pagedList.setPageNum(pageInfo.getPageNum());
        pagedList.setTotal(pageInfo.getTotal());
        return pagedList;
    }

    /**
     * 分页
     * @param pageParam 参数
     * @param data 条件
     * @return
     */
    default PagedList<T> page(PageParam pageParam, T data) {
        String orderBy = OrderParamUtil.toString(pageParam.getOrder(), pageParam.getSort());
        PageHelper.startPage(pageParam.getPage(), pageParam.getPageSize(), orderBy);
        List<T> list = baseMapper().select(data);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        PagedList<T> pagedList = new PagedList<>();
        pagedList.setData(list);
        pagedList.setPageSize(pageInfo.getPageSize());
        pagedList.setPageNum(pageInfo.getPageNum());
        pagedList.setTotal(pageInfo.getTotal());
        return pagedList;
    }

    /**
     * 主键删除
     * @param id 主键
     * @return
     */
    default int delete(ID id) {
        return baseMapper().deleteByPrimaryKey(id);
    }

    /**
     * 条件删除
     * @param data 条件
     * @return
     */
    default int delete(T data) {
        return baseMapper().delete(data);
    }

    /**
     * 添加（单条）
     * @param data 元数据
     * @return
     */
    default T save(T data) {
        baseMapper().insert(data);
        return data;
    }

    /**
     * 添加（多条）
     * @param list 元数据列表
     * @return
     */
    default List<T> save(List<T> list) {
        baseMapper().insertList(list);
        return list;
    }

    /**
     * 更新（主键）
     * @param data 元数据
     * @return
     */
    default int update(T data) {

        return baseMapper().updateByPrimaryKeySelective(data);
    }

    /**
     * 更新（条件）
     * @param data 新数据
     * @param example 条件
     * @return
     */
    default int update(T data, Object example) {
        return baseMapper().updateByExampleSelective(data, example);
    }
}
