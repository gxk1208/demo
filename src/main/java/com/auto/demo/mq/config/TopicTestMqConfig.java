package com.auto.demo.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/5/21 16:04
 */
@Slf4j
@Configuration
public class TopicTestMqConfig {

    public static final String TOPIC_TEST_EXCHANGE = "topic_test_exchange";
    public static final String TOPIC_TEST_EXCHANGE_ROUTE = "topic.route";

    public static final String TOPIC_TEST_QUEUE1 = "topic.test.queue1";

    public static final String TOPIC_TEST_QUEUE2 = "topic.test.queue2";

    public static final String TOPIC_TEST_ROUTE1 = "topic.*";
    public static final String TOPIC_TEST_ROUTE2 = "topic.*";

    @Bean
    public Queue topicTestQueue1(){
        return new Queue(TOPIC_TEST_QUEUE1,true);
    }

    @Bean
    public Queue topicTestQueue2(){
        return new Queue(TOPIC_TEST_QUEUE2,true);
    }

    @Bean
    public TopicExchange topicTestExchange(){
        return new TopicExchange(TOPIC_TEST_EXCHANGE);
    }

    @Bean
    public Binding bindTopicTest1(Queue topicTestQueue1, TopicExchange topicTestExchange){
        return BindingBuilder.bind(topicTestQueue1).to(topicTestExchange).with(TOPIC_TEST_ROUTE1);
    }

    @Bean
    public Binding bindTopicTest2(Queue topicTestQueue2, TopicExchange topicTestExchange){
        return BindingBuilder.bind(topicTestQueue2).to(topicTestExchange).with(TOPIC_TEST_ROUTE2);
    }
}
