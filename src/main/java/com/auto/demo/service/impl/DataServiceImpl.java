package com.auto.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.dto.*;
import com.auto.demo.entity.*;
import com.auto.demo.exception.MessageException;
import com.auto.demo.mapper.*;
import com.auto.demo.param.BackfillParam;
import com.auto.demo.param.QueryAttachParam;
import com.auto.demo.service.DataService;
import com.auto.demo.service.FieldService;
import com.auto.demo.utils.EasyExcelUtil;
import com.auto.demo.utils.WordUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/26 13:41
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private FormLayoutMapper layoutMapper;

    @Autowired
    private SelfFieldMapper selfFieldMapper;

    @Autowired
    private SelfEntityMapper selfEntityMapper;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private PickListMapper pickListMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LayoutConfigMapper layoutConfigMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private BackfillConfigMapper backfillConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addData(Integer id, Map<String, Object> map) {
        //查询要插入数据的表名称
        SelfEntity selfEntity;
        //查询要插入数据的表名称
        selfEntity = selfEntityMapper.selectByPrimaryKey(id);
        if(1 == selfEntity.getType()){
            SelfEntity interEntity = selfEntityMapper.getInEntity(selfEntity.getName(),selfEntity.getPname(),99);
            if(null == interEntity){
                SelfEntity inEntity = new SelfEntity();
                BeanUtils.copyProperties(selfEntity,inEntity);
                while (checkEntityName(inEntity.getPname())){
                    inEntity.setPname(inEntity.getPname()+(int)(Math.random()*100+1));
                }
                StringBuilder inSql = new StringBuilder();
                inSql.append(" CREATE TABLE ").append(optimizeName(inEntity.getPname())).append(" LIKE ").append(optimizeName(selfEntity.getPname()));
                jdbcTemplate.execute(inSql.toString());
                inEntity.setId(null);
                inEntity.setType(3);

                selfEntityMapper.insert(inEntity);
                LayoutConfig lc = new LayoutConfig();
                lc.setBelongEntity(inEntity.getId());
                lc.setConfigName(inEntity.getName()+"表单模板");
                lc.setSelected(true);
                lc.setType(3);
                lc.setConfig("[]");

                layoutConfigMapper.insert(lc);
                List<SelfField> selfFields = selfFieldMapper.selectFieldsByEntityId(id);
                for (SelfField selfField : selfFields) {
                    selfField.setId(null);
                    selfField.setEntityId(inEntity.getId());
                }
                selfFieldMapper.insertList(selfFields);
                selfEntity = selfEntityMapper.selectOne(inEntity);
            }else{
                selfEntity  = interEntity;
            }
        }
        String pname = selfEntity.getPname();
        //新增实体数据时，需要查询当前实体有无不能存在重复值的列，如果有，判断当前要保存的值是否重复
        List<SelfField> repeatFields = selfFieldMapper.selectRepeatField(id);
        //新增实体数据时，需要查询当前实体有无不能为null的列，如果有，判断当前要保存的值是否为null
        List<SelfField> nullFields = selfFieldMapper.selectNullField(id);
        //新增实体数据时，需要查询当前实体有无类型为图片或文件的列，如果有，判断上传的数量
        List<SelfField> attaFields = selfFieldMapper.selectFieldByType(10,id);
        List<SelfField> fileFields = selfFieldMapper.selectFieldByType(11,id);
        attaFields.addAll(fileFields);
        //新增实体数据时，需要查询当前实体有无类型为整数或小数的并且不允许为负数的列，如果有，判断数据有无负值
        List<SelfField> numFields = selfFieldMapper.selectNumField(id);
        //TODO 是不是可以不要这个循环判断
        for (SelfField nullField : nullFields) {
            if(!(map.keySet().contains(nullField.getPname()))){
                throw new MessageException(nullField.getName()+" 不能为空");
            }
        }
        StringBuilder field = new StringBuilder();
        StringBuilder val = new StringBuilder();
        ArrayList<Attachment> attas = new ArrayList<>();
        for( Map.Entry<String, Object> entry : map.entrySet()){
            for (SelfField repeatField : repeatFields) {
                if(WordUtil.getPingYin(entry.getKey()).equals(repeatField.getPname()) && checkRepeatData(pname,WordUtil.getPingYin(entry.getKey()),entry.getValue())){
                    throw new MessageException(repeatField.getName()+" 已存在该数据，该列不可添加重复值");
                }
            }
            for (SelfField nullField : nullFields) {
                if(WordUtil.getPingYin(entry.getKey()).equals(nullField.getPname()) && null == entry.getValue()){
                    throw new MessageException(nullField.getName()+" 不能为空");
                }
            }

            for (SelfField numField : numFields) {
                if(WordUtil.getPingYin(entry.getKey()).equals(numField.getPname()) && 0 > (Integer) entry.getValue()){
                    throw new MessageException(numField.getName()+" 不能为负数");
                }
            }

            for (SelfField attaField : attaFields) {
                int num = Integer.parseInt(attaField.getExtraValue());
                Object object = entry.getKey().equals(attaField.getPname()) ? entry.getValue() : null;
                if(null != object){
                    String[] split = object.toString().split(",");
                    //附件处理
                    for (String s : split) {
                        Attachment a = new Attachment();
                        a.setEntityId(id);
                        a.setFieldId(attaField.getId());
                        a.setPath(s);
                        a.setType(s.substring(s.lastIndexOf(".") + 1));
                        a.setCreateBy(1);
                        a.setCreateTime(System.currentTimeMillis());
                        attas.add(a);
                    }
                    if(split.length>num){
                        if(10 == attaField.getType()){
                            throw new MessageException("上传图片数量不能大于"+num);
                        }else{
                            throw new MessageException("上传文件数量不能大于"+num);
                        }
                    }
                }
            }
            field.append(WordUtil.getPingYin(entry.getKey())).append(", ");
            val.append("'").append(entry.getValue()).append("', ");
        }
        //新增实体数据时，需要查询当前实体有无类型为自增编号的字段，如果有，判断归零类型，然后根据最新一条记录赋值。
        //TODO　Ｒｅｄｉｓ
        List<SelfField> autoNoFields = selfFieldMapper.selectAutoNoField(id);
        for (SelfField autoNoField : autoNoFields) {
            String[] split = autoNoField.getExtraValue().split(",");
            String time = null;
            //获取当天日期
//            if("{YYYYMMDD}-{0000}".equals(split[0])){6
//                time  = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
//            }else {
            time  = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()).toString();
            //           }
            //获取该字段最新一条记录的值
            StringBuilder newData = new StringBuilder("SELECT ");
            newData.append(optimizeName(autoNoField.getPname())).append(" from ").append(optimizeName(pname)).append("ORDER BY create_time desc");
            try{
                String s = jdbcTemplate.queryForObject(newData.toString(), String.class);
                field.append(autoNoField.getPname()).append(", ");
                val.append("'").append(getAutoNo(s, Integer.parseInt(split[1]), time)).append("', ");
            }catch (EmptyResultDataAccessException e){
                field.append(autoNoField.getPname()).append(", ");
                val.append("'").append(time).append("-0001").append("', ");
            }
        }
        //创建人，创建时间
        field.append("create_by").append(", ").append("create_time").append(", ");
        val.append("'").append("1").append("', ").append("'").append(System.currentTimeMillis()).append("', ");
        //修改人，修改时间
        field.append("update_by").append(", ").append("update_time").append(", ");
        val.append("'").append("1").append("', ").append("'").append(System.currentTimeMillis()).append("', ");
        //所属用户，所属部门
        //field.append("belong_user").append(", ").append("belong_dep").append(", ");
        //val.append("'").append("1").append("', ").append("'").append("1").append("', ");
        String subfield = field.toString().substring(0, field.length() - 2);
        String subval = val.toString().substring(0, val.length() - 2);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(optimizeName(pname)).append(" ( ").append(subfield).append(" ) VALUES ( ").append(subval).append(" );");
        jdbcTemplate.update(sql.toString());
        StringBuilder getIdSql = new StringBuilder(" SELECT id FROM ");
        getIdSql.append(optimizeName(selfEntity.getPname())).append("where create_by = 1 ORDER BY create_time desc LIMIT 1");
        Integer insert = jdbcTemplate.queryForObject(getIdSql.toString(), Integer.class);
        for (Attachment atta : attas) {
            atta.setDataId(insert);
        }
        if(0 < attas.size()){
            attachmentMapper.insertList(attas);
        }
        return insert;
    }

    @Override
    public Map<String, Object> getAllData(PageParam pageParam, Integer id, Integer fieldId, String flag) {
        //查询当前实体信息
        SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(id);
        //创建返回结果集
        Map<String, Object> object = new HashMap<>();
        //查询该实体是否进行字段显示布局
        LayoutConfig query = new LayoutConfig();
        query.setBelongEntity(id);
        query.setType(1);
        query.setSelected(true);
        LayoutConfig layoutConfig = layoutConfigMapper.selectOne(query);
        if(null == layoutConfig){
            StringBuilder defaultSql = new StringBuilder("SELECT id, create_by, create_time");
            if(!"create_time".equals(selfEntity.getNameField())){
                defaultSql.append(", ").append(optimizeName(selfEntity.getNameField()));
            }
            defaultSql.append(" FROM ").append(optimizeName(selfEntity.getPname()));
            if(0 != fieldId && null != flag){
                SelfField sortField = selfFieldMapper.selectByPrimaryKey(fieldId);
                defaultSql.append(" order by ").append(sortField.getPname()).append(" ").append(flag);
            }else{
                defaultSql.append(" order by ").append("create_time desc ");
            }
            //TODO 分页
            defaultSql.append(" limit ").append(pageParam.getPage()-1).append(",").append(pageParam.getPageSize());
            List<Map<String, Object>> defaultMap = jdbcTemplate.queryForList(defaultSql.toString());
            List<SelfField> defaultFields = new ArrayList<>();
            SelfField qfield = new SelfField();
            qfield.setPname("id");
            qfield.setEntityId(id);
            SelfField idField = selfFieldMapper.selectOne(qfield);
            defaultFields.add(idField);
            Example example = new Example(SelfField.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isVisible",true);
            criteria.andEqualTo("entityId",id);
            List<SelfField> dfields = selfFieldMapper.selectByExample(example);
            defaultFields.addAll(dfields);
            object.put("headlist",defaultFields);
            PagedList<Map<String, Object>> parse = PagedList.parse(defaultMap);
            object.put("bodylist",parse);
            return object;
        }else {
            //判断该实体是否包含引用字段
            List<SelfField> quoteFields = selfFieldMapper.selectFieldByType(12, id);
            //该实体有字段数据配置，取出所有可见的字段
            String config = layoutConfig.getConfig();
            List<SimpFieldDto> simpFieldDtos = JSON.parseArray(config, SimpFieldDto.class);
            ArrayList<SelfField> fields = new ArrayList<>();
            //添加id字段
            SelfField qfield = new SelfField();
            qfield.setPname("id");
            qfield.setEntityId(id);
            SelfField idField = selfFieldMapper.selectOne(qfield);
            idField.setPname(optimizeName(selfEntity.getPname())+".`id`");
            fields.add(idField);
            Set<SelfEntity> quoteEntitys = new HashSet<>();
            for (SimpFieldDto simpFieldDto : simpFieldDtos) {
                if(simpFieldDto.getIsVisible()){
                    SelfField field = selfFieldMapper.selectByPrimaryKey(simpFieldDto.getId());
                    field.setName(simpFieldDto.getName());
                    SelfEntity selfEntity1 = selfEntityMapper.selectByPrimaryKey(field.getEntityId());
                    field.setPname(optimizeName(selfEntity1.getPname())+"."+optimizeName(field.getPname()));
                    fields.add(field);
                    if(0<quoteFields.size() && !id.equals(selfEntity1.getId())){
                        quoteEntitys.add(selfEntity1);
                    }
                }
            }

            StringBuilder sql = new StringBuilder("SELECT ");
            for (SelfField field : fields) {
                String alias = getAlias(field);
                sql.append(field.getPname()).append(" as ").append(alias).append(", ");
            }
            sql.deleteCharAt(sql.length()-2);
            sql.append(" FROM ").append(optimizeName(selfEntity.getPname()));
            if(0 < quoteEntitys.size()){
                for (SelfEntity quoteEntity : quoteEntitys) {
                    for (SelfField quoteField : quoteFields) {
                        if(Integer.parseInt(quoteField.getExtraValue())==(quoteEntity.getId())){
                            sql.append( "inner join ").append(optimizeName(quoteEntity.getPname())).append(" on ").append(optimizeName(selfEntity.getPname())).append(".").append(optimizeName(quoteField.getPname())).append(" = ").append(optimizeName(quoteEntity.getPname())).append(".`id`");
                        }
                    }
                }
            }
            if(0 != fieldId && null != flag){
                for (SelfField field : fields) {
                    if(fieldId.equals(field.getId()) ){
                        sql.append(" order by ").append(field.getPname()).append(flag);
                    }
                }
            }else{
                sql.append(" order by ").append(optimizeName(selfEntity.getPname())).append(".`update_time` desc ");
            }
            sql.append(" limit ").append((pageParam.getPage()-1)*pageParam.getPageSize()).append(",").append(pageParam.getPageSize());
            List<Map<String, Object>> valList = jdbcTemplate.queryForList(sql.toString());
            formatAllData(valList,fields);
            object.put("headlist",fields);
            PagedList<Map<String, Object>> parse = PagedList.parse(valList);
            object.put("bodylist",parse);
            return object;
        }
    }

    private String getAlias(SelfField field) {
        String alias = field.getPname().replace("`","");
        alias = alias.replace(".","_");
        return alias;
    }

    @Override
    public List<Map<String, Object>> getOneData(Integer id, Integer dataId) {
        //根据实体id查找表名称
        SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(id);
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(optimizeName(selfEntity.getPname())).append(" where id = ").append(dataId);
        List<Map<String, Object>> valList = jdbcTemplate.queryForList(sql.toString());
        this.formatData(id, valList);
        return valList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateData(Integer id, Map<String, Object> map) {
        //获取要修改数据的实体
        SelfEntity priEntity = selfEntityMapper.selectByPrimaryKey(id);
        String pname = priEntity.getPname();
        StringBuilder val = new StringBuilder();
        Integer idVal = null;
        //修改实体数据时，需要查询当前实体有无不能存在重复值的列，如果有，判断当前要保存的值是否重复
        List<SelfField> repeatFields = selfFieldMapper.selectRepeatField(id);
        //修改实体数据时，需要查询当前实体有无不能为null的列，如果有，判断当前要保存的值是否为null
        List<SelfField> nullFields = selfFieldMapper.selectNullField(id);
        //修改实体数据时，需要查询当前实体有无不能修改的列，如果有，判断当前要保存的值是否改变
        List<SelfField> updateFields = selfFieldMapper.selectUpdateField(id);
        //修改实体数据时，需要查询当前实体有无类型为图片或文件的列，如果有，判断上传的数量
        List<SelfField> attaFields = selfFieldMapper.selectFieldByType(10,id);
        List<SelfField> fileFields = selfFieldMapper.selectFieldByType(11,id);
        attaFields.addAll(fileFields);
        //新增实体数据时，需要查询当前实体有无类型为整数或小数的并且不允许为负数的列，如果有，判断数据有无负值
        List<SelfField> numFields = selfFieldMapper.selectNumField(id);
        List<Attachment> attas = new ArrayList<>();
        for( Map.Entry<String, Object> entry : map.entrySet()){

            for (SelfField repeatField : repeatFields) {
                if(WordUtil.getPingYin(entry.getKey()).equals(repeatField.getPname()) && checkRepeatData(pname,WordUtil.getPingYin(entry.getKey()),entry.getValue())){
                    throw new MessageException(repeatField.getName()+" 已存在该数据，该列不可添加重复值");
                }
            }
            for (SelfField nullField : nullFields) {
                if(WordUtil.getPingYin(entry.getKey()).equals(nullField.getPname()) && (null == entry.getValue() || "".equals(entry.getValue().toString()))){
                    throw new MessageException(nullField.getName()+" 不能为空");
                }
            }
            for (SelfField updateField : updateFields) {
                if(WordUtil.getPingYin(entry.getKey()).equals(updateField.getPname())&&checkUpdateData(pname,WordUtil.getPingYin(entry.getKey()),entry.getValue(),(Integer) map.get("id"))){
                    throw new MessageException(updateField.getName()+" 不能修改");
                }
            }
            for (SelfField numField : numFields) {
                if(WordUtil.getPingYin(entry.getKey()).equals(numField.getPname()) && 0 > (Integer) entry.getValue()){
                    throw new MessageException(numField.getName()+" 不能为负数");
                }
            }
            for (SelfField attaField : attaFields) {
                int num = Integer.parseInt(attaField.getExtraValue());
                Object object = entry.getKey().equals(attaField.getPname()) ? entry.getValue() : null;
                if(null != object){
                    String[] split = object.toString().split(",");
                    for (String s : split) {
                        Attachment a = new Attachment();
                        a.setEntityId(id);
                        a.setFieldId(attaField.getId());
                        a.setPath(s);
                        a.setType(s.substring(s.lastIndexOf(".") + 1));
                        a.setCreateBy(1);
                        a.setCreateTime(System.currentTimeMillis());
                        attas.add(a);
                    }
                    if(split.length>num){
                        if(10 == attaField.getType()){
                            throw new MessageException("上传图片数量不能大于"+num);
                        }else{
                            throw new MessageException("上传文件数量不能大于"+num);
                        }
                    }
                }
            }
            if(!"id".equals(WordUtil.getPingYin(entry.getKey()))){
                val.append(WordUtil.getPingYin(entry.getKey())).append("='").append(entry.getValue()).append("', ");
            }else{
                idVal = (Integer)entry.getValue();
            }
        }
        val.append("update_by").append("='").append("1").append("', ");
        val.append("update_time").append("='").append(System.currentTimeMillis()).append("', ");
        val.delete(val.length()-2,val.length()-1);
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(optimizeName(priEntity.getPname())).append(" SET ").append(val).append(" where id =  ").append(idVal);
        jdbcTemplate.update(sql.toString());
        for (Attachment atta : attas) {
            atta.setDataId(idVal);
        }
        Attachment query = new Attachment();
        query.setEntityId(id);
        query.setDataId(idVal);
        attachmentMapper.delete(query);
        attachmentMapper.insertList(attas);
        return 1;
    }

    @Override
    public Integer deleteData(Integer id, Integer dataId) {
        //获取要修改数据的实体
        SelfEntity priEntity = selfEntityMapper.selectByPrimaryKey(id);
        Attachment attachment = new Attachment();
        attachment.setDataId(dataId);
        attachment.setEntityId(id);
        attachmentMapper.delete(attachment);
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(optimizeName(priEntity.getPname())).append(" WHERE id = ").append(dataId);
        return jdbcTemplate.update(sql.toString());
    }

    @Override
    public List<Map<String, Object>> getNameFieldValue(Integer id, String query) {
        //根据id查找对应实体
        SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(id);
        StringBuilder sql = new StringBuilder(" SELECT id,");
        sql.append(optimizeName(selfEntity.getNameField())).append(" FROM ").append(optimizeName(selfEntity.getPname()));
        sql.append(" where ").append(optimizeName(selfEntity.getNameField())).append(" like '%").append(query).append("'");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        return list;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addLayout(List<FormLayout> layouts) {
        Example example = new Example(FormLayout.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId", layouts.get(0).getEntityId());
        layoutMapper.deleteByExample(example);
        for (FormLayout layout : layouts) {
            if( 0 != layout.getFieldId()){
                SelfField selfField = new SelfField();
                selfField.setId(layout.getFieldId());
                selfField.setIsBuild(true);
                selfFieldMapper.updateByPrimaryKeySelective(selfField);
            }
        }
        layoutMapper.insertList(layouts);
        return 1;
    }

    @Override
    public List<SelfField> getLayout(Integer entityId) {
        //获取布局列表
        //List<FormLayout> layouts = layoutMapper.getLayoutByEntityId(entityId);
        FormLayout formLayout = new FormLayout();
        formLayout.setEntityId(entityId);
        List<FormLayout> layouts = layoutMapper.select(formLayout);
        List<SelfField> fields = new ArrayList<>();
        for (FormLayout layout : layouts) {
            if(0 != layout.getFieldId()){
                SelfField field = selfFieldMapper.selectByPrimaryKey(layout.getFieldId());
                fields.add(field);
            }else{
                SelfField field = new SelfField();
                field.setName(layout.getName());
                fields.add(field);
            }
        }
        //获取未布局的字段
        SelfField selfField = new SelfField();
        selfField.setIsBuild(false);
        selfField.setEntityId(entityId);
        List<SelfField> noBuildFields = selfFieldMapper.select(selfField);
        fields.addAll(noBuildFields);
        return fields;
    }

    @Override
    public Map<String, Object> getForm(Integer entityId, Integer dataId, boolean flag) {
        List<LayoutDto> lds = layoutMapper.getForm(entityId);
        if(0 == lds.size()){
            throw new MessageException("抱歉! 表单布局尚未配置，请配置后使用");
        }
        Map<String, Object> map = new HashMap<>();
        //获取要查询的实体
        SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(entityId);
        StringBuilder sql = new StringBuilder("SELECT ");
        for (LayoutDto ld : lds) {
            if(null != dataId && null != ld.getType()){
                sql.append(optimizeName(ld.getPname())).append(", ");
            }
            if(null !=ld.getType()){
                if(8 == ld.getType() || 17 == ld.getType()){
                    //获取字段对应的下拉选项列表
                    Example example = new Example(PickList.class);
                    example.setOrderByClause("sort asc");
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("entityId",ld.getEntityId());
                    criteria.andEqualTo("fieldId",ld.getId());
                    criteria.andEqualTo("isActive",false);
                    List<PickList> pickLists = pickListMapper.selectByExample(example);
                    ld.setValue(pickLists);
                }else if(12 == ld.getType()){
                    SelfEntity quoteEntity = selfEntityMapper.selectByPrimaryKey(ld.getExtraValue());
                    StringBuilder quoteData = new StringBuilder("SELECT id, ");
                    quoteData.append(optimizeName(quoteEntity.getNameField())).append(" FROM ").append(optimizeName(quoteEntity.getPname()));
                    List<Map<String, Object>> maps = jdbcTemplate.queryForList(quoteData.toString());
                    ld.setValue(maps);
                }
            }
        }
        map.put("fields",lds);
        if(null != dataId){
            if(flag){
                sql.append(" create_by, create_time, update_time, ");
            }
            sql.deleteCharAt(sql.length()-2);
            sql.append(" FROM ").append(optimizeName(selfEntity.getPname())).append(" where id = ").append(dataId);
            List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString());
            this.formatData(entityId,dataList);
            map.put("data",dataList);
        }

        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addMenuLayout(LayoutConfigDto layoutConfigDto) {
        LayoutConfig layoutConfig = new LayoutConfig();
        BeanUtils.copyProperties(layoutConfigDto,layoutConfig);
        layoutConfig.setSelected(true);
        layoutConfig.setBelongEntity(0);
        layoutConfig.setConfig(JSON.toJSONString(layoutConfigDto.getObjectList()));
        layoutConfig.setType(2);
        if(null == layoutConfig.getId()){
            layoutConfigMapper.insert(layoutConfig);
        }else{
            layoutConfigMapper.updateByPrimaryKey(layoutConfig);
        }
        layoutConfigMapper.updateMenuSelected(layoutConfig.getId());
        return layoutConfig.getId();
    }

    @Override
    public List<LayoutConfigDto> getMenuLayouts() {
        LayoutConfig layoutConfig = new LayoutConfig();
        layoutConfig.setType(2);
        layoutConfig.setBelongEntity(0);
        List<LayoutConfig> select = layoutConfigMapper.select(layoutConfig);
        ArrayList<LayoutConfigDto> list = new ArrayList<>();
        for (LayoutConfig lc : select) {
            list.add(getLayoutConfigDto(lc));
        }
        return list;
    }

    @Override
    public LayoutConfigDto getMenuLayout(Integer id) {
        LayoutConfig layoutConfig = layoutConfigMapper.selectByPrimaryKey(id);
        return getLayoutConfigDto(layoutConfig);
    }

    @Override
    public LayoutConfigDto getDefaultMenuLayout() {
        LayoutConfig query = new LayoutConfig();
        query.setType(2);
        query.setBelongEntity(0);
        query.setSelected(true);
        LayoutConfig layoutConfig = layoutConfigMapper.selectOne(query);
        if(null == layoutConfig){
            return null;
        }else{
            return getLayoutConfigDto(layoutConfig);
        }
    }

    @Override
    public List<AttachmentDto> getAttach(QueryAttachParam param) {
        List<Attachment> attachs = attachmentMapper.getAttach(param);
        ArrayList<AttachmentDto> attaDtos = new ArrayList<>();
        for (Attachment attachment : attachs) {
            AttachmentDto attaDto = new AttachmentDto();
            BeanUtils.copyProperties(attachment,attaDto);
            attaDto.setEntityName(selfEntityMapper.selectByPrimaryKey(attachment.getEntityId()).getName());
            long l = System.currentTimeMillis();
            l = l-attachment.getCreateTime();
            if(l/1000<60){
                attaDto.setTime(l/1000+"秒前");
            }else if(l/1000/60<60){
                attaDto.setTime(l/1000/60+"分钟前");
            }else if(l/1000/(60*60)<60){
                attaDto.setTime(l/1000/(60*60)+"小时前");
            }else if(l/1000/(60*60*24)<24){
                attaDto.setTime(l/1000/(60*60*24)+"天前");
            }else if(l/1000/(60*60*24*30)<12){
                attaDto.setTime(l/1000/(60*60*24*30)+"个月前");
            }else{
                attaDto.setTime(l/1000/(60*60*24*30*12)+"年前");
            }
            attaDtos.add(attaDto);
        }
        return attaDtos;
    }

    @Override
    public Integer dispatchData(Integer entityId, Integer dataId, Integer userId) {
        //获取实体
        SelfEntity entity = selfEntityMapper.selectByPrimaryKey(entityId);
        StringBuilder sql = new StringBuilder("update ");
        sql.append(optimizeName(entity.getPname())).append(" set ").append(" belong_user = ").append(userId).append(" where id = ").append(dataId);
        jdbcTemplate.update(sql.toString());
        return 1;
    }


    @Override
    public void exportExcel(Integer entityId, String idList, HttpServletResponse response) throws IOException {
        String[] ids = idList.split(",");
        //查询要获取数据的实体
        SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(entityId);
        //创建返回结果集
        List<Map<String, Object>> valList = null;
        List<SelfField> fields = new ArrayList<>();
        //查询该实体是否进行字段显示布局
        LayoutConfig query = new LayoutConfig();
        query.setBelongEntity(entityId);
        query.setType(1);
        query.setSelected(true);
        LayoutConfig layoutConfig = layoutConfigMapper.selectOne(query);
        StringBuilder sql = new StringBuilder();
        if(null == layoutConfig) {
            sql.append("SELECT create_by, create_time");
            if (!"create_time".equals(selfEntity.getNameField())) {
                sql.append(", ").append(optimizeName(selfEntity.getNameField()));
            }
            sql.append(" FROM ").append(optimizeName(selfEntity.getPname()));
            Example example = new Example(SelfField.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isVisible",true);
            criteria.andEqualTo("entityId",entityId);
            fields = selfFieldMapper.selectByExample(example);
        }else{
            //判断该实体是否包含引用字段
            List<SelfField> quoteFields = selfFieldMapper.selectFieldByType(12, entityId);
            //该实体有字段数据配置，取出所有可见的字段
            String config = layoutConfig.getConfig();
            List<SimpFieldDto> simpFieldDtos = JSON.parseArray(config, SimpFieldDto.class);
            Set<SelfEntity> quoteEntitys = new HashSet<>();
            for (SimpFieldDto simpFieldDto : simpFieldDtos) {
                if(simpFieldDto.getIsVisible()){
                    SelfField field = selfFieldMapper.selectByPrimaryKey(simpFieldDto.getId());
                    field.setName(simpFieldDto.getName());
                    SelfEntity selfEntity1 = selfEntityMapper.selectByPrimaryKey(field.getEntityId());
                    field.setPname(optimizeName(selfEntity1.getPname())+"."+optimizeName(field.getPname()));
                    fields.add(field);
                    if(0<quoteFields.size() && !entityId.equals(selfEntity1.getId())){
                        quoteEntitys.add(selfEntity1);
                    }
                }
            }

            sql.append("SELECT ");
            for (SelfField field : fields) {
                String alias = getAlias(field);
                sql.append(field.getPname()).append(" as ").append(alias).append(", ");
            }
            sql.deleteCharAt(sql.length()-2);
            sql.append(" FROM ").append(optimizeName(selfEntity.getPname()));
            if(0 < quoteEntitys.size()){
                for (SelfEntity quoteEntity : quoteEntitys) {
                    for (SelfField quoteField : quoteFields) {
                        if(Integer.parseInt(quoteField.getExtraValue())==(quoteEntity.getId())){
                            sql.append( "inner join ").append(optimizeName(quoteEntity.getPname())).append(" on ").append(optimizeName(selfEntity.getPname())).append(".").append(optimizeName(quoteField.getPname())).append(" = ").append(optimizeName(quoteEntity.getPname())).append(".`id`");
                        }
                    }
                }
            }
        }
        sql.append(" where id in ( ");
        for (String integer : ids) {
            sql.append(Integer.parseInt(integer)).append(", ");
        }
        sql.deleteCharAt(sql.length()-2);
        sql.append(" )");
        valList = jdbcTemplate.queryForList(sql.toString());
        formatAllData(valList, fields);
        String filename = Integer.parseInt(ids[0]) == 0 ? "模板" : selfEntity.getName()+System.currentTimeMillis();
        EasyExcelUtil.downloadFailedUsingJson(response,fields,valList,new String(filename.getBytes(), "UTF-8"));
    }




    @Override
    public Integer saveBackfill(BackfillConfig backfillConfig) {
        if(null == backfillConfig.getId() || 0 == backfillConfig.getId()){
            backfillConfigMapper.insert(backfillConfig);
        }else{
            backfillConfigMapper.updateByPrimaryKeySelective(backfillConfig);
        }
        return 1;
    }

    @Override
    public List<BackfillConfigDto> getBackfills(Integer entityId, Integer fieldId) {
        Example example = new Example(BackfillConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",entityId);
        criteria.andEqualTo("fieldId",fieldId);
        List<BackfillConfig> backfillConfigs = backfillConfigMapper.selectByExample(example);
        ArrayList<BackfillConfigDto> bcds = new ArrayList<>();
        for (BackfillConfig backfillConfig : backfillConfigs) {
            BackfillConfigDto bcd = new BackfillConfigDto();
            BeanUtils.copyProperties(backfillConfig,bcd);
            bcd.setSourceName(selfFieldMapper.selectByPrimaryKey(bcd.getSourceField()).getName());
            bcd.setTargetName(selfFieldMapper.selectByPrimaryKey(bcd.getTargetField()).getName());
            StringBuilder str = new StringBuilder();
            String[] config = backfillConfig.getConfig().split(",");
            if("1".equals(config[0])){
                str.append("新建时，");
            }
            if("1".equals(config[1])){
                str.append("更新时，");
            }
            if("1".equals(config[2])){
                str.append("强制回填，");
            }
            str.deleteCharAt(str.length()-1);
            bcd.setConfigDesc(str.toString());
            bcds.add(bcd);
        }
        return bcds;
    }

    @Override
    public BackfillConfig getBackfill(Integer id) {
        return backfillConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteBackfill(Integer id) {
        return backfillConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getResourceFieldValue(BackfillParam bp) {
        //查询当前引用字段的表单回填配置信息列表
        BackfillConfig query = new BackfillConfig();
        query.setEntityId(bp.getEntityId());
        query.setFieldId(bp.getFieldId());
        List<BackfillConfig> bfConfigs = backfillConfigMapper.select(query);
        if(0 == bfConfigs.size()){
            return null;
        }
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        for (BackfillConfig bfConfig : bfConfigs) {
            String[] split = bfConfig.getConfig().split(",");
            if(null == bp.getDataId() || 0 == bp.getDataId()){
                //添加数据时
                if("1".equals(split[0])){
                    maps.add(getResultMap(bp, bfConfig, split[2]));
                }else{
                    return null;
                }
            }else{
                //修改数据时
                if("1".equals(split[1])){
                    maps.add(getResultMap(bp, bfConfig, split[2]));
                }else{
                    return null;
                }
            }
        }
        return maps;
    }

    @Override
    public List<String> getHeader(String path) throws IOException {
        //创建XSSFWorkbook对象
        XSSFWorkbook wb  = new XSSFWorkbook(new FileInputStream(path));
        //创建XSSFSheet对象
        XSSFSheet sheet = wb.getSheetAt(0);
        //创建XSSFRow对象
        XSSFRow row = sheet.getRow(0);
        //创建XSSFCell对象
        XSSFCell cell=row.getCell(0);
        //获取表头数量
        short lastCellNum = row.getLastCellNum();
        ArrayList<String> headers = new ArrayList<>();
        for (int i = 0; i < lastCellNum; i++) {
            String s = cell.getSheet().getRow(0).getCell(i)
                    .getRichStringCellValue().toString();
            headers.add(s);
        }
        return headers;
    }

    @Override
    public Integer importExcel(String path, Integer entityId, String filedIds) throws Exception {
        List<String> header = getHeader(path);
        //获取日期类型的字段
        List<SelfField> dateFields = selfFieldMapper.selectDateField(entityId);
        List<Integer> dataIndex = new ArrayList<>();
        for (SelfField dateField : dateFields) {
            for (int i = 0; i < header.size(); i++) {
                if(dateField.getName().equals(header.get(i))){
                    dataIndex.add(i);
                }
            }
        }
        //获取引用类型的字段
        List<SelfField> quoteFields = selfFieldMapper.selectFieldByType(12,entityId);
        List<Integer> quoteIndex = new ArrayList<>();
        HashMap<Integer, Object> map = new HashMap<>();
        for (SelfField quoteField : quoteFields) {
            for (int i = 0; i < header.size(); i++) {
                if(quoteField.getName().equals(header.get(i))){
                    quoteIndex.add(i);
                    map.put(i,quoteField);
                }
            }
        }
        //创建XSSFWorkbook对象
        XSSFWorkbook wb  = new XSSFWorkbook(new FileInputStream(path));
        //创建XSSFSheet对象
        XSSFSheet sheet = wb.getSheetAt(0);
        //创建XSSFRow对象
        XSSFRow row = sheet.getRow(0);
        int lastRowNum = sheet.getLastRowNum();
        if(0 == lastRowNum){
            throw new MessageException("excel表无数据");
        }
        //创建XSSFCell对象
        XSSFCell cell=row.getCell(0);
        //获取表头数量
        short lastCellNum = row.getLastCellNum();
        //拼接插入数据sql
        //获取实体对象
        SelfEntity entity = selfEntityMapper.selectByPrimaryKey(entityId);
        List<SelfField> fields = new ArrayList<>();
        //拼接插入字段sql
        StringBuilder fieldSql = new StringBuilder();
        List<String> strings = Arrays.asList(filedIds.split(","));
        for (String s : strings) {
            SelfField field = selfFieldMapper.selectByPrimaryKey(s);
            fields.add(field);
            fieldSql.append(optimizeName(field.getPname())).append(", ");
        }
        fieldSql.append("create_by").append(", ").append("create_time").append(", ");
        fieldSql.append("update_by").append(", ").append("update_time").append(", ");
        //fieldSql.append("belong_user").append(", ").append("belong_dep").append(", ");
        fieldSql.deleteCharAt(fieldSql.length()-2);
        for (int i = 1; i <=lastRowNum; i++) {
            StringBuilder dataSql = new StringBuilder();
            for (int j = 0; j < lastCellNum; j++) {
                // Object o = cell.getSheet().getRow(i).getCell(j).getRichStringCellValue();
                Object cellValue = getCellValue(cell.getSheet().getRow(i).getCell(j));
                if(dataIndex.contains(j)){
                    if(4 == cellValue.toString().length()){
                        cellValue = cellValue+"-01-01";
                    }else if(7 == cellValue.toString().length() || 6 == cellValue.toString().length()){
                        cellValue = cellValue+"-01";
                    }
                }else if(quoteIndex.contains(j)){
                    SelfField qf = (SelfField)map.get(j);
                    SelfEntity quoteEntity = selfEntityMapper.selectByPrimaryKey(qf.getExtraValue());
                    StringBuilder quoteIdSql = new StringBuilder("SELECT id FROM ");
                    quoteIdSql.append(optimizeName(quoteEntity.getPname())).append(" where ").append(quoteEntity.getNameField()).append(" = '").append(cellValue).append("'").append(" limit 1");
                    try {
                        cellValue = jdbcTemplate.queryForObject(quoteIdSql.toString(), Object.class);
                    }catch (Exception e){
                        cellValue = null;
                    }
                }
                if (null == cellValue){
                    dataSql.append(cellValue).append(", ");
                }else{
                    dataSql.append("'").append(cellValue).append("', ");
                }
            }
            //创建人，创建时间
            dataSql.append("'").append("99").append("', ").append("'").append(System.currentTimeMillis()).append("', ");
            //修改人，修改时间
            dataSql.append("'").append("99").append("', ").append("'").append(System.currentTimeMillis()).append("', ");
            //所属用户，所属部门
            //dataSql.append("'").append("99").append("', ").append("'").append("99").append("', ");
            dataSql.deleteCharAt(dataSql.length()-2);
            StringBuilder sql = new StringBuilder("insert into ");
            sql.append(optimizeName(entity.getPname())).append("( ").append(fieldSql).append(" ) values ( ").append(dataSql).append(" )");
            jdbcTemplate.update(sql.toString());
        }
        return 1;
    }

    /**
     * 在获取所有数据和导出数据时格式化数据
     * @param valList 数据集合
     * @param fields 展示的字段集合
     */
    private void formatAllData(List<Map<String, Object>> valList, List<SelfField> fields) {
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //查询是否有日期类型字段
        List<SelfField> dateFields = new ArrayList<>();
        //查询是否有货币类型字段
        List<SelfField> moneyFields = new ArrayList<>();
        //查询是否有引用类型字段
        List<SelfField> allQuoteFields = new ArrayList<>();
        for (SelfField field : fields) {
            if(3==field.getType() || 4==field.getType()){
                dateFields.add(field);
            }else if(16==field.getType()){
                moneyFields.add(field);
            }else if(12==field.getType()){
                allQuoteFields.add(field);
            }
        }

        for (SelfField dateField : dateFields) {
            String layout = dateField.getExtraValue();
            String alias = getAlias(dateField);
            for (Map<String, Object> map : valList) {
                if(null != map.get(alias)){
                    map.put(alias,formatDate(layout,map.get(alias)));
                }
            }
        }

        for (SelfField moneyField : moneyFields) {
            String[] split = moneyField.getExtraValue().split(",");
            int num = Integer.parseInt(split[0]);
            String alias = getAlias(moneyField);
            for (Map<String, Object> map : valList) {
                if(null != map.get(alias)){
                    BigDecimal bigDecimal = new BigDecimal((Double) map.get(alias));
                    map.put(alias,bigDecimal.setScale(num,BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
        }

        for (SelfField quoteField : allQuoteFields) {
            //获取引用实体
            SelfEntity quoteEntity = selfEntityMapper.selectByPrimaryKey(quoteField.getExtraValue());
            String alias = getAlias(quoteField);
            //引用字段 存储的是引用实体id的数据，展示的是引用实体名称字段的数据
            for (Map<String, Object> map : valList) {
                if(null != map.get(alias)) {
                    StringBuilder quoteSql = new StringBuilder(" SELECT ");
                    quoteSql.append(optimizeName(quoteEntity.getNameField())).append(" FROM ").append(optimizeName(quoteEntity.getPname()));
                    quoteSql.append(" WHERE id = ").append(map.get(alias));
                    Object nameFieldVal = jdbcTemplate.queryForObject(quoteSql.toString(), Object.class);
                    map.put(alias, nameFieldVal);
                }
            }
        }
        for (Map<String, Object> map : valList) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(entry.getKey().contains("create_time")||entry.getKey().contains("update_time")){
                    String format = sdf.format(new Date((long) entry.getValue()));
                    map.put(entry.getKey(),format);
                }
            }
        }
    }


    /**
     * 获取表单回填配置产生的数据
     * @param bp 查询参数
     * @param bfConfig 配置信息
     * @param str 是否强制
     * @return
     */
    private Map<String, Object> getResultMap(BackfillParam bp, BackfillConfig bfConfig, String str) {
        Map<String, Object> map = new HashMap<>();
        //目标字段id
        map.put("id",bfConfig.getTargetField());
        //查询当前实体信息
        SelfEntity entity = selfEntityMapper.selectByPrimaryKey(bp.getEntityId());
        //查询源字段信息
        SelfField field = selfFieldMapper.selectByPrimaryKey(bfConfig.getSourceField());
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(optimizeName(field.getPname())).append(" from ").append(optimizeName(entity.getPname())).append(" where id = ").append(bp.getQuoteId());
        Object object = jdbcTemplate.queryForObject(sql.toString(), Object.class);
        //为目标字段赋予的值
        map.put("data",object);
        //是否强制回填
        map.put("constraint", "1".equals(str));
        return map;
    }

    /**
     * 获取表单布局返回对象
     * @param lc
     */
    private LayoutConfigDto getLayoutConfigDto(LayoutConfig lc) {
        LayoutConfigDto lcd = new LayoutConfigDto();
        BeanUtils.copyProperties(lc,lcd);
        lcd.setObjectList(JSON.parseArray(lc.getConfig(),Object.class));
        if(null == lcd.getShare()||"0".equals(lcd.getShare()) ){
            lcd.setShareDesc("私有");
        }else if("ALL".equals(lcd.getShare())){
            lcd.setShareDesc("全部用户");
        }else{
            int num = lcd.getShare().split(",").length;
            lcd.setShareDesc("指定用户（"+num+")");
        }
        if(lcd.getSelected()){
            lcd.setShareDesc(lcd.getShareDesc()+"[当前]");
        }
        return lcd;
    }

    /**
     * 格式化字段类型为日期、日期时间、货币、引用的字段数据
     * @param id 实体id
     * @param valList 返回数据结果集
     */
    private void formatData(Integer id, List<Map<String, Object>> valList) {
        //查询是否有日期类型字段
        List<SelfField> dateFields = selfFieldMapper.selectDateField(id);
        for (SelfField dateField : dateFields) {
            String layout = dateField.getExtraValue();
            for (Map<String, Object> map : valList) {
                if(null != map.get(dateField.getPname())){
                    map.put(dateField.getPname(),formatDate(layout,map.get(dateField.getPname())));
                }
            }
        }
        //查询是否有货币类型字段
        List<SelfField> moneyFields = selfFieldMapper.selectFieldByType(16,id);
        for (SelfField moneyField : moneyFields) {
            String[] split = moneyField.getExtraValue().split(",");
            int num = Integer.parseInt(split[0]);
            for (Map<String, Object> map : valList) {
                if(null != map.get(moneyField.getPname())){
                    BigDecimal bigDecimal = new BigDecimal((Double) map.get(moneyField.getPname()));
                    map.put(moneyField.getPname(),bigDecimal.setScale(num,BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
        }

        //查询是否有引用类型字段
        List<SelfField> quoteFields = selfFieldMapper.selectFieldByType(12,id);
        for (SelfField quoteField : quoteFields) {
            //获取引用实体
            SelfEntity quoteEntity = selfEntityMapper.selectByPrimaryKey(quoteField.getExtraValue());
            //引用字段 存储的是引用实体id的数据，展示的是引用实体名称字段的数据
            for (Map<String, Object> map : valList) {
                if(null != map.get(quoteField.getPname())){
                    StringBuilder quoteSql = new StringBuilder(" SELECT ");
                    quoteSql.append(optimizeName(quoteEntity.getNameField())).append(" FROM ").append(optimizeName(quoteEntity.getPname()));
                    quoteSql.append(" WHERE id = ").append(map.get(quoteField.getPname()));
                    Object nameFieldVal = jdbcTemplate.queryForObject(quoteSql.toString(), Object.class);
                    map.put(quoteField.getPname(),nameFieldVal);
                }
            }
        }
    }

    //为表名和字段名添加 ` `
    private String optimizeName(String name) {
        return new StringBuilder("`").append(name).append("`").toString();
    }

    //检查字段数据是否有重复值
    private boolean checkRepeatData(String entityName,String fieldName,Object val){
        StringBuilder sql = new StringBuilder("SELECT COUNT(id) FROM ");
        sql.append(entityName).append(" where ").append(fieldName).append(" = '").append(val).append("';");
        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        return count >= 1;
    }

    //检查字段数据是否被修改
    private boolean checkUpdateData(String entityName,String fieldName,Object val,Integer id){
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(optimizeName(fieldName)).append(" FROM ").append(optimizeName(entityName)).append(" where id = ").append(id);
        String data =  jdbcTemplate.queryForObject(sql.toString(), String.class);
        return !val.toString().equals(data);
    }
    //获取自增编号日期
    private String getAutoNo(String s,Integer type,String time){
        String date = null;
        String day = s.substring(0, 8);
        String month = s.substring(0, 6);
        String year = s.substring(0, 4);
        String bottom = s.substring(s.length()-4, s.length());
        if(1 == type){
            date = time+"-"+String.format("%0" + 4 + "d",Integer.parseInt(bottom)+1);
        }else if(2 == type){
            date = day.equals(time.substring(0, 8))?day+"-"+String.format("%0" + 4 + "d",Integer.parseInt(bottom)+1):time+"-0001";
        }else if(3 == type){
            date = month.equals(time.substring(0, 6)) ? month+"-"+String.format("%0" + 4 + "d",Integer.parseInt(bottom)+1): time+"-0001";
        }else{
            date = year.equals(time.substring(0, 4))? year+"-"+String.format("%0" + 4 + "d",Integer.parseInt(bottom)+1) :time+"-0001";
        }
        return date;
    }
    //转换展示时的日期格式
    private String formatDate(String layout,Object value){
        if(null == value){
            return null;
        }
        String s = this.convertDate(layout);
        return new SimpleDateFormat(s).format(value);
//        String val = null;
//        if("YYYY".equals(layout)){
//            val = new SimpleDateFormat("yyyy").format(value);
//        }else if("YYYY-MM".equals(layout)) {
//            val  = new SimpleDateFormat("yyyyMM").format(value);
//        }else if("YYYY-MM-DD".equals(layout)) {
//            val  = new SimpleDateFormat("yyyyMMdd").format(value);
//        }else if("YYYY-MM-DD HH:II".equals(layout)) {
//            val  = new SimpleDateFormat("yyyyMMdd HH:mm").format(value);
//        }else if("YYYY-MM-DD HH:II:SS".equals(layout)) {
//            val  = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(value);
//        }
//        return val;
    }

    private String convertDate(String group){
        String s = group.replace("Y", "y");
        // YYYY-MM-DD HH-II-SS > yyyy-MM-dd HH-mm:ss
        s = s.replace("D", "d");
        s = s.replace("I", "m");
        s = s.replace("S", "s");
        return  s;
    }

    private Object getCellValue(Cell cell){
        Object cellValue  = null ;
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: // 数字
                //short s = cell.getCellStyle().getDataFormat();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    // 验证short值
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                    cell.setCellType(CellType.STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: // 空值
                cellValue = null;
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    //查询是否有重复实体名称
    private boolean checkEntityName(String pname){
        SelfEntity selfEntity = new SelfEntity();
        selfEntity.setPname(pname);
        return selfEntityMapper.selectCount(selfEntity) > 0;
    }
}
/*
* //查询可以展示的字段列表（按sort排序）
        //获取实体id集合，查找它们的字段
        ArrayList<Integer> entityIds = new ArrayList<>();
        entityIds.add(id);
        //判断该实体是否为明细实体，查找主实体id
        SelfEntity masterEntity = null;
        Integer masterEntityId = null;
        if(4 == selfEntity.getType()){
            masterEntity = selfEntityMapper.selectByPrimaryKey(selfEntity.getParentId());
            masterEntityId = masterEntity.getId();
            entityIds.add(masterEntityId);
        }
        //判断该实体是否包含引用字段
        List<SelfField> quoteFields = selfFieldMapper.selectFieldByType(12, id);
        //删除quoteFields中明细实体的引用字段
        quoteFields.removeIf(next -> selfEntity.getParentId() == Integer.parseInt(next.getExtraValue()));
        for (SelfField quoteField : quoteFields) {
            entityIds.add(Integer.parseInt(quoteField.getExtraValue()));
        }
        //查找字段集合
        //sign 0 查找所有字段 1查找显示的字段
        Integer sign = 1;
        List<SelfField> fieldList = selfFieldMapper.getFieldsByEntityIds(entityIds,sign);
        //根据实体id查找表名称
        StringBuilder sql = new StringBuilder("SELECT ");
        for (SelfField selfField : fieldList) {
            //修改主实体的字段的展示名称为“主实体名称.主实体字段名称”
            if(null !=masterEntity && masterEntityId .equals(selfField.getEntityId()) ){
                selfField.setName(masterEntity.getName()+"."+selfField.getName());
            }
            //修改引用实体的字段的展示名称为“引用字段名称.引用实体字段名称”
            for (SelfField field : quoteFields) {
                    Integer quoteEntityId = selfField.getEntityId();
                    if(quoteEntityId.equals(Integer.parseInt(field.getExtraValue()))){
                        selfField.setName(field.getName()+"."+selfField.getName());
                }
            }
            //拼接查询数据的字段的sql语句
            SelfEntity entity = selfEntityMapper.selectByPrimaryKey(selfField.getEntityId());
            sql.append(optimizeName(entity.getPname())).append(".").append(optimizeName(selfField.getPname())).append(", ");
            System.out.println(selfField.getPname());
        }
        sql.deleteCharAt(sql.length()-2);
        sql.append(" FROM ").append(optimizeName(selfEntity.getPname()));
        if(null != masterEntity){
            sql.append(" inner join ").append(optimizeName(masterEntity.getPname())).append(" on ").append(optimizeName(masterEntity.getPname())).append(".`id` = ").append(optimizeName(selfEntity.getPname())).append(".`id`");
        }
        for (SelfField quoteField : quoteFields) {
                SelfEntity quoteEntity = selfEntityMapper.selectByPrimaryKey(quoteField.getExtraValue());
                sql.append(" inner join ").append(optimizeName(quoteEntity.getPname())).append(" on ").append(optimizeName(selfEntity.getPname())).append(".`id`  = ").append(optimizeName(quoteEntity.getPname())).append(".`id`");
        }
        if(0 != fieldId && null != flag){
            SelfField sortField = selfFieldMapper.selectByPrimaryKey(fieldId);
            SelfEntity sortEntity = selfEntityMapper.selectByPrimaryKey(sortField.getEntityId());
            sql.append(" order by ").append(sortEntity.getPname()).append(".").append(sortField.getPname()).append(" ").append(flag);
        }else{
            sql.append(" order by ").append(optimizeName(selfEntity.getPname())).append(".update_time desc ");
        }
        sql.append(" limit ").append(pageParam.getPage()).append(",").append(pageParam.getPageSize());
        List<Map<String, Object>> valList = jdbcTemplate.queryForList(sql.toString());
        //查询是否有日期类型字段
        List<SelfField> dateFields = selfFieldMapper.selectDateField(id);
        for (SelfField dateField : dateFields) {
            String layout = dateField.getExtraValue();
            for (Map<String, Object> map : valList) {
                if(null != map.get(dateField.getPname())){
                    map.put(dateField.getPname(),formatDate(layout,map.get(dateField.getPname())));
                }
            }
        }
        //查询是否有货币类型字段
        List<SelfField> moneyFields = selfFieldMapper.selectFieldByType(16,id);
        for (SelfField moneyField : moneyFields) {
            String[] split = moneyField.getExtraValue().split(",");
            int num = Integer.parseInt(split[0]);
            for (Map<String, Object> map : valList) {
                if(null != map.get(moneyField.getPname())){
                    BigDecimal bigDecimal = new BigDecimal((Double) map.get(moneyField.getPname()));
                    map.put(moneyField.getPname(),bigDecimal.setScale(num,BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
        }


        object.put("headlist",fieldList);
        PagedList<Map<String, Object>> parse = PagedList.parse(valList);
        object.put("bodylist",parse);
        return object;*/
