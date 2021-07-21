package com.auto.demo.service.impl;


import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.entity.ApprovalConfig;
import com.auto.demo.entity.SelfField;
import com.auto.demo.enums.FieldTypeEnum;
import com.auto.demo.exception.MessageException;
import com.auto.demo.mapper.ApprovalConfigMapper;
import com.auto.demo.mapper.SelfFieldMapper;
import com.auto.demo.service.ApprovalConfigService;
import com.auto.demo.service.EntityService;
import com.auto.demo.service.FieldService;
import com.auto.demo.utils.OrderParamUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangfuliang
 * @version 1.0
 * @date 2020/6/8 11:47
 */
@Service
public class ApprovalConfigServiceImpl implements ApprovalConfigService {

    @Autowired
    private SelfFieldMapper fieldMapper;

    @Autowired
    ApprovalConfigMapper approvalConfigMapper;
    @Autowired
    EntityService entityService;
    @Autowired
    FieldService fieldService;


    @Override
    public Integer addWorkFlowConfig(ApprovalConfig dataConfig) {
        dataConfig.setCreateTime(System.currentTimeMillis());
        dataConfig.setUpdateTime(System.currentTimeMillis());
        dataConfig.setDeleteFlag(0);
        dataConfig.setDisableFlag(0);
        //TODO 在添加流程时为实体创建新的字段
        addWorkflowFields(Integer.parseInt(dataConfig.getBelongEntity()), new ArrayList<>());
        return approvalConfigMapper.insert(dataConfig);

    }

    @Override
    public Integer modifyWorkFlowConfig(ApprovalConfig config) {
        Integer id = config.getId();
        ApprovalConfig approvalConfig = new ApprovalConfig();
        approvalConfig.setDeleteFlag(0);
        approvalConfig.setId(id);
        ApprovalConfig configResult = approvalConfigMapper.selectOne(approvalConfig);
        if (configResult == null) {
            throw new MessageException("没有找到工作流配置文件");
        }

        long createTime = System.currentTimeMillis();
        configResult.setUpdateTime(createTime);
        configResult.setName(config.getName());
        configResult.setUpdateBy(99);
        configResult.setDisableFlag(config.getDisableFlag());
        approvalConfigMapper.updateByPrimaryKeySelective(configResult);
        return configResult.getId();
    }

    @Override
    public void delWorkFlowConfig(Integer id) {
        ApprovalConfig approvalConfig = new ApprovalConfig();
        approvalConfig.setDeleteFlag(1);
        approvalConfig.setId(id);
        approvalConfigMapper.updateByPrimaryKeySelective(approvalConfig);
    }

    @Override
    public PagedList<ApprovalConfig> pageList(PageParam pageParam) {
        String orderBy = OrderParamUtil.toString(pageParam.getOrder(), pageParam.getSort());
        PageHelper.startPage(pageParam.getPage(), pageParam.getPageSize(), orderBy);
        Example example = Example.builder(ApprovalConfig.class)
                .where(WeekendSqls.<ApprovalConfig>custom()
                        .andEqualTo(ApprovalConfig::getDeleteFlag, 0)).build();
        List<ApprovalConfig> list = approvalConfigMapper.selectByExample(example);
        PageInfo<ApprovalConfig> pageInfo = new PageInfo<>(list);
        PagedList<ApprovalConfig> pagedList = new PagedList<>();
        pagedList.setData(list);
        pagedList.setPageSize(pageInfo.getPageSize());
        pagedList.setPageNum(pageInfo.getPageNum());
        pagedList.setTotal(pageInfo.getTotal());
        return pagedList;
    }

    @Override
    public Integer addSelfWorkFlow(Integer entityId) {
        List<LayoutConfigDto> filedLayout = fieldService.getFieldLayouts(entityId);//所欲偶
        if (filedLayout == null || filedLayout.size() == 0) {
            throw new MessageException("没有进行实体布局设置");
        }
        LayoutConfigDto layoutConfigDto = filedLayout.get(0);
        //TODO 增加具体信息
        return null;
    }

    @Override
    public LayoutConfigDto getFieldLayout(Integer entityId) {
        List<LayoutConfigDto> layoutConfig = fieldService.getFieldLayouts(entityId);
        if (layoutConfig == null || layoutConfig.size() == 0) {
            throw new MessageException("不存在布局");
        }
        return layoutConfig.get(0);
    }

    @Override
    public ApprovalConfig getApprovalConfig(Integer approvalId) {
        ApprovalConfig approvalConfig = new ApprovalConfig();
        approvalConfig.setId(approvalId);
        return approvalConfigMapper.selectOne(approvalConfig);
    }

    @Override
    public void addWorkflowFields(Integer entityId, List<SelfField> fieldsList) {
        SelfField selfField = new SelfField();
        selfField.setEntityId(entityId);
        selfField.setType(FieldTypeEnum.NUMBER.getVal());
        selfField.setName("approvalId");
        List<SelfField> searchList = fieldMapper.select(selfField);
        if (searchList == null || searchList.size() == 0) {
            fieldService.addField(selfField);
        }

        selfField = new SelfField();
        selfField.setEntityId(entityId);
        selfField.setType(FieldTypeEnum.NUMBER.getVal());
        selfField.setName("approvalState");
        selfField.setComment("审批状态");
        searchList = fieldMapper.select(selfField);
        if (searchList == null || searchList.size() == 0) {
            fieldService.addField(selfField);
        }

        selfField = new SelfField();
        selfField.setEntityId(entityId);
        selfField.setType(FieldTypeEnum.NUMBER.getVal());
        selfField.setName("approvalStep");
        selfField.setComment("审批步骤");
        searchList = fieldMapper.select(selfField);
        if (searchList == null || searchList.size() == 0) {
            fieldService.addField(selfField);
        }

        fieldsList.stream().forEach(item -> {
            item.setEntityId(entityId);
            fieldService.addField(item);
        });
    }


}
