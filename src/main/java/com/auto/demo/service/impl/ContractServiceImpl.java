/*
package com.auto.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.hlink.ibms.OaClient;
import com.hlink.ibms.common.JsonResult;
import com.hlink.ibms.common.PageParam;
import com.hlink.ibms.common.PagedList;
import com.hlink.ibms.exception.MessageException;
import com.hlink.ibms.oa.entity.SelfField;
import com.hlink.ibms.project.constants.ContractPriceTypeEnum;
import com.hlink.ibms.project.constants.ContractStatusEnum;
import com.hlink.ibms.project.dao.*;
import com.hlink.ibms.project.dto.*;
import com.hlink.ibms.project.entity.*;
import com.hlink.ibms.project.param.ChargeDetailParam;
import com.hlink.ibms.project.param.ContractSearchParam;
import com.hlink.ibms.project.service.ContractService;
import com.hlink.ibms.util.OrderParamUtil;
import com.hlink.ibms.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/19 14:30
 *//*

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private FormTemplateConfigMapper ftConfigMapper;

    @Autowired
    private ChargeClauseMapper chargeClauseMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private SubregionMapper subregionMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private OaClient oaClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addContract(ContractDto contractDto){
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDto,contract);
        contract.setIsCancel(0);
        contract.setDeleteFlag(0);
        contract.setCreateBy(accountId);
        contract.setCreateTime(System.currentTimeMillis());
        contract.setUpdateBy(accountId);
        contract.setUpdateTime(System.currentTimeMillis());
        contract.setTenantId(tenantId);
        if(null == contract.getContractNo()){
            if(null == contract.getTemplateId()){
                throw new MessageException("未选择合同模版时，请输入合同编号");
            }else{
                String s = this.generateContractNo(contract.getTemplateId(), tenantId);
                contract.setContractNo(s);
            }
        }
        contractMapper.insert(contract);
        //添加的费用条款与合同关联
        List<ChargeClauseDto> charges = contractDto.getCharges();
        ArrayList<ChargeClause> ccs = new ArrayList<>();
        for (ChargeClauseDto charge : charges) {
            ChargeClause chargeClause = new ChargeClause();
            BeanUtils.copyProperties(charge,chargeClause);
            chargeClause.setClause(JSON.toJSONString(charge.getClauses()));
            chargeClause.setIncreaseClause(JSON.toJSONString(charge.getIncreaseClauses()));
            chargeClause.setPreferenceClause(JSON.toJSONString(charge.getPreferenceClauses()));
            chargeClause.setOtherClause(JSON.toJSONString(charge.getOtherClauses()));
            chargeClause.setContractId(contract.getId());
            chargeClause.setDeleteFlag(false);
            chargeClause.setCreateBy(accountId);
            chargeClause.setCreateTime(System.currentTimeMillis());
            chargeClause.setUpdateBy(accountId);
            chargeClause.setUpdateTime(System.currentTimeMillis());
            ccs.add(chargeClause);
        }
        chargeClauseMapper.insertList(ccs);
        ContractCustomDto contractCustomDto = new ContractCustomDto();
        BeanUtils.copyProperties(contract,contractCustomDto);
        contractCustomDto.setCustomValue(contractDto.getCustomValue());
        */
/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date signTime = contractCustomDto.getSignTime();
        Date parse = sdf.parse(signTime.toString());*//*

        String s = JSON.toJSONString(contractCustomDto);
        JsonResult<Integer> insert = oaClient.addData(5, s, tenantId, accountId);
        if(200 != insert.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        return insert.getData();
    }

    */
/**
     * 生成合同编号
     * @param id 合同模板id
     * @param tenantId 租户标识
     *//*

    private String generateContractNo(Integer id, Integer tenantId) {
        SimpleDateFormat sdf;
        FormTemplateConfig ftc = ftConfigMapper.selectByPrimaryKey(id);
        if(1 == ftc.getNoTime()){
            sdf = new SimpleDateFormat("yyyy");
        }else{
            sdf = new SimpleDateFormat("yyyyMM");
        }
        String time = sdf.format(new Date());
        //获取可用的作废合同编号
        if(1 == ftc.getRecycleNo()){
            List<String> cancelNos = contractMapper.getCancelNo(tenantId,id);
            for (String cancelNo : cancelNos) {
                Integer count = contractMapper.checkCancelNo(cancelNo,tenantId);
                if(0 == count){
                    //查找到可用作废合同编号，把标记sign变为false，不需要再生成合同编号
                    return (cancelNo);
                }
            }
        }
        //没有找到可用作废合同编号，需要重新生成合同编号
        RedisAtomicLong atomicInteger = new RedisAtomicLong(id+"号模板", redisTemplate.getConnectionFactory());
        long increment = atomicInteger.incrementAndGet();
        return (ftc.getContractNo()+"-"+time+"-"+String.format("%0" + 4 + "d",increment));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateContract(ContractDto contractDto) {
        if(1!=contractDto.getStatus()){
            throw new MessageException("只有未审批的合同才可以修改");
        }
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDto,contract);
        contract.setCreateBy(accountId);
        contract.setCreateTime(System.currentTimeMillis());
        contract.setUpdateBy(accountId);
        contract.setUpdateTime(System.currentTimeMillis());
        contract.setTenantId(tenantId);
        contractMapper.updateByPrimaryKeySelective(contract);

        //编辑费用条款 先物理删除之前的
        ChargeClause query = new ChargeClause();
        query.setContractId(contract.getId());
        query.setDeleteFlag(false);
        chargeClauseMapper.delete(query);
        List<ChargeClauseDto> charges = contractDto.getCharges();
        ArrayList<ChargeClause> ccs = new ArrayList<>();
        for (ChargeClauseDto charge : charges) {
            ChargeClause chargeClause = new ChargeClause();
            BeanUtils.copyProperties(charge,chargeClause);
            chargeClause.setClause(JSON.toJSONString(charge.getClauses()));
            chargeClause.setIncreaseClause(JSON.toJSONString(charge.getIncreaseClauses()));
            chargeClause.setPreferenceClause(JSON.toJSONString(charge.getPreferenceClauses()));
            chargeClause.setOtherClause(JSON.toJSONString(charge.getOtherClauses()));
            chargeClause.setContractId(contract.getId());
            chargeClause.setDeleteFlag(false);
            chargeClause.setCreateBy(accountId);
            chargeClause.setCreateTime(System.currentTimeMillis());
            chargeClause.setUpdateBy(accountId);
            chargeClause.setUpdateTime(System.currentTimeMillis());
            ccs.add(chargeClause);
        }
        chargeClauseMapper.insertList(ccs);
        ContractCustomDto contractCustomDto = new ContractCustomDto();
        BeanUtils.copyProperties(contract,contractCustomDto);
        contractCustomDto.setCustomValue(contractDto.getCustomValue());
        String s = JSON.toJSONString(contractCustomDto);
        JsonResult<Integer> insert = oaClient.updateData(5, s, tenantId, accountId);
        if(200 != insert.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        return insert.getData();
    }

    @Override
    public PageContractDto getPageContract(PageParam page, ContractSearchParam param) throws ParseException {
        PageContractDto pageContractDto = new PageContractDto();
        String orderBy = OrderParamUtil.builder().append(page.getOrder(), page.getSort()).append("id", 1).build();
        PageHelper.startPage(page.getPage(), page.getPageSize(), orderBy);
        List<Contract> contracts = contractMapper.getAll(param);
        List<ContractListDto> list = new ArrayList<>();
        for (Contract contract : contracts) {
            //TODO gxk 返回值处理 14
            ContractListDto cld = new ContractListDto();
            cld.setContractName(contract.getName());
            cld.setRentNumber(contract.getRentNumber()+(1 == contract.getRentType() ? "㎡" : "个"));
            cld.setStatus(ContractStatusEnum.getDescByVal(contract.getStatus()));
            cld.setStartDay(contract.getStartTime());
            cld.setEndDay(contract.getEndTime());
            //获取合同时长
            long startDateTime = contract.getStartTime().getTime();
            long endDateTime = contract.getEndTime().getTime();
            cld.setContractTime((int) ((endDateTime - startDateTime) / (1000 * 3600 * 24))+"天");
            //获取租赁条款单价、物业条款单价
            getUnitPrice(contract, cld);
            //TODO gxk 总计租金算法

            //获取项目名称、分区名称、楼栋名称
            ProjectBuildingNameDto pbnd = buildingMapper.selectRegionName(contract.getBuildingId());
            cld.setProjectName(pbnd.getProjectName());
            cld.setSubRegionName(pbnd.getSubregionName());
            cld.setBuildingName(pbnd.getBuildingName());
            //拼接楼层-房号
            StringBuilder roomNo = new StringBuilder();
            String[] split = contract.getHouseIds().split(",");
            for (String s : split) {
                FloorHouseNameDto fhnd = houseMapper.getFloorRoomName(Integer.parseInt(s));
                roomNo.append(fhnd.getFloorName()).append("-").append(fhnd.getRoomNo()).append(",");
            }
            roomNo.deleteCharAt(roomNo.length()-1);
            cld.setRoomNo(roomNo.toString());

            //TODO gxk 租客、跟进人

            list.add(cld);
        }
        //到期监控图
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH)-1;
        int year = cal.get(Calendar.YEAR);
        List<String> months = new ArrayList<>();
        Map<String,Integer> number = new HashMap<>();
        for(int i = 0; i<36; i++){
            ///String time = month<10 ? year+"-0"+month : year+"-"+month;
            String time = year+"-"+month;
            months.add(time);
            number.put(time,0);
            month++;
            if(12 == month){
                month = 1;
                year++;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = sdf.parse(months.get(0)+"-00");
        Date endTime = sdf.parse(months.get(months.size()-1)+"-31");
        List<Date> endTimes = contractMapper.getContractByEndTime(startTime,endTime);
        SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM");
        for (Date time : endTimes) {
            String s = endSdf.format(time);
            s = s.replace("-0","-");
            if(null != number.get(s)){
                number.put(s,number.get(s)+1);
            }
        }
        pageContractDto.setTime(months);
        pageContractDto.setNumber(number);
        PagedList<ContractListDto> pagedList = new PagedList<>();
        pagedList.setPageSize(page.getPageSize());
        pagedList.setPageNum(page.getPage());
        pagedList.setData(list);
        pagedList.setTotal(list.size());
        pageContractDto.setPagedList(pagedList);
        return pageContractDto;
    }

    */
