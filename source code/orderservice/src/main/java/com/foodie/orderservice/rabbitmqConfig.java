package com.foodie.orderservice;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitmqConfig {

    @Bean
    protected Queue quene()
    {
        Queue queue = new Queue("myQueue");
        return queue;
    }


    @Bean
    public Queue createQueue1()
    {
        return new Queue("myfanout1");
    }

    @Bean
    public Queue createQueue2()
    {
        return new Queue("myfanout2");
    }


    @Bean
    public FanoutExchange getFanoutExchange()
    {
        return new FanoutExchange("amq.fanout");
    }


    @Bean
    public Binding bingding(Queue createQueue1 , FanoutExchange getFanoutExchange)
    {
        return BindingBuilder.bind(createQueue1).to(getFanoutExchange);
    }

    @Bean
    public Binding binding2(Queue createQueue2 , FanoutExchange getFanoutExchange)
    {
        return BindingBuilder.bind(createQueue2).to(getFanoutExchange);

    }


    @Bean
    public Queue topicQueue1()
    {
        return new Queue("topic1");

    }

    @Bean
    public Queue topicQueue2()
    {
        return new Queue("topic2");

    }


    @Bean
    public TopicExchange topicExchange()
    {
        return new TopicExchange("amq.topic");
    }


    @Bean
    public Binding topicBinding(Queue topicQueue1 , TopicExchange topicExchange)
    {
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with("com.msb.*");
    }


    @Bean
    public Binding topicBinding2(Queue topicQueue2 , TopicExchange topicExchange)
    {
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("com.msb.#");
    }




}
