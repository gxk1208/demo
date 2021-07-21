package com.auto.demo.mapper;


import com.auto.demo.base.BaseMapper;
import com.auto.demo.dto.LayoutDto;
import com.auto.demo.entity.FormLayout;

import java.util.List;

public interface FormLayoutMapper extends BaseMapper<FormLayout> {
    /**
     * 获取指定实体表单布局
     * @param entityId
     * @return
     */
    List<FormLayout> getLayoutByEntityId(Integer entityId);

    /**
     * 获取用户新增、编辑、开数据时的布局
     * @param entityId
     * @return
     */
    List<LayoutDto> getForm(Integer entityId);
}
