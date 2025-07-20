package io.github.alinacozy.audit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@Aspect
public class AuditAspect {

    private KafkaTemplate<String, String> kafkaTemplate;

    public AuditAspect() {
    }

    @Autowired(required = false)
    public AuditAspect(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(WeylandWatchingYou)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint, WeylandWatchingYou weylandWatchingYou) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();

        String logMessage = String.format(
                "{\"method\":\"%s\", \"args\":%s, \"result\":%s}",
                methodName,
                convertToJson(args),
                convertToJson(result));

        
        if (!weylandWatchingYou.kafkaTopic().isEmpty() && kafkaTemplate != null) {
            try {
                kafkaTemplate.send(
                        weylandWatchingYou.kafkaTopic(),
                        logMessage);
            } catch (Exception e) {
                System.err.println("[AUDIT] Kafka error: " + e.getMessage());
            }
        }
        else {
            System.out.println("[AUDIT] " + logMessage);
        }

        return result;
    }

    private String convertToJson(Object obj) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
