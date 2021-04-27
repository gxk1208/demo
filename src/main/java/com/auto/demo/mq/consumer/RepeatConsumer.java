package com.auto.demo.mq.consumer;

import com.auto.demo.mq.config.RepeatSendMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
@EnableRabbit
public class RepeatConsumer {

    private Integer num = 0;

    @RabbitHandler
    @RabbitListener(queues = RepeatSendMqConfig.REPEAT_QUEUE)
    public void receiverMessage(String msg, Message message,
                                Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        log.info("consumer time : {}",System.currentTimeMillis());
        Thread.sleep(1000);
        log.info("重复发送,{}",num);

        // 消息确认消费
        // channel.basicAck(tag,false);
        channel.basicNack(tag,false,false);
        num++;
    }
}
