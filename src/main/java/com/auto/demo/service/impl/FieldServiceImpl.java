package com.auto.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.dto.SelfFieldDto;
import com.auto.demo.dto.SimpFieldDto;
import com.auto.demo.entity.LayoutConfig;
import com.auto.demo.entity.PickList;
import com.auto.demo.entity.SelfEntity;
import com.auto.demo.entity.SelfField;
import com.auto.demo.enums.FieldTypeEnum;
import com.auto.demo.exception.MessageException;
import com.auto.demo.mapper.LayoutConfigMapper;
import com.auto.demo.mapper.PickListMapper;
import com.auto.demo.mapper.SelfEntityMapper;
import com.auto.demo.mapper.SelfFieldMapper;
import com.auto.demo.param.SimpFieldParam;
import com.auto.demo.service.FieldService;
import com.auto.demo.utils.WordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 14:30
 */
@Service
public class FieldServiceImpl implements FieldService {
    @Autowired
    private SelfFieldMapper fieldMapper;

    @Autowired
    private SelfEntityMapper entityMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PickListMapper pickListMapper;

    @Autowired
    private LayoutConfigMapper layoutConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addField(SelfField field) {
        if(WordUtil.isSpecialChar(field.getName())){
            throw new MessageException("字段名称不能包含特殊符号标点符号以及空格");
        }
        //获取新增字段的实体
        SelfEntity selfEntity = entityMapper.selectByPrimaryKey(field.getEntityId());
        SelfEntity inEntity = new SelfEntity();
        SelfEntity interEntity = entityMapper.getInEntity(selfEntity.getName(),selfEntity.getPname(),99);
        if(1 == selfEntity.getType() && null == interEntity){
            //当前账户是否已为内建实体创建字段（内建实体是否已有映射表）
            BeanUtils.copyProperties(selfEntity,inEntity);
            while (checkEntityName(inEntity.getPname())){
                inEntity.setPname(inEntity.getPname()+(int)(Math.random()*100+1));
            }
            StringBuilder inSql = new StringBuilder();
            inSql.append(" CREATE TABLE ").append(optimizeName(inEntity.getPname())).append(" LIKE ").append(optimizeName(selfEntity.getPname()));
            jdbcTemplate.execute(inSql.toString());
            inEntity.setId(null);
            inEntity.setType(3);
            entityMapper.insert(inEntity);
            LayoutConfig lc = new LayoutConfig();
            lc.setBelongEntity(inEntity.getId());
            lc.setConfigName(inEntity.getName()+"表单模板");
            lc.setSelected(true);
            lc.setType(3);
            lc.setConfig("[]");
            layoutConfigMapper.insert(lc);
            List<SelfField> selfFields = fieldMapper.selectFieldsByEntityId(field.getEntityId());
            for (SelfField selfField : selfFields) {
                selfField.setId(null);
                selfField.setEntityId(inEntity.getId());
            }
            fieldMapper.insertList(selfFields);
        }
        field.setPname(WordUtil.getPingYin(field.getName()));
        if(1 == selfEntity.getType()){
            field.setEntityId(null == interEntity ?inEntity.getId():interEntity.getId());
        }
        while (checkFieldName(field.getPname(),field.getEntityId())){
            field.setPname(field.getPname()+(int)(Math.random()*100+1));
        }
        field.setDisplayType(FieldTypeEnum.getNameByVal(field.getType()));
        field.setCreateType(true);
        field.setIsBuild(false);
        field.setIsVisible(false);
        if(9 != field.getType()){
            field.setUpdateable(true);
            field.setRepeatable(true);
            field.setNullable(true);
        }else{
            field.setUpdateable(false);
            field.setRepeatable(false);
            field.setNullable(false);
        }
        field.setSort(0);
        fieldMapper.insert(field);

        StringBuilder sql = new StringBuilder("ALTER TABLE ");
//        sql.append(optimizeName(selfEntity.getPname()));
        sql.append(optimizeName( 1 == selfEntity.getType() ? null == interEntity ?inEntity.getPname():interEntity.getPname() :selfEntity.getPname()));
        sql.append(" ADD COLUMN ").append(optimizeName(field.getPname())).append(FieldTypeEnum.getTypeByVal(field.getType()));
        if(null != field.getDefaultValue()){
            sql.append(" default").append(" '").append(field.getDefaultValue()).append("' ");
        }
        if(null != field.getComment()){
            sql.append(" COMMENT '").append(field.getComment()).append("'");
        }
        jdbcTemplate.execute(sql.toString());
        return 1;
    }