/**
     * 获取合同的租赁条款单价、物业条款单价
     * @param contract 合同信息
     * @param cld 返回对象信息
     *//*

    private void getUnitPrice(Contract contract, ContractListDto cld) {
        ChargeClause query = new ChargeClause();
        query.setContractId(contract.getId());
        query.setDeleteFlag(false);
        List<ChargeClause> select = chargeClauseMapper.select(query);
        if(0 == select.size()){
            cld.setRentPrice("-");
            cld.setPropertyPrice("-");
        }else if(1 == select.size()){
            ChargeClause chargeClause = select.get(0);
            List<Clause> clauses = JSON.parseArray(chargeClause.getClause(),Clause.class);
            Clause clause = clauses.get(0);
            String unitPrice = clause.getContractPrice() + ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice());
            if(1 == chargeClause.getClauseType()){
                cld.setRentPrice(unitPrice);
                cld.setPropertyPrice("-");
            }else{
                cld.setRentPrice("-");
                cld.setPropertyPrice(unitPrice);
            }
        }else{
            boolean flag1 = false;
            boolean flag2 = false;
            for (ChargeClause chargeClause : select) {
                Integer type = chargeClause.getClauseType();
                if((flag1 && 1 == type)||(flag2 && 2 == type)){
                    break;
                }
                List<Clause> clauses = JSON.parseArray(chargeClause.getClause(),Clause.class);
                Clause clause = clauses.get(0);
                String unitPrice = clause.getContractPrice() + ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice());
                if(1 == chargeClause.getClauseType()){
                    cld.setRentPrice(unitPrice);
                    flag1 = true;
                }else{
                    cld.setPropertyPrice(unitPrice);
                    flag2 = true;
                }
            }
        }
    }

    @Override
    public ContractDto getContract(Integer id) {
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        ContractDto contractDto = new ContractDto();
        Contract contract = contractMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(contract,contractDto);
        Example example = new Example(ChargeClause.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("contractId",id);
        criteria.andEqualTo("deleteFlag",false);
        List<ChargeClause> chargeClauses = chargeClauseMapper.selectByExample(example);
        List<ChargeClauseDto> ccds = new ArrayList<>();
        for (ChargeClause chargeClause : chargeClauses) {
            ChargeClauseDto ccd = new ChargeClauseDto();
            BeanUtils.copyProperties(chargeClause,ccd);
            ccd.setClauses(JSON.parseArray(chargeClause.getClause(),Clause.class));
            ccd.setIncreaseClauses(JSON.parseArray(chargeClause.getIncreaseClause(), IncreaseClause.class));
            ccd.setPreferenceClauses(JSON.parseArray(chargeClause.getPreferenceClause(),PreferenceClause.class));
            ccd.setOtherClauses(JSON.parseArray(chargeClause.getOtherClause(),Clause.class));
            ccds.add(ccd);
        }
        contractDto.setCharges(ccds);
        JsonResult<Map<String, Object>> updateData = oaClient.getUpdateData(5, tenantId,accountId, id);
        if(200 != updateData.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        String s = JSON.toJSONString(updateData.getData());
        contractDto.setCustomValue(s);
        contractDto.setStatusDesc(ContractStatusEnum.getDescByVal(contractDto.getStatus()));
        return contractDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delContract(Integer id) {
        Contract c = contractMapper.selectByPrimaryKey(id);
        if(1!=c.getStatus()){
            throw new MessageException("只有未审批的合同才可以删除");
        }
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        Contract contract = new Contract();
        contract.setId(id);
        contract.setDeleteFlag(1);
        Example example = new Example(ChargeClause.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("contractId",id);
        criteria.andEqualTo("deleteFlag",false);
        List<ChargeClause> chargeClauses = chargeClauseMapper.selectByExample(example);
        for (ChargeClause chargeClause : chargeClauses) {
            chargeClause.setDeleteFlag(true);
            chargeClauseMapper.updateByPrimaryKeySelective(chargeClause);
        }
        contractMapper.updateByPrimaryKeySelective(contract);
        String s = JSON.toJSONString(contract);
        JsonResult<Integer> data = oaClient.updateData(5, s, tenantId, accountId);
        if(200 != data.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        return data.getData();
    }

    @Override
    public Integer cancelContract(Integer id) {
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        Contract contract = new Contract();
        contract.setId(id);
        contract.setStatus(7);
        contract.setIsCancel(1);
        contractMapper.updateByPrimaryKeySelective(contract);
        String s = JSON.toJSONString(contract);
        JsonResult<Integer> data = oaClient.updateData(5, s, tenantId, accountId);
        if(200 != data.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        return 1;
    }

    @Override
    public List<ChargeDetailDto> generateDetail(ChargeDetailParam cdp) throws ParseException {
        List<ChargeDetailDto> list = new ArrayList<>();
        //查询要生成明细的费用条款信息
        //保证金明细
        list.addAll(this.getMarginDetail(cdp));
        //租金明细（因租期划分方式、计费类型、递增条款或优惠条款发生的差异将按时间分割存放在ChargeDetailDto.chargeDetails中）
        List<Clause> clauses = cdp.getClauses();
        if(null == clauses || 0 == clauses.size()){
            throw new MessageException("请添加条款");
        }
        //flag == 1代表生成明细的费用条款是主体条款，受优惠条款和递增条款影响
        List<ChargeDetailDto> rent = getClauseDetails(cdp, clauses,1);
        //优惠条款处理
        this.discount(cdp,rent);
        list.addAll(rent);
        //其他条款明细
        List<Clause> otherClauses = cdp.getOtherClauses();
        //flag == 2代表生成明细的费用条款是其他条款，不受优惠条款和递增条款影响
        if(null != otherClauses && 0 < otherClauses.size()){
            List<ChargeDetailDto> otherClausesDetails = getClauseDetails(cdp, otherClauses,2);
            list.addAll(otherClausesDetails);
        }
        return list;
    }

    */
