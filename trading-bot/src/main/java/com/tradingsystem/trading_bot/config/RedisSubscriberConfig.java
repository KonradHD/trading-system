package com.tradingsystem.trading_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.tradingsystem.trading_bot.service.RedisListenerService;

@Configuration
public class RedisSubscriberConfig {

    @Value("${redis.bot.active-users.endpoint}")
    private String ACTIVE_CHANNEL_NAME;

    @Bean
    public MessageListenerAdapter listenerAdapter(RedisListenerService receiver) {
        return new MessageListenerAdapter(receiver, "handleMessage");
    }

    @Bean
    public RedisMessageListenerContainer container(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        
        container.addMessageListener(listenerAdapter, new ChannelTopic(ACTIVE_CHANNEL_NAME));
        
        return container;
    }
}
