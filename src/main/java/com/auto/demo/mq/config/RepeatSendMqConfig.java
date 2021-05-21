package com.auto.demo.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Administrator
 */
@Slf4j
@Configuration
public class RepeatSendMqConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback  {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        // 指定confirmCallback
        rabbitTemplate.setConfirmCallback(this);
        // 指定returnCallback
        rabbitTemplate.setReturnCallback(this);

        rabbitTemplate.setMandatory(true);
    }

    public static final String REPEAT_QUEUE = "repeat_queue";

    public static final String REPEAT_EXCHANGE = "repeat_exchange";

    public static final String REPEAT_ROUTING = "repeat_routing";

    @Bean
    public Queue repeatQueue(){
        return new Queue(REPEAT_QUEUE,true);
    }

/*    @Bean
    public CustomExchange customExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange(REPEAT_EXCHANGE,"x-delayed-message",true,false,args);
    }

    @Bean
    public Binding repeatBinding(Queue repeatQueue, CustomExchange customExchange){
        return BindingBuilder.bind(repeatQueue).to(customExchange).with(REPEAT_ROUTING).noargs();
    }*/

    @Bean
    public DirectExchange repeatExchange(){
        return new DirectExchange(REPEAT_EXCHANGE,true,false);
    }

    @Bean
    public Binding repeatBinding(Queue repeatQueue, DirectExchange repeatExchange){
        return BindingBuilder.bind(repeatQueue).to(repeatExchange).with(REPEAT_ROUTING);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // spring.rabbitmq.publisher-confirm-type: true
        // ack true 成功 false 失败
        if(ack){
            correlationData = null;
            log.info("消息成功发送到路由器");
        }else{
            log.info("消息唯一标识 {}",correlationData);
            log.info("确认结果 {}",ack);
            log.info("失败原因 {}",cause);

        }

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        //    template:
        //      mandatory: true
        //    publisher-returns: true
        // exchange-->queue失败，回调该方法
        log.info("消息 exchange-->queue失败");
        log.info("消息主体 message {}",message);
        log.info("回应码 replyCode {}",replyCode);
        log.info("回应消息 {}",replyText);
        log.info("消息使用的交换器 exchange {}",exchange);
        log.info("消息使用的路由键 routing {}",routingKey);

        // 解析message 获取消息，进行重发
    }
}