/**
     * 获取条款信息的明细
     * @param cdp 生成明细信息的入参
     * @param clauses 费用条款集合
     * @return
     *//*

    private List<ChargeDetailDto> getClauseDetails(ChargeDetailParam cdp, List<Clause> clauses, Integer flag) {
        List<IncreaseClause> ics = cdp.getIncreaseClauses();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<ChargeDetailDto> rent = new ArrayList<>();
        for (Clause clause : clauses) {
            //按实际天数计费
            Date time = clause.getCStartTime();
            Date priTime = null;
            if(1 == clause.getCostType()){
                //按起始日划分租期
                if(1 == clause.getLeaseTermDivision()){
                    while (0 > time.compareTo(clause.getCEndTime())){
                        ChargeDetailDto cdd = new ChargeDetailDto();
                        cdd.setType(1 == cdp.getClauseType()?"租金":"物业费");
                        cdd.setFinalPrice(clause.getContractPrice()+ ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
                        //获取租期的开始时间
                        if(0 == time .compareTo(clause.getCStartTime())){
                            priTime = time;
                        }else{
                            priTime = time;
                            Calendar c = Calendar.getInstance();
                            c.setTime(priTime);
                            c.add(Calendar.DATE,1);
                            priTime = c.getTime();
                        }
                        //计算租期的结束时间
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(priTime);
                        cal.add(Calendar.MONTH,clause.getPayCycle());
                        cal.add(Calendar.DATE,-1);
                        time = cal.getTime();
                        //付款日，根据租期开始时间、推迟/提前、天数、日期的形式获取付款时间；
                        cdd.setPayDay(this.getPayDate(priTime,clause.getPayType(),clause.getPayDate(),clause.getDateType()));
                        //对最后不满付费周期的时间段的处理
                        if(0<=time.compareTo(clause.getCEndTime())){
                            cdd.setSection(sdf.format(priTime)+"-"+sdf.format(clause.getCEndTime()));
                            cdd.setReceivable(this.getReceiveCharge(priTime,clause.getCEndTime(),cdp.getRentNum(),clause));
                            if(1 == flag){
                                this.increase(cdp,cdd,priTime,clause.getCEndTime(),clause);
                            }
                        }else{
                            cdd.setSection(sdf.format(priTime)+"-"+sdf.format(time));
                            cdd.setReceivable(this.getReceiveCharge(priTime,time,cdp.getRentNum(),clause));
                            if(1 == flag){
                                this.increase(cdp,cdd,priTime,time,clause);
                            }
                        }
                        cdd.setCarryForward(new BigDecimal("0.00"));
                        cdd.setNeedCollect(cdd.getReceivable().subtract(cdd.getCarryForward()));
                        this.discount1(cdp,cdd,clause);
                        rent.add(cdd);
                    }
                    //按自然月划分(首月非整自然月算一个月)
                }else{
                    //按自然月划分租期
                    while (0 > time.compareTo(clause.getCEndTime())){
                        ChargeDetailDto cdd = new ChargeDetailDto();
                        cdd.setType(1 == cdp.getClauseType()?"租金":"物业费");
                        cdd.setFinalPrice(clause.getContractPrice()+ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
                        //获取租期的开始时间
                        if(0 == time .compareTo(clause.getCStartTime())){
                            priTime = time;
                        }else{
                            priTime = time;
                            Calendar c = Calendar.getInstance();
                            c.setTime(priTime);
                            c.add(Calendar.DATE,1);
                            priTime = c.getTime();
                        }
                        //计算租期的结束时间
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(priTime);
                        cal.add(Calendar.MONTH,clause.getPayCycle()-1);
                        cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DATE));
                        time = cal.getTime();
                        cdd.setPayDay(this.getPayDate(priTime,clause.getPayType(),clause.getPayDate(),clause.getDateType()));
                        //对最后不满付费周期的时间段的处理
                        if(0<=time.compareTo(clause.getCEndTime())){
                            cdd.setSection(sdf.format(priTime)+"-"+sdf.format(clause.getCEndTime()));
                            cdd.setReceivable(this.getReceiveCharge(priTime,clause.getCEndTime(),cdp.getRentNum(),clause));
                            if(1 == flag){
                                this.increase(cdp,cdd,priTime,clause.getCEndTime(),clause);
                            }
                        }else{
                            cdd.setSection(sdf.format(priTime)+"-"+sdf.format(time));
                            cdd.setReceivable(this.getReceiveCharge(priTime,time,cdp.getRentNum(),clause));
                            if(1 == flag){
                                this.increase(cdp,cdd,priTime,time,clause);
                            }
                        }
                        cdd.setCarryForward(new BigDecimal("0.00"));
                        cdd.setNeedCollect(cdd.getReceivable().subtract(cdd.getCarryForward()));
                        this.discount1(cdp,cdd,clause);
                        rent.add(cdd);
                    }
                }
            }else{
                //按月进行收费
                //按起始日划分租期
                if(1 == clause.getLeaseTermDivision()){
                    while (0 > time.compareTo(clause.getCEndTime())){
                        ChargeDetailDto cdd = new ChargeDetailDto();
                        cdd.setType(1 == cdp.getClauseType()?"租金":"物业费");
                        cdd.setFinalPrice(clause.getContractPrice()+ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
                        //获取租期的开始时间
                        if(0 == time .compareTo(clause.getCStartTime())){
                            priTime = time;
                        }else{
                            priTime = time;
                            Calendar c = Calendar.getInstance();
                            c.setTime(priTime);
                            c.add(Calendar.DATE,1);
                            priTime = c.getTime();
                        }
                        //计算租期的结束时间
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(priTime);
                        cal.add(Calendar.MONTH,clause.getPayCycle());
                        cal.add(Calendar.DATE,-1);
                        time = cal.getTime();
                        //付款日，根据租期开始时间、推迟/提前、天数、日期的形式获取付款时间；
                        cdd.setPayDay(this.getPayDate(priTime,clause.getPayType(),clause.getPayDate(),clause.getDateType()));
                        //对最后不满付费周期的时间段的处理
                        if(0<=time.compareTo(clause.getCEndTime())){
                            cdd.setSection(sdf.format(priTime)+"-"+sdf.format(clause.getCEndTime()));
                            cdd.setReceivable(this.getReceiveCharge(priTime,clause.getCEndTime(),cdp.getRentNum(),clause));
                            String[] split = getMonthAndDay(priTime, clause.getCEndTime()).split(",");
                            if(0 < Integer.parseInt(split[0]) && 0 < Integer.parseInt(split[1])){
                                ArrayList<ChargeDetail> sonRent = new ArrayList<>();
                                //整月份分支
                                ChargeDetail sonMonthCdd = new ChargeDetail();
                                BeanUtils.copyProperties(cdd,sonMonthCdd);
                                //额外天数分支
                                ChargeDetail sonDayCdd = new ChargeDetail();
                                BeanUtils.copyProperties(cdd,sonDayCdd);
                                //计算整月份的结束日期
                                Calendar instance = Calendar.getInstance();
                                instance.setTime(priTime);
                                instance.add(Calendar.MONTH,Integer.parseInt(split[0]));
                                instance.add(Calendar.DATE,-1);
                                Date sonMonthTime = instance.getTime();
                                sonMonthCdd.setSection(sdf.format(priTime)+"-"+sdf.format(sonMonthTime));
                                clause.setCostType(2);
                                sonMonthCdd.setReceivable(this.getReceiveCharge(priTime,sonMonthTime,cdp.getRentNum(),clause));
                                sonMonthCdd.setCarryForward(new BigDecimal("0.00"));
                                sonMonthCdd.setNeedCollect(sonMonthCdd.getReceivable().subtract(sonMonthCdd.getCarryForward()));
                                //获取额外天数的开始日期
                                instance.setTime(sonMonthTime);
                                instance.add(Calendar.DATE,1);
                                Date sonDayTime = instance.getTime();
                                sonDayCdd.setSection(sdf.format(sonDayTime)+"-"+sdf.format(clause.getCEndTime()));
                                sonDayCdd.setReceivable(this.getReceiveCharge(sonDayTime,clause.getCEndTime(),cdp.getRentNum(),clause));
                                sonDayCdd.setCarryForward(new BigDecimal("0.00"));
                                sonDayCdd.setNeedCollect(sonDayCdd.getReceivable().subtract(sonDayCdd.getCarryForward()));
                                //将分支添加至主体租期中，改变主体租期的最终单价以及计算的金额（等于各分支单独计算金额的和）
                                sonRent.add(sonMonthCdd);
                                sonRent.add(sonDayCdd);
                                cdd.setFinalPrice("-");
                                cdd.setReceivable(sonMonthCdd.getReceivable().add(sonDayCdd.getReceivable()));
                                cdd.setChargeDetails(sonRent);
                            }
                            if(1 == flag){
                                this.increase1(cdp,cdd,priTime,clause.getCEndTime(),clause,1);
                            }
                        }else{
                            cdd.setSection(sdf.format(priTime)+"-"+sdf.format(time));
                            cdd.setReceivable(this.getReceiveCharge(priTime,time,cdp.getRentNum(),clause));
                            if(1 == flag){
                                this.increase1(cdp,cdd,priTime,time,clause,1);
                            }
                        }
                        cdd.setCarryForward(new BigDecimal("0.00"));
                        cdd.setNeedCollect(cdd.getReceivable().subtract(cdd.getCarryForward()));
                        this.discount1(cdp,cdd,clause);
                        rent.add(cdd);
                    }
                }else{
                    //按自然月划分租期
                    while (0 > time.compareTo(clause.getCEndTime())){
                        ChargeDetailDto cdd = new ChargeDetailDto();
                        cdd.setType(1 == cdp.getClauseType()?"租金":"物业费");
                        cdd.setFinalPrice(clause.getContractPrice()+ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
                        //获取租期的开始时间
                        if(0 == time .compareTo(clause.getCStartTime())){
                            priTime = time;
                        }else{
                            priTime = time;
                            Calendar c = Calendar.getInstance();
                            c.setTime(priTime);
                            c.add(Calendar.DATE,1);
                            priTime = c.getTime();
                        }
                        //计算租期的结束时间
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(priTime);
                        cal.add(Calendar.MONTH,clause.getPayCycle()-1);
                        cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DATE));
                        time = cal.getTime();
                        cdd.setPayDay(this.getPayDate(priTime,clause.getPayType(),clause.getPayDate(),clause.getDateType()));
                        //对最后不满付费周期的时间段的处理
                        if(0<=time.compareTo(clause.getCEndTime())){
                            cdd.setSection(sdf.format(priTime)+"-"+sdf.format(clause.getCEndTime()));
                            cdd.setReceivable(this.getReceiveCharge(priTime,clause.getCEndTime(),cdp.getRentNum(),clause));
                            String[] split = getMonthAndDay(priTime, clause.getCEndTime()).split(",");
                            if(0 < Integer.parseInt(split[0])&& 0 < Integer.parseInt(split[1])) {
                                ArrayList<ChargeDetail> sonRent = new ArrayList<>();
                                //整月份分支
                                ChargeDetail sonMonthCdd = new ChargeDetail();
                                BeanUtils.copyProperties(cdd, sonMonthCdd);
                                //额外天数分支
                                ChargeDetail sonDayCdd = new ChargeDetail();
                                BeanUtils.copyProperties(cdd, sonDayCdd);
                                Calendar instance = Calendar.getInstance();
                                instance.setTime(priTime);
                                instance.add(Calendar.MONTH, Integer.parseInt(split[0]));
                                instance.add(Calendar.DATE, -1);
                                Date sonMonthTime = instance.getTime();
                                sonMonthCdd.setSection(sdf.format(priTime) + "-" + sdf.format(sonMonthTime));
                                sonMonthCdd.setReceivable(this.getReceiveCharge(priTime,sonMonthTime,cdp.getRentNum(),clause));
                                sonMonthCdd.setCarryForward(new BigDecimal("0.00"));
                                sonMonthCdd.setNeedCollect(sonMonthCdd.getReceivable().subtract(sonMonthCdd.getCarryForward()));
                                instance.setTime(sonMonthTime);
                                instance.add(Calendar.DATE, 1);
                                Date sonDayTime = instance.getTime();
                                sonDayCdd.setSection(sdf.format(sonDayTime) + "-" + sdf.format(clause.getCEndTime()));
                                //改变计费方式，不满一月的按天进行收费
                                clause.setCostType(1);
                                sonDayCdd.setReceivable(this.getReceiveCharge(sonDayTime,clause.getCEndTime(),cdp.getRentNum(),clause));
                                sonDayCdd.setCarryForward(new BigDecimal("0.00"));
                                sonDayCdd.setNeedCollect(sonDayCdd.getReceivable().subtract(sonDayCdd.getCarryForward()));
                                sonRent.add(sonMonthCdd);
                                sonRent.add(sonDayCdd);
                                cdd.setFinalPrice("-");
                                cdd.setReceivable(sonMonthCdd.getReceivable().add(sonDayCdd.getReceivable()));
                                cdd.setChargeDetails(sonRent);
                            }
                            if(1 == flag){
                                this.increase1(cdp,cdd,priTime,clause.getCEndTime(),clause,2);
                            }
                        }else{
                            String s = sdf.format(priTime);
                            cdd.setSection(s+"-"+sdf.format(time));
                            cdd.setReceivable(this.getReceiveCharge(priTime,time,cdp.getRentNum(),clause));
                            s = s.substring(s.length()-2);
                            //按月划分，首月非整月
                           if(!"01".equals(s) && 1 != clause.getPayCycle()){
                               List<ChargeDetail> sonRent = new ArrayList<>();
                               //自然月分支
                               ChargeDetail sonMonthCdd = new ChargeDetail();
                               BeanUtils.copyProperties(cdd,sonMonthCdd);
                               //非自然月分支
                               ChargeDetail sonDayCdd = new ChargeDetail();
                               BeanUtils.copyProperties(cdd,sonDayCdd);
                               Calendar instance = Calendar.getInstance();
                               instance.setTime(priTime);
                               instance.set(Calendar.DATE,instance.getActualMaximum(Calendar.DATE));
                               sonDayCdd.setSection(sdf.format(priTime)+"-"+sdf.format(instance.getTime()));
                               //改变计费方式，不满一月的按天进行收费
                               clause.setCostType(1);
                               sonDayCdd.setReceivable(this.getReceiveCharge(priTime,instance.getTime(),cdp.getRentNum(),clause));
                               sonDayCdd.setCarryForward(new BigDecimal("0.00"));
                               sonDayCdd.setNeedCollect(sonDayCdd.getReceivable().subtract(sonDayCdd.getCarryForward()));
                               instance.add(Calendar.DATE,1);
                               Date sonMonthTime = instance.getTime();
                               instance.setTime(sonMonthTime);
                               instance.add(Calendar.MONTH,clause.getPayCycle()-2);
                               instance.set(Calendar.DATE,cal.getActualMaximum(Calendar.DATE));
                               sonMonthCdd.setSection(sdf.format(sonMonthTime)+"-"+sdf.format(instance.getTime()));
                               //改变计费方式,按月进行收费
                               clause.setCostType(2);
                               sonMonthCdd.setReceivable(this.getReceiveCharge(sonMonthTime,instance.getTime(),cdp.getRentNum(),clause));
                               sonMonthCdd.setCarryForward(new BigDecimal("0.00"));
                               sonMonthCdd.setNeedCollect(sonMonthCdd.getReceivable().subtract(sonMonthCdd.getCarryForward()));
                               sonRent.add(sonDayCdd);
                               sonRent.add(sonMonthCdd);
                               cdd.setFinalPrice("-");
                               cdd.setReceivable(sonMonthCdd.getReceivable().add(sonDayCdd.getReceivable()));
                               cdd.setChargeDetails(sonRent);
                           }
                            if(1 == flag){
                                this.increase1(cdp,cdd,priTime,time,clause,2);
                            }
                        }
                        cdd.setCarryForward(new BigDecimal("0.00"));
                        cdd.setNeedCollect(cdd.getReceivable().subtract(cdd.getCarryForward()));
                        this.discount1(cdp,cdd,clause);
                        rent.add(cdd);
                    }
                }
            }
        }
        return rent;
    }

    */