    private boolean checkFieldName(String pname, Integer entityId){
        return fieldMapper.getRepeatFieldCount(pname, entityId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateField(SelfField field) {
        //获取原有字段名称
        SelfField selfField = fieldMapper.selectByPrimaryKey(field.getId());
        //修改字段表
        if(null != field.getName()){
            field.setPname(WordUtil.getPingYin(field.getName()));
        }
        fieldMapper.updateByPrimaryKeySelective(field);
        //获取当前字段所属的实体
        SelfEntity selfEntity = entityMapper.selectByPrimaryKey(field.getEntityId());
        //修改生成的数据表的字段
        StringBuilder sql = new StringBuilder("ALTER TABLE ");
        sql.append(optimizeName(selfEntity.getPname())).append(" CHANGE ").append(optimizeName(selfField.getPname())).append(" ");
        if(null != field.getName()){
            sql.append(optimizeName(field.getPname()));
        }else {
            sql.append(optimizeName(selfField.getPname()));
        }
        sql.append(" ").append(FieldTypeEnum.getTypeByVal(selfField.getType()));
        if(null != field.getDefaultValue()){
            sql.append(" default").append(" '").append(field.getDefaultValue()).append("' ");
        }
        if(null != field.getComment()){
            sql.append(" COMMENT '").append(field.getComment()).append("'");
        }
        jdbcTemplate.execute(sql.toString());
        return 1;
    }

    @Override
    public Integer deleteField(Integer id) {
        //获取要删除的字段的信息
        SelfField selfField = fieldMapper.selectByPrimaryKey(id);
        //获取要删除的字段所属的实体
        SelfEntity selfEntity = entityMapper.selectByPrimaryKey(selfField.getEntityId());
        //删除字段表记录
        fieldMapper.deleteByPrimaryKey(id);
        //删除生成表的字段
        StringBuilder sql = new StringBuilder("ALTER TABLE ");
        sql.append(optimizeName(selfEntity.getPname())).append(" DROP COLUMN ").append(optimizeName(selfField.getPname()));
        jdbcTemplate.execute(sql.toString());
        return 1;
    }

    @Override
    public SelfFieldDto getField(Integer id) {
        SelfField selfField = fieldMapper.selectByPrimaryKey(id);
        SelfFieldDto selfFieldDto = new SelfFieldDto();
        BeanUtils.copyProperties(selfField,selfFieldDto);
        selfFieldDto.setDisplayType(selfFieldDto.getDisplayType()+"("+selfField+")");
        if(8 == selfField.getType() || 17 == selfField.getType()){
            //获取字段对应的下拉选项列表
            Example example = new Example(PickList.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("entityId",selfField.getEntityId());
            criteria.andEqualTo("fieldId",selfField.getId());
            criteria.andEqualTo("isActive",false);
            List<PickList> pickLists = pickListMapper.selectByExample(example);
            selfFieldDto.setValues(pickLists);
        }else if(10 == selfField.getType() || 11 == selfField.getType()){
            selfFieldDto.setValues(Integer.parseInt(selfField.getExtraValue()));
        }else if(12 == selfField.getType()){
            SelfEntity selfEntity = entityMapper.selectByPrimaryKey(Integer.parseInt(selfField.getExtraValue()));
            StringBuilder name = new StringBuilder(selfEntity.getName());
            name.append("(").append(selfEntity.getPname()).append(")");
            selfFieldDto.setValues(name.toString());
        }else if(3 == selfField.getType() || 4 == selfField.getType()){
            selfFieldDto.setValues(selfField.getExtraValue());
        }else if(9 == selfField.getType()){
            String[] split = selfField.getExtraValue().split(",");
            HashMap<String, Object> map = new HashMap<>();
            map.put("noRule",split[0]);
            map.put("toZero",split[1]);
            selfFieldDto.setValues(map);
        }else if(16 == selfField.getType()){
            String[] split = selfField.getExtraValue().split(",");
            HashMap<String, Object> map = new HashMap<>();
            map.put("length",split[0]);
            map.put("isMinus",split[1]);
            selfFieldDto.setValues(map);
        }
        return selfFieldDto;
    }

    @Override
    public List<SelfField> getAllField(Integer id,String query) {
        return fieldMapper.getFields(id,query);
    }

    @Override
    public List<SelfField> getNameFields(Integer id) {
        return fieldMapper.getNameFields(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addPickList(Integer entityId,Integer fieldId,List<PickList> pickLists) {
        if(null == pickLists || 0 == pickLists.size()){
            throw new MessageException("最少要有一个选项");
        }
        //判断绑定的字段是否为下拉列表或多选框
        SelfField field = fieldMapper.selectByPrimaryKey(pickLists.get(0).getFieldId());
        if(8 != field.getType() && 17 != field.getType()){
            throw new MessageException("列表和多选类型的字段才可以添加选项");
        }
        //删除原有的
        Example example = new Example(PickList.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",entityId);
        criteria.andEqualTo("fieldId",fieldId);
        pickListMapper.deleteByExample(example);

        for (PickList pickList : pickLists) {
            pickList.setEntityId(entityId);
            pickList.setFieldId(fieldId);
            pickList.setCreateBy(1);
            pickList.setCreateTime(System.currentTimeMillis());
            pickList.setUpdateBy(1);
            pickList.setUpdateTime(System.currentTimeMillis());
        }
        pickListMapper.insertList(pickLists);
        return 1;
    }

    @Override
    public List<PickList> getPickList(Integer entityId, Integer fieldId) {
        //查询指定字段所有下拉列表
        Example example = new Example(PickList.class);
        example.setOrderByClause("sort asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",entityId);
        criteria.andEqualTo("fieldId",fieldId);
        return pickListMapper.selectByExample(example);
    }

    @Override
    public Integer updateFieldLayout(LayoutConfigDto layoutConfigDto) {
        LayoutConfig layoutConfig = new LayoutConfig();
        BeanUtils.copyProperties(layoutConfigDto,layoutConfig);
        layoutConfig.setType(1);
        layoutConfig.setSelected(true);
        layoutConfig.setConfig(JSON.toJSONString(layoutConfigDto.getObjectList()));
        layoutConfigMapper.updateByPrimaryKeySelective(layoutConfig);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SelfField> getDefaultLayout(Integer id) {
        LayoutConfig query = new LayoutConfig();
        query.setBelongEntity(id);
        query.setType(1);
        query.setSelected(true);
        LayoutConfig layoutConfig = layoutConfigMapper.selectOne(query);
        if(null == layoutConfig){

            //根据实体id获取字段集合
            List<SelfField> fields = fieldMapper.selectFieldsByEntityId(id);
            //查询该实体是否有引用字段
            List<SelfField> quoteFields = fieldMapper.selectFieldByType(12,id);
            for (SelfField quoteField : quoteFields) {
                List<SelfField> fields1 = fieldMapper.selectFieldsByEntityId(Integer.parseInt(quoteField.getExtraValue()));
                for (SelfField selfField : fields1) {
                    selfField.setName(quoteField.getName()+"."+selfField.getName());
                    selfField.setIsVisible(false);
                }
                fields.addAll(fields1);
            }

            return fields;
        }else{
            String config = layoutConfig.getConfig();
            List<SimpFieldDto> simpFieldDtos = JSON.parseArray(config, SimpFieldDto.class);
            ArrayList<SelfField> fields = new ArrayList<>();
            for (SimpFieldDto simpFieldDto : simpFieldDtos) {
                SelfField field = fieldMapper.selectByPrimaryKey(simpFieldDto.getId());
                field.setIsVisible(simpFieldDto.getIsVisible());
                field.setName(simpFieldDto.getName());
                fields.add(field);
            }
            return fields;
        }
    }

    @Override
    public List<SelfField> getAllShowField(Integer id) {

        //根据实体id获取字段集合
        List<SelfField> fields = fieldMapper.selectFieldsByEntityId(id);
        //查询该实体是否有引用字段
        List<SelfField> quoteFields = fieldMapper.selectFieldByType(12,id);
        for (SelfField quoteField : quoteFields) {
            List<SelfField> fields1 = fieldMapper.selectFieldsByEntityId(Integer.parseInt(quoteField.getExtraValue()));
            for (SelfField selfField : fields1) {
                selfField.setName(quoteField.getName()+"."+selfField.getName());
                selfField.setIsVisible(false);
            }
            fields.addAll(fields1);
        }

        return fields;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveFieldLayout(LayoutConfigDto layoutConfigDto) {
        LayoutConfig lc = new LayoutConfig();
        BeanUtils.copyProperties(layoutConfigDto,lc);
        lc.setType(1);
        lc.setSelected(true);
        lc.setConfig(JSON.toJSONString(layoutConfigDto.getObjectList()));
        if(null == lc.getId()){
            layoutConfigMapper.insert(lc);
        }else{
            layoutConfigMapper.updateByPrimaryKeySelective(lc);
        }
        layoutConfigMapper.updateFieldSelected(layoutConfigDto.getBelongEntity(),lc.getId());
        return lc.getId();
    }

    @Override
    public List<LayoutConfigDto> getFieldLayouts(Integer entityId) {
        LayoutConfig query = new LayoutConfig();
        query.setBelongEntity(entityId);
        query.setType(1);
        List<LayoutConfig> select = layoutConfigMapper.select(query);
        ArrayList<LayoutConfigDto> layoutConfigDtos = new ArrayList<>();
        for (LayoutConfig layoutConfig : select) {
            LayoutConfigDto lcd = getFieldLayoutConfigDto(layoutConfig);
            layoutConfigDtos.add(lcd);
        }
        return layoutConfigDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LayoutConfigDto getFieldLayout(Integer layoutId) {
        LayoutConfig layoutConfig = layoutConfigMapper.selectByPrimaryKey(layoutId);
        return getFieldLayoutConfigDto(layoutConfig);
    }

    @Override
    public List<SelfField> getCustomField(Integer entityId, Integer accountId) {
        //获取要查询的内建实体
        SelfEntity selfEntity = entityMapper.selectByPrimaryKey(entityId);
        return fieldMapper.getCustomField(entityId, accountId, selfEntity.getPname());
    }

    @Override
    public List<SelfField> getTargetField(Integer entityId) {
        SelfField query = new SelfField();
        query.setEntityId(entityId);
        query.setType(2);
        return fieldMapper.select(query);
    }

    //查询是否有重复实体名称
    private boolean checkEntityName(String pname){
        SelfEntity selfEntity = new SelfEntity();
        selfEntity.setPname(pname);
        return entityMapper.selectCount(selfEntity) > 0;
    }

    private LayoutConfigDto getFieldLayoutConfigDto(LayoutConfig layoutConfig) {
        LayoutConfigDto lcd = new LayoutConfigDto();
        BeanUtils.copyProperties(layoutConfig,lcd);
        lcd.setObjectList(JSON.parseArray(layoutConfig.getConfig(), Object.class));
        if(null == layoutConfig.getShare()){
            lcd.setShareDesc("私有");
        }else if("ALL".equals(layoutConfig.getShare())){
            lcd.setShareDesc("全部用户");
        }else{
            int num = layoutConfig.getShare().split(",").length;
            lcd.setShareDesc("指定用户（"+num+")");
        }
        if(layoutConfig.getSelected()){
            lcd.setShareDesc(lcd.getShareDesc()+"[当前]");
        }
        return lcd;
    }

    private String optimizeName(String name){
        return new StringBuilder("`").append(name).append("`").toString();
    }
}
