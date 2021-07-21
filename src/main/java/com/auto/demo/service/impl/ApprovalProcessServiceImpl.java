package com.auto.demo.service.impl;


import com.alibaba.fastjson.JSON;
import com.auto.demo.common.ApprovalState;
import com.auto.demo.common.FlowParser;
import com.auto.demo.dto.SelfEntityDto;
import com.auto.demo.dto.SelfWorkFlowDto;
import com.auto.demo.entity.*;
import com.auto.demo.exception.MessageException;
import com.auto.demo.mapper.ApprovalConfigMapper;
import com.auto.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangfuliang
 * @version 1.0
 * @date 2020/6/8 11:47
 */
@Service
public class ApprovalProcessServiceImpl implements ApprovalProcessService {

    @Autowired
    ApprovalConfigMapper approvalConfigMapper;
    @Autowired
    EntityService entityService;
    @Autowired
    FieldService fieldService;
    @Autowired
    ApprovalConfigService approvalConfigService;
    @Autowired
    DataService dataService;
    @Autowired
    private FlowParser flowParser;

    @Override
    public Integer addUserWorkFlow(SelfWorkFlowDto selfWorkFlow) {
        Map<String, Object> map = selfWorkFlow.getFiledValueMap();
        return dataService.addData(selfWorkFlow.getEntityId(), map);
    }

    @Override
    public SelfEntityDto getEntity(Integer id) {
        return entityService.getEntity(id);
    }

    @Override
    public Integer updateEntity(SelfEntity entity) {
        return entityService.updateEntity(entity);
    }


    @Override
    public FlowNode submitWorkFlow(Integer entityId, Integer dataId) {

        List<Map<String, Object>> result = dataService.getOneData(entityId, dataId);
        Map<String, Object> filedValueMap = result.get(0);
        Integer approvalId = (Integer) filedValueMap.get("approvalId");
        Integer approvalState = (Integer) filedValueMap.get("approvalState");
        Integer approvalStep = (Integer) filedValueMap.get("approvalStep");
        if (approvalId == null) {
            throw new MessageException("没有添加流程");
        }
        //根据approveId获取配置文件
        ApprovalConfig approvalConfig = approvalConfigService.getApprovalConfig(approvalId);
        String flowDefinition = approvalConfig.getFlowDefinition();
        //流程解析
        //修改当前任务的状态
        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("approvalState", ApprovalState.PROCESSING.getState());
        mapValue.put("id", dataId);
        dataService.updateData(entityId, mapValue);

        FlowNode rootNode = (FlowNode) JSON.parse(flowDefinition);
        FlowParser flowParser = new FlowParser();
        FlowNode searchNode = flowParser.getNextNode(rootNode, mapValue);
        if (searchNode != null) {

        }
        return searchNode;
    }

    @Override
    public FlowNode test() {
        FlowNode node = new FlowNode();

        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setName("ROOT");
        nodeEntity.setNodeId(1);
        nodeEntity.setNodeType(1);
        nodeEntity.setSignType(1);
        node.setNodeEntity(nodeEntity);


        FlowNode childNode1 = new FlowNode();
        FlowDataFilter flowDataFilter = new FlowDataFilter();
        flowDataFilter.setField("qingjiayuanyin");
        flowDataFilter.setOp("eq");
        flowDataFilter.setTableName("h_qingjiashiti");
        childNode1.addDataFilter(flowDataFilter);

        NodeEntity childNodeEntity = new NodeEntity();
        childNodeEntity.setName("分支1");
        childNodeEntity.setNodeId(11);
        childNodeEntity.setNodeType(1);
        childNodeEntity.setSignType(1);
        childNode1.setNodeEntity(childNodeEntity);
        node.addChildNode(childNode1);

        FlowNode childNode2 = new FlowNode();
        childNodeEntity = new NodeEntity();
        childNodeEntity.setName("分支1");
        childNodeEntity.setNodeId(11);
        childNodeEntity.setNodeType(1);
        childNodeEntity.setSignType(1);
        childNode2.setNodeEntity(childNodeEntity);
        node.addChildNode(childNode2);

        //分支推广
        FlowNode chidNode21 = new FlowNode();
        childNodeEntity = new NodeEntity();
        childNodeEntity.setName("分支11");
        childNodeEntity.setNodeId(111);
        childNodeEntity.setNodeType(1);
        childNodeEntity.setSignType(1);
        chidNode21.setNodeEntity(childNodeEntity);
        childNode2.addChildNode(chidNode21);


        FlowNode childNode3 = new FlowNode();
        childNodeEntity = new NodeEntity();
        childNodeEntity.setName("分支1");
        childNodeEntity.setNodeId(11);
        childNodeEntity.setNodeType(1);
        childNodeEntity.setSignType(1);
        childNode3.setNodeEntity(childNodeEntity);
        node.addChildNode(childNode3);

        FlowNode t = node.findChildNodeById(111);

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("qingjiayuanyin", "请假两天");

        FlowNode nextNode = flowParser.getNextNode(node, mapValue);
        return node;
    }
}