/**
     * 按月收费的租期划分
     * @param cdp 入参
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param clause 条款
     * @param cdd 租期
     * @return
     *//*

    private LeaseSplitRuleDto leaseSplitRule(ChargeDetailParam cdp,Date startDate, Date endDate, Clause clause,ChargeDetailDto cdd){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        LeaseSplitRuleDto leaseSplitRuleDto = new LeaseSplitRuleDto();
        List<ChargeDetail> list = new ArrayList<>();
        BigDecimal receivable = new BigDecimal("0.00");
        //条款开始时间
        Date leaseStartTime = clause.getCStartTime();
        //查询条款起始日
        Calendar clauseCal = Calendar.getInstance();
        clauseCal.setTime(leaseStartTime);
        //开始时间
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        //结束时间
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        //相差时间不满一整月，按天计费，不予划分租期
        String[] mad = this.getMonthAndDay(startDate, endDate).split(",");
        if(0 == Integer.parseInt(mad[0])){
            return null;
        }
        //按月收费、按起始日划分租期
        if(2 == clause.getCostType() && 1 == clause.getLeaseTermDivision()){
            //开始时间是起始日，且相差时间大于一个月时，会将原有时间段拆分为两部分
            if(startCal.get(Calendar.DATE) == clauseCal.get(Calendar.DATE)){
                if(endCal.get(Calendar.DATE) == clauseCal.get(Calendar.DATE)-1){
                    return null;
                }
                if(endCal.get(Calendar.DATE)<clauseCal.get(Calendar.DATE)){
                    endCal.add(Calendar.MONTH,-1);
                }
                endCal.set(Calendar.DATE,clauseCal.get(Calendar.DATE));
                endCal.add(Calendar.DATE,-1);
                //整月份
                ChargeDetail beforeCd = new ChargeDetail();
                BeanUtils.copyProperties(cdd,beforeCd);
                beforeCd.setSection(sdf.format(startDate)+"-"+sdf.format(endCal.getTime()));
                clause.setCostType(2);
                beforeCd.setReceivable(this.getReceiveCharge(startDate,endCal.getTime(),cdp.getRentNum(),clause));
                beforeCd.setNeedCollect(beforeCd.getReceivable().subtract(beforeCd.getCarryForward()));
                list.add(beforeCd);
                receivable = receivable.add(beforeCd.getReceivable());
                //额外天数
                endCal.add(Calendar.DATE,1);
                ChargeDetail laterCd = new ChargeDetail();
                BeanUtils.copyProperties(cdd,laterCd);
                laterCd.setSection(sdf.format(endCal.getTime())+"-"+sdf.format(endDate));
                clause.setCostType(1);
                laterCd.setReceivable(this.getReceiveCharge(endCal.getTime(),endDate,cdp.getRentNum(),clause));
                laterCd.setNeedCollect(laterCd.getReceivable().subtract(laterCd.getCarryForward()));
                list.add(laterCd);
                receivable = receivable.add(laterCd.getReceivable());
            }else{
                //开始时间不是起始日，且下一个起始日至结束日大于一个月时，会将原有时间段拆分为三部分（将第一段时间拆出后，再次调用本方法，得到二三段时间）
                if(startCal.get(Calendar.DATE) > clauseCal.get(Calendar.DATE)){
                    startCal.add(Calendar.MONTH,1);
                }
                startCal.set(Calendar.DATE,clauseCal.get(Calendar.DATE));
                startCal.add(Calendar.DATE,-1);
                //获取第一段时间，额外天数
                ChargeDetail beforeCd = new ChargeDetail();
                BeanUtils.copyProperties(cdd,beforeCd);
                beforeCd.setSection(sdf.format(startDate)+"-"+sdf.format(startCal.getTime()));
                clause.setCostType(1);
                beforeCd.setReceivable(this.getReceiveCharge(startDate,startCal.getTime(),cdp.getRentNum(),clause));
                beforeCd.setNeedCollect(beforeCd.getReceivable().subtract(beforeCd.getCarryForward()));
                list.add(beforeCd);
                receivable = receivable.add(beforeCd.getReceivable());
                //获取二三段时间
                startCal.add(Calendar.DATE,1);
                LeaseSplitRuleDto lsrt = this.leaseSplitRule(cdp, startCal.getTime(), endDate, clause, cdd);
                if(null == lsrt){
                    lsrt = new LeaseSplitRuleDto();
                    List<ChargeDetail> cds = new ArrayList<>();
                    ChargeDetail laterCd = new ChargeDetail();
                    BeanUtils.copyProperties(cdd,laterCd);
                    laterCd.setSection(sdf.format(startCal.getTime())+"-"+sdf.format(endDate));
                    clause.setCostType(2);
                    laterCd.setReceivable(this.getReceiveCharge(startCal.getTime(),endDate,cdp.getRentNum(),clause));
                    laterCd.setNeedCollect(laterCd.getReceivable().subtract(laterCd.getCarryForward()));
                    cds.add(laterCd);
                    lsrt.setChargeDetails(cds);
                    lsrt.setReceivable(laterCd.getReceivable());
                }
                list.addAll(lsrt.getChargeDetails());
                receivable = receivable.add(lsrt.getReceivable());
            }
        }else if(2 == clause.getCostType() && 2 == clause.getLeaseTermDivision()){
            //按月收费、按自然月划分租期.
            if(Integer.parseInt("01") == (startCal.get(Calendar.DATE))){
                if(endCal.get(Calendar.DATE) == endCal.getActualMaximum(Calendar.DATE)){
                    return null;
                }
                //开始日为1号，该租期划分为两段
                endCal.add(Calendar.MONTH,-1);
                endCal.set(Calendar.DATE,endCal.getActualMaximum(Calendar.DATE));
                ChargeDetail beforeCd = new ChargeDetail();
                BeanUtils.copyProperties(cdd,beforeCd);
                beforeCd.setSection(sdf.format(startDate)+"-"+sdf.format(endCal.getTime()));
                clause.setCostType(2);
                beforeCd.setReceivable(this.getReceiveCharge(startDate,endCal.getTime(),cdp.getRentNum(),clause));
                beforeCd.setNeedCollect(beforeCd.getReceivable().subtract(beforeCd.getCarryForward()));
                list.add(beforeCd);
                receivable = receivable.add(beforeCd.getReceivable());
                endCal.add(Calendar.DATE,1);
                ChargeDetail laterCd = new ChargeDetail();
                BeanUtils.copyProperties(cdd,laterCd);
                laterCd.setSection(sdf.format(endCal.getTime())+"-"+sdf.format(endDate));
                clause.setCostType(1);
                laterCd.setReceivable(this.getReceiveCharge(endCal.getTime(),endDate,cdp.getRentNum(),clause));
                laterCd.setNeedCollect(laterCd.getReceivable().subtract(laterCd.getCarryForward()));
                list.add(laterCd);
                receivable = receivable.add(laterCd.getReceivable());
            }else{
                //开始日不为1号，该租期划分为三段
                startCal.set(Calendar.DATE,startCal.getActualMaximum(Calendar.DATE));
                ChargeDetail beforeCd = new ChargeDetail();
                BeanUtils.copyProperties(cdd,beforeCd);
                beforeCd.setSection(sdf.format(startDate)+"-"+sdf.format(startCal.getTime()));
                clause.setCostType(1);
                beforeCd.setReceivable(this.getReceiveCharge(startDate,startCal.getTime(),cdp.getRentNum(),clause));
                beforeCd.setNeedCollect(beforeCd.getReceivable().subtract(beforeCd.getCarryForward()));
                list.add(beforeCd);
                receivable = receivable.add(beforeCd.getReceivable());
                startCal.add(Calendar.DATE,1);
                LeaseSplitRuleDto lsrt = this.leaseSplitRule(cdp, startCal.getTime(), endDate, clause, cdd);
                if(null == lsrt){
                    lsrt = new LeaseSplitRuleDto();
                    List<ChargeDetail> cds = new ArrayList<>();
                    ChargeDetail laterCd = new ChargeDetail();
                    BeanUtils.copyProperties(cdd,laterCd);
                    laterCd.setSection(sdf.format(startCal.getTime())+"-"+sdf.format(endDate));
                    clause.setCostType(2);
                    laterCd.setReceivable(this.getReceiveCharge(startCal.getTime(),endDate,cdp.getRentNum(),clause));
                    laterCd.setNeedCollect(laterCd.getReceivable().subtract(laterCd.getCarryForward()));
                    cds.add(laterCd);
                    lsrt.setChargeDetails(cds);
                    lsrt.setReceivable(laterCd.getReceivable());
                }
                receivable = receivable.add(lsrt.getReceivable());
            }
        }
        leaseSplitRuleDto.setChargeDetails(list);
        leaseSplitRuleDto.setReceivable(receivable);
        return leaseSplitRuleDto;
    }

    */
