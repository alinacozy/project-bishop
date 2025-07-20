package io.github.alinacozy.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.alinacozy.exception.CustomExceptionHandler;
import io.github.alinacozy.service.CommandService;
import io.github.alinacozy.service.MetricsService;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
@ConditionalOnClass(CommandService.class)
public class MyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MetricsService metricsService(MeterRegistry meterRegistry) {
        return new MetricsService(meterRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandService commandService(MetricsService metricsService) {
        return new CommandService(metricsService);
    }

    @Bean
    public CustomExceptionHandler customExceptionHandler(){
        return new CustomExceptionHandler();
    }
}