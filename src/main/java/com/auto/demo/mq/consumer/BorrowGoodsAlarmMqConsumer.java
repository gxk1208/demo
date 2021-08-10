package com.auto.demo.mq.consumer;


import com.alibaba.fastjson.JSON;
import com.auto.demo.mq.config.BorrowGoodsAlarmConfig;
import com.auto.demo.param.IdParam;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/11/19 16:02
 */
@Component
@Slf4j
@EnableRabbit
public class BorrowGoodsAlarmMqConsumer {

//    @RabbitListener(queues = BorrowGoodsAlarmConfig.ALARM_QUEUE)
//    @RabbitHandler
    public void receiveMessage(@Payload IdParam message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {

        channel.basicAck(tag, true);
//
    }
}