/**
     * 后三种优惠条款对租期明细信息的影响
     * @param cdp
     * @param cdds
     *//*

    private void discount(ChargeDetailParam cdp,List<ChargeDetailDto> cdds ){
        List<PreferenceClause> pcs = cdp.getPreferenceClauses();
        if(null == pcs || 0 == pcs.size()){
            return;
        }
        for (PreferenceClause pc : pcs) {
        if(3 == pc.getPreferenceType() || 4 == pc.getPreferenceType() || 5 == pc.getPreferenceType()){
                for(int i = pc.getStartPeriods()-1; i < pc.getStartPeriods()+pc.getLongPeriod()-1; i++){
                    ChargeDetailDto cdd = cdds.get(i);
                    if(null == cdd.getChargeDetails() || 0 == cdd.getChargeDetails().size()){
                        this.discountCost(pc, cdd);
                    }else {
                        BigDecimal receivable = new BigDecimal("0.00");
                        ArrayList<ChargeDetail> cds = new ArrayList<>();
                        for (ChargeDetail chargeDetail : cdd.getChargeDetails()) {
                            ChargeDetailDto sonCdd = new ChargeDetailDto();
                            BeanUtils.copyProperties(chargeDetail,sonCdd);
                            ChargeDetailDto chargeDetailDto = this.discountCost(pc, sonCdd);
                            receivable = receivable.add(chargeDetailDto.getReceivable());
                            BeanUtils.copyProperties(chargeDetailDto,chargeDetail);
                            cds.add(chargeDetail);
                        }
                        cdd.setChargeDetails(cds);
                        cdd.setReceivable(receivable);
                    }
                    cdd.setNeedCollect(cdd.getReceivable().subtract(cdd.getCarryForward()));
                }
            }
        }
    }

    */
/**
     * 前两种优惠条款对租期明细信息的影响
     * @param cdp
     * @param cdd
     *//*

    //方法内改变租期划分
    private void discount1(ChargeDetailParam cdp,ChargeDetailDto cdd,Clause clause){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<ChargeDetail> list = new ArrayList<>();
        BigDecimal receivable = new BigDecimal("0.00");
        List<PreferenceClause> pcs = cdp.getPreferenceClauses();
        if(null == pcs || 0 == pcs.size()){
            return;
        }
        for (PreferenceClause pc : pcs) {
            //获取开始减免租期前一天的时间
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(pc.getPStartTime());
            startCal.add(Calendar.DATE,-1);
            //获取结束减免租期后一天的时间
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(pc.getPEndTime());
            endCal.add(Calendar.DATE,1);
            if(1 == pc.getPreferenceType() || 2 == pc.getPreferenceType()){
                if(null == cdd.getChargeDetails() || 0 == cdd.getChargeDetails().size()){
                    String[] split = cdd.getSection().split("-");
                    Date startDate = null;
                    Date endDate = null;
                    try {
                        startDate = sdf.parse(split[0]);
                        endDate = sdf.parse(split[1]);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //租期完全免租
                    if(0 >= pc.getPStartTime().compareTo(startDate) && 0 <= pc.getPEndTime().compareTo(endDate)){
                        cdd.setReceivable(new BigDecimal("0"));
                    }else if(0 >= pc.getPStartTime().compareTo(startDate) && 0 > pc.getPEndTime().compareTo(endDate) && 0 < pc.getPEndTime().compareTo(startDate)){
                        //租期前半部分免租
                        ChargeDetail free = new ChargeDetail();
                        BeanUtils.copyProperties(cdd,free);
                        free.setSection(sdf.format(startDate)+"-"+sdf.format(pc.getPEndTime()));
                        free.setReceivable(new BigDecimal("0.00"));
                        free.setNeedCollect(new BigDecimal("0.00"));
                        list.add(free);
                        receivable = this.noDiscountLease(cdp, cdd, clause, list, receivable, endCal.getTime(), endDate);
                    }else if(0 < pc.getPStartTime().compareTo(startDate) && 0 > pc.getPEndTime().compareTo(endDate)){
                        //租期中间部分免租,将租期划分为三个时间段
                        receivable = this.noDiscountLease(cdp, cdd, clause, list, receivable, startDate, startCal.getTime());
                        ChargeDetail free = new ChargeDetail();
                        free.setSection(sdf.format(pc.getPStartTime())+"-"+sdf.format(pc.getPEndTime()));
                        BeanUtils.copyProperties(cdd,free);
                        free.setReceivable(new BigDecimal("0.00"));
                        free.setNeedCollect(new BigDecimal("0.00"));
                        list.add(free);
                        receivable = this.noDiscountLease(cdp, cdd, clause, list, receivable, endCal.getTime(), endDate);
                    }else if(0 < pc.getPStartTime().compareTo(startDate) && 0 > pc.getPStartTime().compareTo(endDate) && 0 <= pc.getPEndTime().compareTo(endDate) ){
                        //租期后半部分免租
                        receivable = this.noDiscountLease(cdp, cdd, clause, list, receivable, startDate, startCal.getTime());
                        ChargeDetail free = new ChargeDetail();
                        free.setSection(sdf.format(pc.getPStartTime())+"-"+sdf.format(endDate));
                        BeanUtils.copyProperties(cdd,free);
                        free.setReceivable(new BigDecimal("0.00"));
                        free.setNeedCollect(new BigDecimal("0.00"));
                        list.add(free);
                    }
                    if(0 < list.size()){
                        cdd.setChargeDetails(list);
                        cdd.setFinalPrice("-");
                        cdd.setNeedCollect(cdd.getReceivable().subtract(cdd.getCarryForward()));
                    }
                }
            }
        }
    }

    private BigDecimal noDiscountLease(ChargeDetailParam cdp, ChargeDetailDto cdd, Clause clause, List<ChargeDetail> list, BigDecimal receivable, Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        LeaseSplitRuleDto lsrt = this.leaseSplitRule(cdp, startDate, endDate, clause, cdd);
        if (lsrt == null) {
            ChargeDetail chargeDetail = new ChargeDetail();
            BeanUtils.copyProperties(cdd, chargeDetail);
            chargeDetail.setSection(sdf.format(startDate) + "-" + sdf.format(endDate));
            chargeDetail.setReceivable(this.getReceiveCharge(startDate, endDate, cdp.getRentNum(), clause));
            chargeDetail.setNeedCollect(chargeDetail.getReceivable().subtract(chargeDetail.getCarryForward()));
            list.add(chargeDetail);
            receivable = receivable.add(chargeDetail.getReceivable());
        } else {
            list.addAll(lsrt.getChargeDetails());
            receivable = receivable.add(lsrt.getReceivable());
        }
        return receivable;
    }

    */
/**
     * 计算后三种优惠方式优惠后应收金额
     * @param pc 优惠条款
     * @param cdd 租期
     *//*

    private ChargeDetailDto discountCost(PreferenceClause pc, ChargeDetailDto cdd ) {
        String[] s = cdd.getFinalPrice().split("元");
        if(3 == pc.getPreferenceType()){
             BigDecimal finalPrice = new BigDecimal(s[0]).multiply(pc.getDiscount()).divide(new BigDecimal("10")).setScale(5,BigDecimal.ROUND_HALF_UP);
             cdd.setFinalPrice(finalPrice+"元"+s[1]);
             cdd.setReceivable(cdd.getReceivable().multiply(finalPrice));
         }else if(4 == pc.getPreferenceType()){
             cdd.setReceivable(cdd.getReceivable().subtract(pc.getDiscount()));
         }else if(5 == pc.getPreferenceType()){
            BigDecimal finalPrice = new BigDecimal(s[0]).subtract(pc.getDiscount());
            cdd.setFinalPrice(finalPrice+"元"+s[1]);
            cdd.setReceivable(cdd.getReceivable().multiply(finalPrice));
        }
        cdd.setNeedCollect(cdd.getReceivable().subtract(cdd.getCarryForward()));
        return cdd;
    }

    */
/**
     * 按天进行收费的递增条款影响
     * @param cdp 入参
     * @param cdd 租期
     * @param startTime 租期开始时间
     * @param endTime 租期结束时间
     * @param clause 条款信息
     *//*

    private void increase(ChargeDetailParam cdp, ChargeDetailDto cdd, Date startTime, Date endTime,Clause clause){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<IncreaseClause> ics = cdp.getIncreaseClauses();
        if(null == ics || 0 == ics.size()){
            return;
        }
        for (IncreaseClause ic : ics) {
            Date increaseTime = ic.getIncreaseTime();
            //递增时间在租期内或者递增时间等于租期结束时间
            if(0 >= increaseTime.compareTo(endTime) && 0 < increaseTime.compareTo(startTime)){
                if (null == cdd.getChargeDetails() || 0 == cdd.getChargeDetails().size()){
                    List<ChargeDetail> cds = new ArrayList<>();
                    //增长前租期
                    ChargeDetail preIncrease = new ChargeDetail();
                    BeanUtils.copyProperties(cdd,preIncrease);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(increaseTime);
                    cal.add(Calendar.DATE,-1);
                    preIncrease.setSection(sdf.format(startTime)+"-"+sdf.format(cal.getTime()));
                    preIncrease.setReceivable(this.getReceiveCharge(startTime,cal.getTime(),cdp.getRentNum(),clause));
                    preIncrease.setCarryForward(new BigDecimal("0.00"));
                    preIncrease.setNeedCollect(preIncrease.getReceivable().subtract(preIncrease.getCarryForward()));
                    cds.add(preIncrease);
                    //增长后租期
                    ChargeDetail afterIncrease = new ChargeDetail();
                    BeanUtils.copyProperties(cdd,afterIncrease);
                    afterIncrease.setSection(sdf.format(increaseTime) + "-" + sdf.format(endTime));
                    clause.setContractPrice(this.getIncreaseRentPrice(clause.getContractPrice(),ic.getPriceNumber(),ic.getPriceType()));
                    afterIncrease.setReceivable(this.getReceiveCharge(increaseTime,endTime,cdp.getRentNum(),clause));
                    afterIncrease.setCarryForward(new BigDecimal("0.00"));
                    afterIncrease.setNeedCollect(afterIncrease.getReceivable().subtract(afterIncrease.getCarryForward()));
                    cds.add(afterIncrease);
                    cdd.setReceivable(preIncrease.getReceivable().add(afterIncrease.getReceivable()));
                    cdd.setChargeDetails(cds);
                }
            }else if(0 == increaseTime.compareTo(startTime)){
                //递增时间在租期开始时间
                clause.setContractPrice(this.getIncreaseRentPrice(clause.getContractPrice(),ic.getPriceNumber(),ic.getPriceType()));
                cdd.setReceivable(this.getReceiveCharge(startTime,endTime,cdp.getRentNum(),clause));
            }
            //递增时间在租期结束时间
        }
    }

    */
