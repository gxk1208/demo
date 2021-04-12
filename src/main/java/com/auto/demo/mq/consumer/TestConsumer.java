package com.auto.demo.mq.consumer;

import com.auto.demo.entity.SelfEntity;
import com.auto.demo.mq.config.TestMessageConfig;
import com.auto.demo.utils.ThreeDES科拓.Test;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/4/7 11:14
 */
@Slf4j
@Configuration
@EnableRabbit
public class TestConsumer {

    @RabbitHandler
    @RabbitListener(queues = TestMessageConfig.TEST_QUEUE)
    public void receiveMessage(@Payload SelfEntity se, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)  long tag) throws IOException {
        log.info("接收到消息 {}",se.getName());

        channel.basicAck(tag,true);
    }

}
