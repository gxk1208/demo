package com.auto.demo.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author gxk
 * @version 1.0
 * @date 2020/11/19 15:38
 */
@Configuration
public class BorrowGoodsAlarmConfig {

    public static final String ALARM_EXCHANGE = "alarm_exchange";

    public static final String ALARM_QUEUE = "alarm_queue";

    public static final String ALARM_ROUTE = "alarm_routing";

    @Bean
    public Queue borrowAlarmQueue(){
        return new Queue(BorrowGoodsAlarmConfig.ALARM_QUEUE,true);
    }

    @Bean
    public CustomExchange borrowAlarmExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(BorrowGoodsAlarmConfig.ALARM_EXCHANGE,"x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingAlarmExchangeMessage(Queue borrowAlarmQueue, CustomExchange borrowAlarmExchange) {
        return BindingBuilder.bind(borrowAlarmQueue).to(borrowAlarmExchange).with(ALARM_ROUTE).noargs();
    }
}
