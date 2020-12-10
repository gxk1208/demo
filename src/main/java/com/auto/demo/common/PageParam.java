/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package com.auto.demo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 带分页的画面查询参数
 * @author 袁贵
 * @version 1.0
 * @since  1.0
 */
@ApiModel("分页查询参数")
public class PageParam {

    /** 使用PageHelper，页码从1开始 */
	@ApiModelProperty(value = "页码从1开始", example = "1", allowableValues="range[1, infinity]")
    int currentPage = 1;

    /** 每页件数 */
	@ApiModelProperty(value = "每页记录数", example = "10", allowableValues="range[2, infinity]")
    int pageSize = 10;

	@ApiModelProperty(value = "排序字段")
    String order;

	@ApiModelProperty(value = "排序")
    int sort;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPage() {
        return this.currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getOffSet(){

        return (this.currentPage - 1) * this.getPageSize();
    }

    @Override
    public String toString() {
        return "{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", order='" + order + '\'' +
                ", sort=" + sort +
                '}';
    }
}