/**
     * 按月进行收费的递增条款影响
     * @param cdp 入参
     * @param cdd 租期
     * @param startTime 租期开始时间
     * @param endTime 租期结束时间
     * @param clause 条款信息
     * @param flag 标志 1，按起始日进行租期划分 2，按自然月进行租期划分
     *//*

    private void increase1(ChargeDetailParam cdp, ChargeDetailDto cdd, Date startTime, Date endTime,Clause clause,Integer flag){
        //按月计费
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<IncreaseClause> ics = cdp.getIncreaseClauses();
        if(null == ics || 0 == ics.size()){
            return;
        }
        for (IncreaseClause ic : ics) {
            Date increaseTime = ic.getIncreaseTime();
            //递增时间在租期内或者递增时间等于租期结束时间
            if (0 >= increaseTime.compareTo(endTime) && 0 < increaseTime.compareTo(startTime)){
                BigDecimal receivable = new BigDecimal("0.00");
                //当前租期没有子分支
                List<ChargeDetail> cds = new ArrayList<>();
                if(null == cdd.getChargeDetails() || 0 == cdd.getChargeDetails().size()){
                    receivable = this.increaseByMonthCharge(cdp.getRentNum(), cdd, startTime, endTime, clause, ic, receivable, cds,flag);
                }else{
                //当前租期包含子分支
                    List<ChargeDetail> chargeDetails = cdd.getChargeDetails();
                    for (ChargeDetail chargeDetail : chargeDetails) {
                        ChargeDetailDto sonCdd = new ChargeDetailDto();
                        BeanUtils.copyProperties(chargeDetail,sonCdd);
                        String[] split = sonCdd.getSection().split("-");
                        Date sonStartTime =null;
                        Date sonEndTime = null;
                        try {
                            sonStartTime = sdf.parse(split[0]);
                            sonEndTime = sdf.parse(split[1]);
                        }catch (Exception e){

                        }
                        if (0 >= increaseTime.compareTo(sonEndTime) && 0 < increaseTime.compareTo(sonStartTime)) {
                            this.increaseByMonthCharge(cdp.getRentNum(), sonCdd, sonStartTime, sonEndTime, clause, ic, receivable, cds,flag);
                        }else{
                            chargeDetail.setFinalPrice(clause.getContractPrice()+ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
                            chargeDetail.setReceivable(this.getReceiveCharge(sonStartTime,sonEndTime,cdp.getRentNum(),clause));
                            chargeDetail.setNeedCollect(chargeDetail.getReceivable().subtract(chargeDetail.getCarryForward()));
                            cds.add(chargeDetail);
                        }
                    }
                }
                cdd.setChargeDetails(cds);
                cdd.setFinalPrice("-");
                cdd.setReceivable(receivable);
            }else if(0 == increaseTime.compareTo(startTime)){
                //递增时间在租期开始时间
                clause.setContractPrice(this.getIncreaseRentPrice(clause.getContractPrice(),ic.getPriceNumber(),ic.getPriceType()));
                cdd.setReceivable(this.getReceiveCharge(startTime,endTime,cdp.getRentNum(),clause));
            }
        }

    }

    */
/**
     * 按月进行计费，递增时给没有子分支的租期生成子分支的方法
     * @param rentNumber 租赁面积
     * @param cdd 租期
     * @param startTime 租期开始时间
     * @param endTime 租期结束时间
     * @param clause 主体条款信息
     * @param ic 递增条款信息
     * @param receivable 租期应收金额
     * @param cds 子分支列表
     * @param flag 标志 1，按起始日进行租期划分 2，按自然月进行租期划分
     * @return
     *//*

    private BigDecimal increaseByMonthCharge(BigDecimal rentNumber, ChargeDetailDto cdd, Date startTime, Date endTime, Clause clause, IncreaseClause ic, BigDecimal receivable, List<ChargeDetail> cds, Integer flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date increaseTime = ic.getIncreaseTime();
        //获取递增日期的前一天
        Calendar instance = Calendar.getInstance();
        instance.setTime(increaseTime);
        instance.add(Calendar.DATE,-1);
        Date increaseBefore = instance.getTime();
        //判断递增日期与租期开始日期是否相差整月份
        String[] madStart = this.getMonthAndDay(startTime, increaseTime).split(",");
        if(0 < Integer.parseInt(madStart[0]) && 0 < Integer.parseInt(madStart[1])){
            Calendar increaseCal = Calendar.getInstance();
            //计算整月份结束日期
            increaseCal.setTime(startTime);
            if(1 == flag){
                increaseCal.add(Calendar.MONTH,Integer.parseInt(madStart[0]));
                increaseCal.add(Calendar.DATE,-1);
            }else{
                increaseCal.add(Calendar.MONTH,Integer.parseInt(madStart[0])-1);
                increaseCal.set(Calendar.DATE,increaseCal.getActualMaximum(Calendar.DATE));
            }
            //递增前整月份租金明细
            ChargeDetail increaseBeforeMonth = new ChargeDetail();
            BeanUtils.copyProperties(cdd,increaseBeforeMonth);
            increaseBeforeMonth.setSection(sdf.format(startTime)+"-"+sdf.format(increaseCal.getTime()));
            BigDecimal monthReceive = this.getReceiveCharge(startTime, increaseCal.getTime(), rentNumber, clause);
            receivable = receivable.add(monthReceive);
            increaseBeforeMonth.setReceivable(monthReceive);
            increaseBeforeMonth.setCarryForward(new BigDecimal("0.00"));
            increaseBeforeMonth.setNeedCollect(increaseBeforeMonth.getReceivable().subtract(increaseBeforeMonth.getCarryForward()));
            cds.add(increaseBeforeMonth);
            //额外天数的开始日期
            increaseCal.setTime(increaseCal.getTime());
            increaseCal.add(Calendar.DATE,1);
            //递增前额外天数租金明细
            ChargeDetail increaseBeforeDay = new ChargeDetail();
            BeanUtils.copyProperties(cdd,increaseBeforeDay);
            increaseBeforeDay.setSection(sdf.format(increaseCal.getTime())+"-"+sdf.format(increaseBefore));
            BigDecimal receiveDay = this.getReceiveCharge(increaseCal.getTime(), increaseBefore, rentNumber, clause);
            receivable = receivable.add(receiveDay);
            increaseBeforeDay.setReceivable(receiveDay);
            increaseBeforeDay.setCarryForward(new BigDecimal("0.00"));
            increaseBeforeDay.setNeedCollect(increaseBeforeDay.getReceivable().subtract(increaseBeforeDay.getCarryForward()));
            cds.add(increaseBeforeDay);
        }else{
            ChargeDetail increaseBeforeDate = new ChargeDetail();
            BeanUtils.copyProperties(cdd,increaseBeforeDate);
            increaseBeforeDate.setSection(sdf.format(startTime)+"-"+sdf.format(increaseBefore));
            BigDecimal dateReceive = this.getReceiveCharge(startTime, increaseBefore, rentNumber, clause);
            increaseBeforeDate.setReceivable(dateReceive);
            increaseBeforeDate.setCarryForward(new BigDecimal("0.00"));
            increaseBeforeDate.setNeedCollect(increaseBeforeDate.getReceivable().subtract(increaseBeforeDate.getCarryForward()));
            cds.add(increaseBeforeDate);
            receivable = receivable.add(dateReceive);
        }
        //判断递增日期与租期结束时间是否相差整月份
        String[] madEnd = this.getMonthAndDay(increaseTime, endTime).split(",");
        if(0 < Integer.parseInt(madEnd[0]) && 0 < Integer.parseInt(madEnd[1])){
            clause.setContractPrice(this.getIncreaseRentPrice(clause.getContractPrice(),ic.getPriceNumber(),ic.getPriceType()));
            //计算下个整月开始时间
            Calendar startCal = Calendar.getInstance();
            if(1 == flag){
                startCal.setTime(startTime);
                startCal.add(Calendar.DATE,-1);
                Calendar increaseCal = Calendar.getInstance();
                increaseCal.setTime(increaseTime);
                if(startCal.get(Calendar.DATE) >= increaseCal.get(Calendar.DATE)){
                    startCal.set(Calendar.MONTH,increaseCal.get(Calendar.MONTH));
                }else{
                    startCal.set(Calendar.MONTH,increaseCal.get(Calendar.MONTH)+1);
                }
            }else{
                startCal.setTime(increaseTime);
                startCal.set(Calendar.DATE,startCal.getActualMaximum(Calendar.DATE));
            }
            //递增后额外天数租期详情
            ChargeDetail increaseAfterDay = new ChargeDetail();
            increaseAfterDay.setSection(sdf.format(increaseTime)+"-"+sdf.format(startCal.getTime()));
            BigDecimal dayReceive = this.getReceiveCharge(increaseTime, startCal.getTime(), rentNumber, clause);
            increaseAfterDay.setReceivable(dayReceive);
            increaseAfterDay.setFinalPrice(clause.getContractPrice()+ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
            increaseAfterDay.setCarryForward(new BigDecimal("0.00"));
            increaseAfterDay.setNeedCollect(increaseAfterDay.getReceivable().subtract(increaseAfterDay.getCarryForward()));
            cds.add(increaseAfterDay);
            receivable = receivable.add(dayReceive);
            //递增后整月份租期详情
            startCal.add(Calendar.DATE,1);
            ChargeDetail increaseAfterMonth = new ChargeDetail();
            increaseAfterMonth.setSection(sdf.format(startCal.getTime())+"-"+sdf.format(endTime));
            BigDecimal monthReceive = this.getReceiveCharge(startCal.getTime(), endTime, rentNumber, clause);
            increaseAfterMonth.setReceivable(monthReceive);
            increaseAfterMonth.setFinalPrice(clause.getContractPrice()+ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
            increaseAfterMonth.setCarryForward(new BigDecimal("0.00"));
            increaseAfterMonth.setNeedCollect(increaseAfterMonth.getReceivable().subtract(increaseAfterMonth.getCarryForward()));
            cds.add(increaseAfterMonth);
            receivable = receivable.add(monthReceive);
        }else{
            clause.setContractPrice(this.getIncreaseRentPrice(clause.getContractPrice(),ic.getPriceNumber(),ic.getPriceType()));
            ChargeDetail increaseAfterDate = new ChargeDetail();
            BeanUtils.copyProperties(cdd,increaseAfterDate);
            increaseAfterDate.setSection(sdf.format(increaseBefore)+"-"+sdf.format(endTime));
            BigDecimal dateReceive = this.getReceiveCharge(increaseBefore, endTime, rentNumber, clause);
            receivable = receivable.add(dateReceive);
            increaseAfterDate.setReceivable(dateReceive);
            increaseAfterDate.setFinalPrice(clause.getContractPrice()+ContractPriceTypeEnum.getDescByVal(clause.getContractUnitPrice()));
            increaseAfterDate.setCarryForward(new BigDecimal("0.00"));
            increaseAfterDate.setNeedCollect(increaseAfterDate.getReceivable().subtract(increaseAfterDate.getCarryForward()));
            cds.add(increaseAfterDate);
        }
        return receivable;
    }

    */
