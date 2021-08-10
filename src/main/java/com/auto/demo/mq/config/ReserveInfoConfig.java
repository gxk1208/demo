package com.auto.demo.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/8/5 9:10
 */
@Configuration
public class ReserveInfoConfig {

    public static final String RESERVE_EXCHANGE = "reserve_exchange";

    public static final String RESERVE_QUEUE = "reserve_queue";

    public static final String RESERVE_ROUTE = "reserve_routing";

    @Bean
    public Queue reserveQueue(){
        return new Queue(RESERVE_QUEUE,true);
    }

    @Bean
    public CustomExchange reserveExchange(){
        HashMap<String, Object> args = new HashMap<>(1);
        args.put("x-delayed-type","direct");
        return new CustomExchange(RESERVE_EXCHANGE,"x-delayed-message",true,false,args);
    }

    @Bean
    public Binding bindReserveExchange(Queue reserveQueue, CustomExchange reserveExchange){
        return BindingBuilder.bind(reserveQueue).to(reserveExchange).with(RESERVE_ROUTE).noargs();
    }
}
