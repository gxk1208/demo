package com.auto.demo.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.auto.demo.entity.ServrMonitor.Sys;

import com.auto.demo.mq.config.RepeatSendMqConfig;
import com.auto.demo.service.EasyService;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/9/17 15:55
 */
@Slf4j
@Service
public class EasyServiceImpl implements EasyService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String test() {
        return "1";
    }

    @Override
    public String imgCode(HttpServerRequest request, HttpServerResponse response) {

        return null;
    }

    @Override
    public Integer repeat(String msg, Integer tenantId) {
        rabbitTemplate.convertAndSend(RepeatSendMqConfig.REPEAT_EXCHANGE,RepeatSendMqConfig.REPEAT_ROUTING,msg, message -> {
            log.info("send time : {}", System.currentTimeMillis());
            return message;
        });
        return null;
    }

}
