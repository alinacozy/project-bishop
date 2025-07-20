package io.github.alinacozy.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    private final MeterRegistry meterRegistry;
    private static final String QUEUE_SIZE_METRIC = "bishop.queue.size";
    private static final String COMPLETED_TASKS_METRIC = "bishop.completed.tasks";

    private final AtomicInteger queueSize = new AtomicInteger(0);

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // Регистрируем Gauge, привязанный к queueSize
        Gauge.builder(QUEUE_SIZE_METRIC, queueSize, AtomicInteger::get)
             .register(meterRegistry);
    }

    public void updateQueueSize(int size) {
        queueSize.set(size);
    }

    public void incrementCompletedTasks(String author) {
        meterRegistry.counter(COMPLETED_TASKS_METRIC, "author", author).increment();
    }
}
