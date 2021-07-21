package com.auto.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auto.demo.common.JsonResult;
import com.auto.demo.dto.SelfEntityDto;
import com.auto.demo.dto.SelfWorkFlowDto;
import com.auto.demo.entity.FlowNode;
import com.auto.demo.entity.SelfEntity;
import com.auto.demo.service.ApprovalProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangfuliang
 * @version 1.0
 * @date 2020/6/8 14:18
 */
@RestController
@RequestMapping("/api/approval/process")
@Api(tags = "工作流处理")
@Slf4j
public class ApprovalProcessController {
    @Autowired
    ApprovalProcessService approvalProcessService;

    @ApiOperation("新建用户流程申请草稿")
    @PostMapping("/add-user-workflow")
    public JsonResult<Integer> addUserWorkFlow(@RequestBody SelfWorkFlowDto selfWorkFlow) {
        return JsonResult.success(approvalProcessService.addUserWorkFlow(selfWorkFlow));
    }

    @ApiOperation("提交审批申请")
    @PostMapping("/submit")
    public JsonResult<FlowNode> submitWorkFlow(@RequestParam("entityId") Integer entityId, @RequestParam("taskId") Integer taskId) {
        FlowNode res = approvalProcessService.submitWorkFlow(entityId, taskId);
        return JsonResult.success(res);
    }


    @GetMapping("/get-entity")
    @ApiOperation(value = "获取指定实体")
    public JsonResult<SelfEntityDto> getEntity(@RequestParam("id") Integer id) {
        return JsonResult.success(approvalProcessService.getEntity(id));
    }

    @PostMapping("/update-entity")
    @ApiOperation(value = "修改实体")
    public JsonResult<Integer> updateEntity(@RequestBody SelfEntity entity) {
        return JsonResult.success(approvalProcessService.updateEntity(entity));
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试数据")
    public JsonResult<FlowNode> test() {
        FlowNode res = approvalProcessService.test();
        String data = JSON.toJSONString(res);
        FlowNode temp = JSONObject.parseObject(data, FlowNode.class);

//        NodeEntity nodeEntity = (NodeEntity) jsonObject.get("nodeEntity");
//        JSONArray list = jsonObject.getJSONArray("childNodes");
//        for (int i=0; i<list.size(); i++){
//            FlowNode temp = (FlowNode) list.get(i);
//            NodeEntity tempNode = temp.getNodeEntity();
//            String name = tempNode.getName();
//            name = tempNode.getName();
//        }
        return JsonResult.success(temp);
    }


}
