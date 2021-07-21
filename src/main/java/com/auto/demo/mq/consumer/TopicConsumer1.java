package com.auto.demo.mq.consumer;

import com.auto.demo.entity.SelfEntity;
import com.auto.demo.mq.config.TestMessageConfig;
import com.auto.demo.mq.config.TopicTestMqConfig;
import com.rabbitmq.client.Channel;
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
 * @date 2021/5/21 16:43
 */
@Slf4j
@Configuration
@EnableRabbit
public class TopicConsumer1 {

    @RabbitHandler
    @RabbitListener(queues = TopicTestMqConfig.TOPIC_TEST_QUEUE1)
    public void receiveMessage(@Payload String se, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)  long tag) throws IOException {
        log.info("接收到消息 home {}",se);

        channel.basicAck(tag,true);
    }
}
