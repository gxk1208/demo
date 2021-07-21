package com.auto.demo.service;

import com.auto.demo.dto.ChargeRuleDto;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/7/8 15:10
 */
public interface ChargeEasyService {

    void saveRule(List<ChargeRuleDto> rules);

    void calculatorRule(Integer ruleId);
}
