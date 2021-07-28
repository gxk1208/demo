package com.auto.demo.service.impl;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.auto.demo.entity.SelfEntity;
import com.auto.demo.mapper.SelfEntityMapper;
import com.auto.demo.mq.config.RepeatSendMqConfig;
import com.auto.demo.mq.config.TopicTestMqConfig;
import com.auto.demo.service.EasyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private SelfEntityMapper selfEntityMapper;


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
        CorrelationData data = new CorrelationData(msg);
        rabbitTemplate.convertAndSend(RepeatSendMqConfig.REPEAT_EXCHANGE,RepeatSendMqConfig.REPEAT_ROUTING,msg, message -> {
            log.info("send time : {}", System.currentTimeMillis());
            return message;
        },data);
        return 1;
    }

    @Override
    public void topicTest(String message) {
        rabbitTemplate.convertAndSend(TopicTestMqConfig.TOPIC_TEST_EXCHANGE,TopicTestMqConfig.TOPIC_TEST_EXCHANGE_ROUTE,message);
    }

    @Override
    public Map<String, String> easyMap(Object test) {
        List<Map<String, String>> map =  selfEntityMapper.getEasyMap(test);
        return null;
    }
}
