package com.auto.demo.mapper;


import com.auto.demo.base.BaseMapper;
import com.auto.demo.entity.Attachment;
import com.auto.demo.param.QueryAttachParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttachmentMapper extends BaseMapper<Attachment> {
    /**
     * 获取附件
     * @param param
     * @return
     */
    List<Attachment> getAttach(@Param("param") QueryAttachParam param);
}
