package com.auto.demo.mq.consumer;

import com.auto.demo.mq.config.FanoutTestMqConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/4/12 10:10
 */
@Configuration
public class FanoutTest {

    private  Logger logger = LoggerFactory.getLogger(FanoutTest.class);

    private static RabbitTemplate rabbitTemplate;


/*    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static TopicTest topicTest;

    @PostConstruct
    public void init(){
        topicTest = this;
        topicTest.rabbitTemplate = this.rabbitTemplate;
    }*/

    @Autowired
    public FanoutTest(RabbitTemplate rabbitTemplate){
        FanoutTest.rabbitTemplate = rabbitTemplate;
    }


    public static synchronized void send(String message, Integer tenantId){

        // topic MQ
        HashMap<String, Object> map = new HashMap<>();
        map.put("message",message);
        map.put("tenantId",tenantId);
        rabbitTemplate.convertAndSend(FanoutTestMqConfig.FANOUT_WS_EXCHANGE,"",map);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(), exchange = @Exchange(value = FanoutTestMqConfig.FANOUT_WS_EXCHANGE,type = ExchangeTypes.FANOUT)
    ))
    @RabbitHandler
    public void userPassMqConsumer(@Payload Map mapMessage , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        Integer tenantId = (Integer) mapMessage.get("tenantId");
        String message = (String) mapMessage.get("message");
        logger.info("租户-{}接收实时数据开始：{}", tenantId, message);
        try {
            channel.basicAck(tag,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(), exchange = @Exchange(value = FanoutTestMqConfig.FANOUT_WS_EXCHANGE,type = ExchangeTypes.FANOUT)
    ))
    @RabbitHandler
    public void userPassMqConsumer1(@Payload Map mapMessage , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        Integer tenantId = (Integer) mapMessage.get("tenantId");
        String message = (String) mapMessage.get("message");
        logger.info("1租户-{}接收实时数据开始：{}", tenantId, message);
        try {
            channel.basicAck(tag,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
