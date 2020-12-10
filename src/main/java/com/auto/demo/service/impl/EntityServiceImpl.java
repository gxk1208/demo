package com.auto.demo.service.impl;

import com.auto.demo.dto.SelfEntityDto;
import com.auto.demo.entity.SelfEntity;
import com.auto.demo.entity.SelfField;
import com.auto.demo.enums.FieldTypeEnum;
import com.auto.demo.exception.MessageException;
import com.auto.demo.mapper.SelfEntityMapper;
import com.auto.demo.mapper.SelfFieldMapper;
import com.auto.demo.service.EntityService;
import com.auto.demo.utils.WordUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 14:09
 */
@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private SelfEntityMapper selfEntityMapper;

    @Autowired
    private SelfFieldMapper selfFieldMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(SelfEntity entity) {

        List<String> list = new ArrayList<>();
        list.add("1，2");
        list.add("1,3");
        for (String s : list) {
            String s1 = s.toString();
            String[] split = s1.split(",");
            System.out.println(split[0]);
        }

        if (WordUtil.isSpecialChar(entity.getName())){
            throw new MessageException("实体名称不能包含特殊符号标点符号以及空格");
        }
        if(checkEntityName(entity.getName())){
            throw new MessageException("实体名称不能重复");
        }
        //汉字转拼音
        entity.setPname("h_"+WordUtil.getPingYin(entity.getName()).toLowerCase());
        //查询新建实体名称是否重复，有则添加随机数
        while (checkEntityPName(entity.getPname())){
            entity.setPname(entity.getPname()+(int)(Math.random()*100+1));
        }
        //创建数据表
//        if(null == entity.getParentId()||0 == entity.getParentId()){
        StringBuilder sql = new StringBuilder("CREATE TABLE if not exists ");
// project_id int(11), tenant_id int(11),  delete_flag tinyint(1),
        sql.append(optimizeName(entity.getPname())).append("(`id` int(11) NOT NULL AUTO_INCREMENT, `create_by` int(11) NOT NULL,`create_time` bigint(13) NOT NULL,`update_by` int(11) NOT NULL,`update_time` bigint(13) NOT NULL, ");
        if(null != entity.getParentId() && 0<entity.getParentId()){
            //获取绑定的主实体
            SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(entity.getParentId());
            sql.append(optimizeName(selfEntity.getPname())).append(" int(11) , ");
        }else{
            sql.append(" belong_user int(11),  belong_dep int(11), ");
        }
        //创建名称字段
        if(null != (entity.getNameField()) ){
            sql.append(entity.getPname()).append("name").append(" varchar(255) , ");
        }
        sql.append(" PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='").append(entity.getComment()).append("'");
        jdbcTemplate.execute(sql.toString());
        if(null != entity.getParentId() && 0<entity.getParentId()){
            entity.setType(4);
        }else{
            entity.setParentId(0);
            entity.setType(3);
        }
        entity.setNameField(null == entity.getNameField()?"create_time":entity.getPname()+"name");
        //实体表添加记录

        selfEntityMapper.insert(entity);
        //为SELFFIELD表添加id、创建人、创建时间、修改人、修改时间记录
        ArrayList<SelfField> selfFields = new ArrayList<>();
        selfFields.add(getSelfField("id","id",entity.getId(),"主键标识",1));
//        selfFields.add(getSelfField("所属项目ID","project_id",entity.getId(),"所属项目ID",1));
//        selfFields.add(getSelfField("所属租户ID","tenant_id",entity.getId(),"所属租户ID",1));
//        selfFields.add(getSelfField("删除状态","delete_flag",entity.getId(),"0:未删除 1:删除",18));
        selfFields.add(getSelfField("创建人","create_by",entity.getId(),"创建人",1));
        selfFields.add(getSelfField("创建时间","create_time",entity.getId(),"创建时间",5));
        selfFields.add(getSelfField("修改人","update_by",entity.getId(),"修改人",1));
        selfFields.add(getSelfField("修改时间","update_time",entity.getId(),"修改时间",5));
        if(null != entity.getParentId() && 0<entity.getParentId()){
            SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(entity.getParentId());
            SelfField quote = getSelfField(selfEntity.getName(), selfEntity.getPname(), entity.getId(), "引用主记录", 12);
            quote.setExtraValue(selfEntity.getId().toString());
            selfFields.add(quote);
            selfEntity.setType(2);
            selfEntityMapper.updateByPrimaryKeySelective(selfEntity);
        }else{
            selfFields.add(getSelfField("所属用户","belong_user",entity.getId(),"所属用户",1));
            selfFields.add(getSelfField("所属部门","belong_dep",entity.getId(),"所属部门",1));
        }
        if(!"create_time".equals(entity.getNameField())){
            selfFields.add(getSelfField(entity.getName()+"名称",entity.getPname()+"name",entity.getId(),"名称字段",2));
        }
        selfFieldMapper.insertList(selfFields);
/*

        }else{
            //获取绑定的主实体
            SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(entity.getParentId());
            StringBuilder sql = new StringBuilder("CREATE TABLE if not exists ");
            sql.append(optimizeName(entity.getPname())).append("(`id` int(11) NOT NULL AUTO_INCREMENT,`create_by` int(11) NOT NULL,`create_time` bigint(13) NOT NULL,`update_by` int(11) NOT NULL,`update_time` bigint(13) NOT NULL,  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='");
            sql.append(entity.getComment()).append("'");
            jdbcTemplate.execute(sql.toString());
            //实体表添加记录
            entity.setType(4);
            entity.setNameField("create_time");
            selfEntityMapper.insert(entity);
            //为SELFFIELD表添加id、创建人、创建时间、修改人、修改时间记录
            ArrayList<SelfField> selfFields = new ArrayList<>();
            selfFields.add(getSelfField("id","id",entity.getId(),"主键标识",1));
            selfFields.add(getSelfField("创建人","create_by",entity.getId(),"创建人",1));
            selfFields.add(getSelfField("创建时间","create_time",entity.getId(),"创建时间",5));
            selfFields.add(getSelfField("修改人","update_by",entity.getId(),"修改人",1));
            selfFields.add(getSelfField("修改时间","update_time",entity.getId(),"修改时间",5));

            selfFieldMapper.insertList(selfFields);

            selfEntity.setType(2);
            selfEntityMapper.updateByPrimaryKeySelective(selfEntity);
        }
*/

        return 1;
    }

    /**
     * 为新建实体类添加五个固定字段
     * @param name 字段展示名称
     * @param pname 字段内部名称
     * @param entityId 字段所属实体
     * @param comment 字段备注
     * @param type 字段类型
     * @return
     */
    private SelfField getSelfField(String name, String pname,Integer entityId,String comment,Integer type) {
        SelfField selfField = new SelfField();
        selfField.setName(name);
        selfField.setPname(pname);
        selfField.setEntityId(entityId);
        selfField.setComment(comment);
        selfField.setType(type);
        selfField.setDisplayType(FieldTypeEnum.getNameByVal(type));
        selfField.setNullable("名称字段".equals(comment) );
        selfField.setUpdateable("名称字段".equals(comment));
        selfField.setRepeatable("id".equals(name) );
        selfField.setCreateType("名称字段".equals(comment));
        selfField.setIsBuild(false);
        selfField.setIsVisible("create_by".equals(pname) || "create_time".equals(pname) || "名称字段".equals(comment));
        return selfField;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateEntity(SelfEntity entity) {
        //获取原有的entity
        SelfEntity priEntity = selfEntityMapper.selectByPrimaryKey(entity.getId());
        entity.setPname("h_"+WordUtil.getPingYin(entity.getName()));
        //查询修改实体名称是否重复，有则添加随机数
        while (checkEntityPName(entity.getPname())){
            entity.setPname(entity.getPname()+(int)(Math.random()*100+1));
        }
        StringBuilder sql = new StringBuilder("alter table ");
        sql.append(optimizeName(priEntity.getPname())).append(" rename to ").append(optimizeName(entity.getPname()));
        jdbcTemplate.execute(sql.toString());
        selfEntityMapper.updateByPrimaryKeySelective(entity);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteEntity(Integer id) {

        //获取要删除的entity
        SelfEntity priEntity = selfEntityMapper.selectByPrimaryKey(id);
        //判断当前实体生成的表是否有数据
        String querySql = new StringBuilder("select count(id) from ").append(optimizeName(priEntity.getPname())).toString();
        Integer count;
        try {
            count = jdbcTemplate.queryForObject(querySql, Integer.class);
        }catch (NullPointerException e){
            count = 0;
        }
        if(0 < count){
            throw new MessageException("不能删除有数据的实体 数量"+count);
        }
        //判断是否为主实体
        if(2 == priEntity.getType()){
            throw new MessageException("删除主实体时需要先删除明细实体");
        }
        //判断当前实体是否被引用
        List<SelfField> selfFields = selfFieldMapper.selectQuoteFieldByType(12);
        for (SelfField selfField : selfFields) {
            if(id == Integer.parseInt(selfField.getExtraValue())){
                throw new MessageException("该实体已被引用，请先删除引用字段");
            }
        }

        //删除实体对应的表
        StringBuilder sql = new StringBuilder("DROP TABLE if exists ");
        sql.append(optimizeName(priEntity.getPname()));
        jdbcTemplate.execute(sql.toString());
        //删除实体
        selfEntityMapper.deleteByPrimaryKey(id);
        //删除对应的字段
        Example example = new Example(SelfField.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",id);
        selfFieldMapper.deleteByExample(example);
        if(4 == priEntity.getType()){
            SelfEntity selfEntity = new SelfEntity();
            selfEntity.setId(priEntity.getParentId());
            selfEntity.setType(3);
            selfEntityMapper.updateByPrimaryKeySelective(selfEntity);
        }
        return 1;
    }

    @Override
    public List<SelfEntityDto> getAllEntity() {
        List<SelfEntityDto> selfEntities = selfEntityMapper.selectAllEntity();
        for (SelfEntityDto selfEntity : selfEntities) {
            selfEntity.setTypeDesc(selfEntity.getType()==1?"内建":selfEntity.getType()==2?"明细":"");
            if(selfEntity.getType()==2){
                selfEntity.setDetailEntity(selfEntityMapper.getEntityByparentId(selfEntity.getId()));
            }
        }
        return selfEntities;
    }

    @Override
    public SelfEntityDto getEntity(Integer id) {
        SelfEntity se = selfEntityMapper.selectByPrimaryKey(id);
        SelfEntityDto selfEntity = new SelfEntityDto();
        BeanUtils.copyProperties(se,selfEntity);
        if(selfEntity.getType()==2){
            selfEntity.setDetailEntity(selfEntityMapper.getEntityByparentId(selfEntity.getId()));
        }
        return selfEntity;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addDetailEntity(SelfEntity entity) {
        if (WordUtil.isSpecialChar(entity.getName())){
            throw new MessageException("实体名称不能包含特殊符号标点符号以及空格");
        }
        //汉字转拼音
        entity.setPname(WordUtil.getPingYin(entity.getName()).toLowerCase());
        //查询新建实体名称是否重复，有则添加随机数
        while (checkEntityPName(entity.getPname())){
            entity.setPname(entity.getPname()+(int)(Math.random()*100+1));
        }
        selfEntityMapper.insert(entity);
        if(null != entity.getParentId()){
            //父级实体
            SelfEntity selfEntity = selfEntityMapper.selectByPrimaryKey(entity.getParentId());
            if(null != selfEntity){
                StringBuilder sqlSon = new StringBuilder("create table ");
                sqlSon.append(optimizeName(entity.getPname())).append(" select * ").append(" from ");
                sqlSon.append(optimizeName(selfEntity.getPname())).append("where 0");
                jdbcTemplate.execute(sqlSon.toString());
                //查询新增的详情实体的id
                SelfEntity detailEntity = selfEntityMapper.selectOne(entity);
                //向字段表中添加数据
                List<SelfField> fields = selfFieldMapper.selectAll();
                for (SelfField field : fields) {
                    field.setId(null);
                    field.setEntityId(detailEntity.getId());
                }
                selfFieldMapper.insertList(fields);
            }
        }
        return 1;
    }

    @Override
    public List<SelfEntity> getMasterEntity() {
        return selfEntityMapper.getMasterEntity();
    }

    @Override
    public List<SelfEntity> getAttaEntity() {
        Set<SelfEntity> attaEntity = selfEntityMapper.getAttaEntity();
        Iterator<SelfEntity> iterator = attaEntity.iterator();
        HashSet<Integer> masterEntityId = new HashSet<>();
        while (iterator.hasNext()){
            SelfEntity entity = iterator.next();
            if(4 == entity.getType()){
                masterEntityId.add(entity.getParentId());
                iterator.remove();
            }
        }
        for (Integer i : masterEntityId) {
            attaEntity.add(selfEntityMapper.selectByPrimaryKey(i));
        }
        return new ArrayList<>(attaEntity);
    }

    //查询是否有重复实体名称
    private boolean checkEntityPName(String pname){
        SelfEntity selfEntity = new SelfEntity();
        selfEntity.setPname(pname);
        return selfEntityMapper.selectCount(selfEntity) > 0;
    }
    private boolean checkEntityName(String name){
        SelfEntity selfEntity = new SelfEntity();
        selfEntity.setName(name);
        return selfEntityMapper.selectCount(selfEntity) > 0;
    }

    //为表名和字段名添加 ` `
    private String optimizeName(String name) {
        return new StringBuilder("`").append(name).append("`").toString();
    }

}
/*
* //查询该表字段名称集合
        StringBuilder field = new StringBuilder(" ( ");
        List<String> select = selfFieldMapper.getNameByEntityId(id);
        for (int i = 0; i < select.size(); i++) {
            field.append(i==list.size()-1 ? list.get(i)+"," : list.get(i)+" ) ");
        }

        StringBuilder val = new StringBuilder(" ( ");

        for (int i = 0; i < list.size(); i++) {
            val.append(i==list.size()-1 ? list.get(i)+"," : list.get(i)+" ) ");
        }*/
//                //获取需要拷贝父实体的字段
//                SelfField selfField = new SelfField();
//                selfField.setCreateType(true);
//                selfField.setEntityId(entity.getParentId());
//                List<SelfField> select = selfFieldMapper.select(selfField);
//                StringBuilder fieldsql = new StringBuilder();
//                for (SelfField field : select) {
//                    fieldsql.append(field.getPname()).append(", ");
//                }
//                fieldsql.delete(fieldsql.length()-2,fieldsql.length()-1);
