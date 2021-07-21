package com.auto.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.auto.demo.dto.ChargeRuleDto;
import com.auto.demo.entity.ChargeField;
import com.auto.demo.entity.ChargeRule;
import com.auto.demo.mapper.ChargeFieldMapper;
import com.auto.demo.mapper.ChargeRuleMapper;
import com.auto.demo.service.ChargeEasyService;
import com.auto.demo.utils.Calculator02;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/7/8 15:10
 */
@Slf4j
@Service
public class ChargeEasyServiceImpl implements ChargeEasyService {

    @Autowired
    private ChargeFieldMapper chargeFieldMapper;

    @Autowired
    private ChargeRuleMapper chargeRuleMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public void saveRule(List<ChargeRuleDto> rules) {
        ChargeRule chargeRule = new ChargeRule();
        chargeRule.setRule(JSON.toJSONString(rules));



        log.info("输入规则 ：{}",chargeRule.getRule());
        chargeRuleMapper.insert(chargeRule);
    }

    @Override
    public void calculatorRule(Integer ruleId) {
        ChargeRule chargeRule = chargeRuleMapper.selectByPrimaryKey(ruleId);
        List<ChargeRuleDto> rules = JSON.parseArray(chargeRule.getRule(), ChargeRuleDto.class);
        StringBuilder str = new StringBuilder();
        for (ChargeRuleDto rule : rules) {
            switch (rule.getType()) {
                case 1:
                    ChargeField chargeField = chargeFieldMapper.selectByPrimaryKey(rule.getValue());
                    Long aLong = jdbcTemplate.queryForObject(chargeField.getMean(), Long.class, chargeField.getInParam());
                    str.append(aLong);
                    break;
                case 2:
                case 3:
                    // 运算符直接拼接
                    str.append(rule.getValue());
                    break;
                default:
                    break;
            }

        }
        String s = str.toString();
        log.info("自定义收费计算公式 {}",s);
        Calculator02 calculator02 = new Calculator02();
        BigDecimal caculate = calculator02.caculate(s);
        log.info("自定义收费计算结果 {}",caculate);
    }
}
