package io.github.alinacozy.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Role;
import org.springframework.kafka.core.KafkaTemplate;

import io.github.alinacozy.audit.AuditAspect;

@Configuration
@EnableAspectJAutoProxy
public class AuditAutoConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AuditAspect auditAspect(KafkaTemplate<String, String> kafkaTemplate) {
        return new AuditAspect(kafkaTemplate);
    }
}