package com.auto.demo.service;


import com.auto.demo.dto.SelfEntityDto;
import com.auto.demo.dto.SelfWorkFlowDto;
import com.auto.demo.entity.FlowNode;
import com.auto.demo.entity.SelfEntity;

/**
 * @author zhangfuliang
 * @version 1.0
 * @date 2020/6/8 11:46
 */
public interface ApprovalProcessService {
    Integer addUserWorkFlow(SelfWorkFlowDto selfWorkFlow);

    SelfEntityDto getEntity(Integer id);

    Integer updateEntity(SelfEntity entity);

    FlowNode submitWorkFlow(Integer entityId, Integer taskId);

    FlowNode test();
}
