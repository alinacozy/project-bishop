package io.github.alinacozy.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.alinacozy.exception.CustomExceptionHandler;
import io.github.alinacozy.service.CommandService;

@Configuration
@ConditionalOnClass(CommandService.class)
public class MyAutoConfiguration {

    @Bean
    public CommandService commandService() {
        return new CommandService();
    }

    @Bean
    public CustomExceptionHandler customExceptionHandler(){
        return new CustomExceptionHandler();
    }
}