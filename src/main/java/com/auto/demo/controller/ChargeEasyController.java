package com.auto.demo.controller;

import com.auto.demo.dto.ChargeRuleDto;
import com.auto.demo.service.ChargeEasyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/7/8 15:08
 */
@Api(tags = "收费规则自定义")
@RestController
@RequestMapping(value = "/charge/easy")
public class ChargeEasyController {

    @Autowired
    private ChargeEasyService chargeEasyService;

    @PostMapping(value = "/save-rule")
    public void saveRule(@RequestBody List<ChargeRuleDto> rules){
        chargeEasyService.saveRule(rules);
    }

    @PostMapping(value = "/calculator")
    public void calculatorRule(@RequestBody Integer ruleId){
        chargeEasyService.calculatorRule(ruleId);
    }
}
