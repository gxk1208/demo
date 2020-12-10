package com.auto.demo.service;


import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.entity.ApprovalConfig;
import com.auto.demo.entity.SelfField;

import java.util.List;

/**
 * @author zhangfuliang
 * @version 1.0
 * @date 2020/6/8 11:46
 */
public interface ApprovalConfigService {
    Integer addWorkFlowConfig(ApprovalConfig approvalConfig);

    Integer modifyWorkFlowConfig(ApprovalConfig approvalConfig);

    void delWorkFlowConfig(Integer id);

    PagedList<ApprovalConfig> pageList(PageParam pageParam);

    Integer addSelfWorkFlow(Integer id);

    LayoutConfigDto getFieldLayout(Integer entityId);

    ApprovalConfig getApprovalConfig(Integer approvalId);

    void addWorkflowFields(Integer entityId, List<SelfField> fieldsList);

}