/**
     * 计算增长后的合同单价
     * @param priPrice  增长前合同单价
     * @param priceNumber 增长数量
     * @param priceType 增长类型
     * @return
     *//*

    private BigDecimal getIncreaseRentPrice(BigDecimal priPrice, BigDecimal priceNumber, Integer priceType){
        if(1 == priceType){
            priPrice = priPrice.add(priPrice.multiply(priceNumber.divide(new BigDecimal("100"),5,BigDecimal.ROUND_HALF_UP)));
        }else{
            priPrice = priPrice.add(priceNumber);
        }
        return priPrice;
    }

    */
/**
     * 获取一个租期内应收金额
     * @param startTime 租期开始时间
     * @param endTime 租期结束时间
     * @param clause 最终单价\单价类型\计费类型\年天数
     * @param rentNumber 租赁数量
     * @return
     *//*

    private BigDecimal getReceiveCharge(Date startTime, Date endTime, BigDecimal rentNumber,Clause clause){
        Integer costType = clause.getCostType();
        Integer unitPriceType = clause.getContractUnitPrice();
        BigDecimal finalPrice = clause.getContractPrice();
        Integer dayOfYear = clause.getDaysOfYear();
        long startDateTime = startTime.getTime();
        long endDateTime = endTime.getTime();
        //计算当前租期开始结束时间相差的天数
        int days = (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24))+1;
        //计算当前租期开始结束时间相差几个月零几天
        String[] split = this.getMonthAndDay(startTime, endTime).split(",");
        BigDecimal dayPrice;
        BigDecimal monthPrice;
        BigDecimal contractPrice = null;
        if(1 == unitPriceType){
            dayPrice = finalPrice;
            monthPrice = finalPrice.multiply(new BigDecimal(dayOfYear)).divide(new BigDecimal("12"),3,BigDecimal.ROUND_HALF_UP);
            if(1 == costType){
                contractPrice = dayPrice.multiply(rentNumber).multiply(new BigDecimal(days));
            }else{
                contractPrice = monthPrice.multiply(new BigDecimal(split[0])).multiply(rentNumber).add(dayPrice.multiply(new BigDecimal(split[1])).multiply(rentNumber));
            }
        }else if(2 == unitPriceType){
            monthPrice = finalPrice;
            dayPrice = monthPrice.multiply(new BigDecimal("12").divide(new BigDecimal(dayOfYear),3,BigDecimal.ROUND_HALF_UP));
            if(1 == costType){
                contractPrice = dayPrice.multiply(rentNumber).multiply(new BigDecimal(days));
            }else{
                contractPrice = monthPrice.multiply(new BigDecimal(split[0])).multiply(rentNumber).add(dayPrice.multiply(new BigDecimal(split[1])).multiply(rentNumber));
            }
        }else if(3 == unitPriceType){
            monthPrice = finalPrice;
            dayPrice = monthPrice.multiply(new BigDecimal("12")).divide(new BigDecimal(dayOfYear), 3, BigDecimal.ROUND_HALF_UP);
            if(1 == costType){
                contractPrice = dayPrice.multiply(new BigDecimal(days));
            }else{
                contractPrice = monthPrice.multiply(new BigDecimal(split[0])).add(dayPrice.multiply(new BigDecimal(split[1])));
            }
        }else if(4 == unitPriceType){
            dayPrice = finalPrice;
            monthPrice = finalPrice.multiply(new BigDecimal(dayOfYear).divide(new BigDecimal("12"),3,BigDecimal.ROUND_HALF_UP));
            if(1 == costType){
                contractPrice = dayPrice.multiply(new BigDecimal(days));
            }else{
                contractPrice = monthPrice.multiply(new BigDecimal(split[0])).add(dayPrice.multiply(new BigDecimal(split[1])));
            }
        }else if(5 == unitPriceType){
            dayPrice = finalPrice.divide(new BigDecimal(dayOfYear),3,BigDecimal.ROUND_HALF_UP);
            monthPrice = finalPrice.divide(new BigDecimal("12"),3,BigDecimal.ROUND_HALF_UP);
            if(1 == costType){
                contractPrice = dayPrice.multiply(new BigDecimal(days));
            }else{
                contractPrice = monthPrice.multiply(new BigDecimal(split[0])).add(dayPrice.multiply(new BigDecimal(split[1])));
            }
        }
        BigDecimal bigDecimal = contractPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;
    }

    */
/**
     * 获取关于保证金的明细信息
     * @param cdp 生成明细数据的入参
     * @return
     *//*

    private List<ChargeDetailDto> getMarginDetail(ChargeDetailParam cdp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<ChargeDetailDto> list = new ArrayList<>();
        ChargeDetailDto margin = new ChargeDetailDto();
        margin.setSection(sdf.format(cdp.getStartTime())+"-"+sdf.format(cdp.getEndTime()));
        margin.setType(1 == cdp.getClauseType()?"租金保证金":"物业保证金(能耗周转费)");
        margin.setPayDay(cdp.getStartTime());
        margin.setFinalPrice("-");
        //保证金应收金额(按照主体条款信息第一条进行计算)
        //Clause clause = JSON.parseArray(cdp.getClause(), Clause.class).get(0);
        Clause clause = cdp.getClauses().get(0);
        BigDecimal contractPrice = null;
        if(1 == clause.getContractUnitPrice()){
            contractPrice = clause.getContractPrice().multiply(cdp.getRentNum()).multiply(cdp.getMargin()).multiply(new BigDecimal("30"));
        }else if(2 == clause.getContractUnitPrice()){
            contractPrice = clause.getContractPrice().multiply(cdp.getRentNum()).multiply(cdp.getMargin());
        }else if(3 == clause.getContractUnitPrice()){
            contractPrice = clause.getContractPrice().multiply(cdp.getMargin());
        }else if(4 == clause.getContractUnitPrice()){
            contractPrice = clause.getContractPrice().multiply(cdp.getMargin()).multiply(new BigDecimal("30"));
        }else if(5 == clause.getContractUnitPrice()){
            contractPrice = clause.getContractPrice().multiply(cdp.getMargin()).divide(new BigDecimal("12"),5,BigDecimal.ROUND_HALF_UP);
        }
        margin.setReceivable(1 == cdp.getMarginUnit() ? contractPrice : cdp.getMargin());
        margin.setCarryForward(new BigDecimal("0.00"));
        margin.setNeedCollect(margin.getReceivable().subtract(margin.getCarryForward()));
        list.add(margin);
        List<IncreaseClause> increaseClauses = cdp.getIncreaseClauses();
        BigDecimal receivable = margin.getReceivable();
        if(null != increaseClauses && 0 < increaseClauses.size()){
            for (IncreaseClause increaseClause : increaseClauses) {
                ChargeDetailDto increaseMargin = new ChargeDetailDto();
                BeanUtils.copyProperties(margin,increaseMargin);
                increaseMargin.setPayDay(increaseClause.getIncreaseTime());
                BigDecimal increase = null;
                if(1 == increaseClause.getMarginType()){
                    increase = receivable.multiply(increaseClause.getMarginNumber().divide(new BigDecimal("100"),5,BigDecimal.ROUND_HALF_UP));
                    receivable.add(increase);
                }else{
                    increase = increaseClause.getMarginNumber();
                    receivable.add(increase);
                }
                increaseMargin.setReceivable(increase);
                increaseMargin.setNeedCollect(increase.subtract(increaseMargin.getCarryForward()));
                list.add(increaseMargin);
            }
        }
        return list;
    }

    */
/**
     * 计算付款日
     * @param date 租金开始日期
     * @param payType 付款时间选择提前或延迟
     * @param payDate 提前或延迟天数
     * @param dateType 工作日或自然日
     * @return
     *//*

    private Date getPayDate(Date date,Integer payType,Integer payDate,Integer dateType){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day;
        if(1 == dateType){
            if(1 == cal.get(Calendar.DAY_OF_WEEK)){
                day = 1 == payType ? (payDate+2)*(-1) : payDate;
            }else if( 7 == cal.get(Calendar.DAY_OF_WEEK)){
                day = 1 == payType ? (payDate+1)*(-1) : payDate+1;
            }else{
                day = 1 == payType ? payDate*(-1) : payDate;
            }
            cal.add(Calendar.DATE,day);
        }else if(2 == dateType){
            day = 1 == payType ? payDate*(-1) : payDate;
            cal.add(Calendar.DATE,day);
        }else{
            cal.set(Calendar.DATE,payDate);
            if(1 == payType && payDate>cal.get(Calendar.DATE)){
                cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
            } else if (2 == payType && payDate<cal.get(Calendar.DATE)){
                cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
            }
        }
        return cal.getTime();
    }

    */
