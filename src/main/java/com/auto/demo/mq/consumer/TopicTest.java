package com.auto.demo.mq.consumer;

import com.auto.demo.mq.config.TopicTestMqConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/4/12 10:10
 */
@Configuration
public class TopicTest {

    private  Logger logger = LoggerFactory.getLogger(TopicTest.class);

    private static RabbitTemplate rabbitTemplate1 = new RabbitTemplate();

    @Autowired
    private  RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate1  = this.rabbitTemplate;
    }

    public static synchronized void send(String message, Integer tenantId){

        // topic MQ
        HashMap<String, Object> map = new HashMap<>();
        map.put("message",message);
        map.put("tenantId",tenantId);
        ConnectionFactory connectionFactory;
        RabbitTemplate rabbitTemplate2 = new RabbitTemplate();
        rabbitTemplate1.convertAndSend(TopicTestMqConfig.FANOUT_WS_EXCHANGE,"",map);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(), exchange = @Exchange(value = TopicTestMqConfig.FANOUT_WS_EXCHANGE,type = ExchangeTypes.FANOUT)
    ))
    @RabbitHandler
    public void userPassMqConsumer(@Payload Map mapMessage , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        Integer tenantId = (Integer) mapMessage.get("tenantId");
        String message = (String) mapMessage.get("message");
        logger.info("租户-{}发送实时数据开始：{}", tenantId, message);
        try {
            channel.basicAck(tag,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(), exchange = @Exchange(value = TopicTestMqConfig.FANOUT_WS_EXCHANGE,type = ExchangeTypes.FANOUT)
    ))
    @RabbitHandler
    public void userPassMqConsumer1(@Payload Map mapMessage , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        Integer tenantId = (Integer) mapMessage.get("tenantId");
        String message = (String) mapMessage.get("message");
        logger.info("1租户-{}发送实时数据开始：{}", tenantId, message);
        try {
            channel.basicAck(tag,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
