package com.auto.demo.mq.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/4/7 11:05
 */
@Configuration
public class TestMessageConfig {

    public static final String TEST_EXCHANGE = "test_exchange";

    public static final String TEST_QUEUE = "test_queue";

    public static final String TEST_ROUTE = "test_route";

    @Bean
    public Queue appMessageQueue(){
        return new Queue(TEST_QUEUE,true);
    }

    @Bean
    public DirectExchange appMessageExchange(){
        return new DirectExchange(TEST_EXCHANGE,true,false);
    }

    @Bean
    public Binding bindAppMessage(Queue appMessageQueue, DirectExchange appMessageExchange){
        return BindingBuilder.bind(appMessageQueue).to(appMessageExchange).with(TEST_ROUTE);
    }


}