/**
     * 计算两个日期之间相差几个月零几天
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     *//*

    private String getMonthAndDay(Date startDate,Date endDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(startDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(endDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH)+1;
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH)+1;
        int toDay = to.get(Calendar.DAY_OF_MONTH);

        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay+1  - fromDay;
        if(0 > month){
            year = year -1;
            if(0 > day){
                month = toMonth+(12-fromMonth)-1;
                Calendar c = Calendar.getInstance();
                c.set(toYear, toMonth-1, 0);
                day = toDay + c.get(Calendar.DAY_OF_MONTH) - fromDay+1;
            }else{
                month = toMonth+(12-fromMonth);
            }
        }else{
            if(0 > day){
                month = month -1;
                Calendar c = Calendar.getInstance();
                c.set(toYear, toMonth-1, 0);
                day = toDay + c.get(Calendar.DAY_OF_MONTH) - fromDay+1;
            }
        }
        return year*12 + month + "," + (day);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addContractConfig(FormTemplateConfigDto ftcd) {
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        FormTemplateConfig ftc = new FormTemplateConfig();
        BeanUtils.copyProperties(ftcd,ftc);
        ftc.setDeleteFlag(false);
        ftc.setTenantId(tenantId);
        ftc.setCreateBy(accountId);
        ftc.setCreateTime(System.currentTimeMillis());
        ftc.setUpdateBy(accountId);
        ftc.setUpdateTime(System.currentTimeMillis());
        ftConfigMapper.insert(ftc);
        List<ChargeClauseDto> ccds = ftcd.getChargeClauseDtos();
        List<ChargeClause> ccs = new ArrayList<>();
        for (ChargeClauseDto ccd : ccds) {
            ChargeClause cc = new ChargeClause();
            BeanUtils.copyProperties(ccd,cc);
            cc.setClause(JSON.toJSONString(ccd.getClauses()));
            cc.setIncreaseClause(JSON.toJSONString(ccd.getIncreaseClauses()));
            cc.setDeleteFlag(false);
            cc.setTemplateId(ftc.getId());
            cc.setCreateBy(accountId);
            cc.setCreateTime(System.currentTimeMillis());
            cc.setUpdateBy(accountId);
            cc.setUpdateTime(System.currentTimeMillis());
            ccs.add(cc);
        }
        chargeClauseMapper.insertList(ccs);
        List<SelfField> customValue = ftcd.getCustomValue();
        JsonResult<Integer> data = oaClient.addFieldLayout(customValue, 5, tenantId, accountId, ftc.getId());
        if(200 != data.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        return ftc.getId();
    }

    @Override
    public PagedList<SimpTemplateDto> getAllConfig(PageParam page) {
        Integer tenantId = ThreadLocalUtil.getTenantId();
        String orderBy = OrderParamUtil.builder().append(page.getOrder(), page.getSort()).append("id", 1).build();
        PageHelper.startPage(page.getPage(), page.getPageSize(), orderBy);
        List<SimpTemplateDto> list =  ftConfigMapper.getAllConfig(tenantId);
        for (SimpTemplateDto std : list) {
            StringBuilder regionNames = new StringBuilder();
            String[] split = std.getBuildings().split(",");
            for (String s : split) {
                ProjectBuildingNameDto pbName = buildingMapper.selectRegionName(Integer.parseInt(s));
                regionNames.append(pbName.getProjectName()).append("-").append(pbName.getSubregionName()).append("-").append(pbName.getBuildingName()).append(",");
            }
            if(0 < regionNames.length()){
                regionNames.deleteCharAt(regionNames.length()-1);
            }
            std.setBuildings(regionNames.toString());
        }
        return PagedList.parse(list);
    }

    @Override
    public FormTemplateConfigDto getConfig(Integer id) {
        FormTemplateConfigDto ftcd = getBaseConfig(id);
        Example example = new Example(ChargeClause.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateId",id);
        criteria.andEqualTo("deleteFlag",false);
        List<ChargeClause> chargeClauses = chargeClauseMapper.selectByExample(example);
        List<ChargeClauseDto> ccds = new ArrayList<>();
        for (ChargeClause chargeClause : chargeClauses) {
            ChargeClauseDto ccd = new ChargeClauseDto();
            BeanUtils.copyProperties(chargeClause,ccd);
            ccd.setClauses(JSON.parseArray(chargeClause.getClause(), Clause.class));
            ccd.setIncreaseClauses(JSON.parseArray(chargeClause.getIncreaseClause(), IncreaseClause.class));
            ccd.setPreferenceClauses(JSON.parseArray(chargeClause.getPreferenceClause(),PreferenceClause.class));
            ccd.setOtherClauses(JSON.parseArray(chargeClause.getOtherClause(),Clause.class));
            ccds.add(ccd);
        }
        ftcd.setChargeClauseDtos(ccds);
        return ftcd;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateContractConfig(FormTemplateConfigDto ftcd) {
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        FormTemplateConfig ftc = new FormTemplateConfig();
        BeanUtils.copyProperties(ftcd,ftc);
        ftc.setTenantId(tenantId);
        ftc.setUpdateBy(accountId);
        ftc.setUpdateTime(System.currentTimeMillis());
        ftConfigMapper.updateByPrimaryKeySelective(ftc);
        //费用条款 先删除再插入
        Example example = new Example(ChargeClause.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateId",ftcd.getId());
        criteria.andEqualTo("deleteFlag",false);
        chargeClauseMapper.deleteByExample(example);
        List<ChargeClauseDto> ccds = ftcd.getChargeClauseDtos();
        List<ChargeClause> ccs = new ArrayList<>();
        for (ChargeClauseDto ccd : ccds) {
            ChargeClause cc = new ChargeClause();
            BeanUtils.copyProperties(ccd,cc);
            cc.setClause(JSON.toJSONString(ccd.getClauses()));
            cc.setIncreaseClause(JSON.toJSONString(ccd.getIncreaseClauses()));
            cc.setTemplateId(ftc.getId());
            cc.setDeleteFlag(false);
            cc.setCreateBy(accountId);
            cc.setCreateTime(System.currentTimeMillis());
            cc.setUpdateBy(accountId);
            cc.setUpdateTime(System.currentTimeMillis());
            ccs.add(cc);
        }
        chargeClauseMapper.insertList(ccs);
        List<SelfField> customValue = ftcd.getCustomValue();
        JsonResult<Integer> data = oaClient.updateFieldLayout(customValue, 5, tenantId, accountId, ftc.getId());
        if(200 != data.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        return ftc.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteConfig(Integer id) {
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        FormTemplateConfig ftc = new FormTemplateConfig();
        ftc.setId(id);
        ftc.setDeleteFlag(true);
        ftc.setTenantId(tenantId);
        ftc.setUpdateBy(accountId);
        ftc.setUpdateTime(System.currentTimeMillis());
        ftConfigMapper.updateByPrimaryKeySelective(ftc);
        Example example = new Example(ChargeClause.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateId",id);
        criteria.andEqualTo("deleteFlag",false);
        List<ChargeClause> chargeClauses = chargeClauseMapper.selectByExample(example);
        for (ChargeClause chargeClause : chargeClauses) {
            chargeClause.setDeleteFlag(true);
            chargeClauseMapper.updateByPrimaryKeySelective(chargeClause);
        }
        return 1;
    }

    @Override
    public List<SimpTemplateDto> getConfigsByBuilds(List<Integer> ids) {
        Integer tenantId = ThreadLocalUtil.getTenantId();
        List<SimpTemplateDto> list =  ftConfigMapper.getAllConfig(tenantId);
        Iterator<SimpTemplateDto> iterator = list.iterator();
        while (iterator.hasNext()){
            boolean flag = false;
            SimpTemplateDto next = iterator.next();
            for (Integer id : ids) {
                String[] split = next.getBuildings().split(",");
                List<String> strings = Arrays.asList(split);
                if(strings.contains(id.toString())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                iterator.remove();
            }
        }
        return list;
    }

    @Override
    public FormTemplateConfigDto getBaseConfig(Integer id) {
        Integer accountId = ThreadLocalUtil.getAccountId();
        Integer tenantId = ThreadLocalUtil.getTenantId();
        FormTemplateConfigDto ftcd = new FormTemplateConfigDto();
        FormTemplateConfig ftc = ftConfigMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(ftc,ftcd);
        JsonResult<List<SelfField>> fieldLayout = oaClient.getFieldLayout(5, tenantId,accountId, ftc.getId());
        if(200 != fieldLayout.getCode()){
            throw new MessageException("调用暴露接口失败");
        }
        ftcd.setCustomValue(fieldLayout.getData());
        return ftcd;
    }

    @Override
    public List<ChargeClauseDto> getChargeConfig(Integer id, Date startTime, Date endTime) {
        Example example = new Example(ChargeClause.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateId",id);
        criteria.andEqualTo("deleteFlag",false);
        List<ChargeClause> chargeClauses = chargeClauseMapper.selectByExample(example);
        List<ChargeClauseDto> ccds = new ArrayList<>();
        for (ChargeClause chargeClause : chargeClauses) {
            ChargeClauseDto ccd = new ChargeClauseDto();
            BeanUtils.copyProperties(chargeClause,ccd);
            ccd.setClauses(JSON.parseArray(chargeClause.getClause(), Clause.class));
            List<IncreaseClause> increaseClauses = JSON.parseArray(chargeClause.getIncreaseClause(), IncreaseClause.class);
            List<IncreaseClause> ics = new ArrayList<>();
            if(0<increaseClauses.size()) {
                Integer increaseSpace = increaseClauses.get(0).getIncreaseSpace();
                Calendar instance = Calendar.getInstance();
                instance.setTime(startTime);
                instance.add(Calendar.YEAR,increaseSpace);
                Date time = instance.getTime();
                while (0>time.compareTo(endTime)){
                    IncreaseClause increaseClause = new IncreaseClause();
                    BeanUtils.copyProperties(increaseClauses.get(0),increaseClause);
                    increaseClause.setIncreaseTime(time);
                    ics.add(increaseClause);
                    instance.setTime(time);
                    instance.add(Calendar.YEAR,increaseSpace);
                    time = instance.getTime();
                }
            }
            ccd.setIncreaseClauses(ics);
            ccd.setPreferenceClauses(JSON.parseArray(chargeClause.getPreferenceClause(),PreferenceClause.class));
            ccd.setOtherClauses(JSON.parseArray(chargeClause.getOtherClause(),Clause.class));
            ccds.add(ccd);
        }
        return ccds;
    }
}
*/
