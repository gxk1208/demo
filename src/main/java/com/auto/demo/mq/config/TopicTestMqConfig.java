package com.auto.demo.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/4/12 9:47
 */
@Configuration
public class TopicTestMqConfig {

    public static final String FANOUT_WS_EXCHANGE = "fanout_test_exchange";

    @Bean(name = FANOUT_WS_EXCHANGE)
    public FanoutExchange phonicExchange(){
        return new FanoutExchange(FANOUT_WS_EXCHANGE,true,false);
    }
}
