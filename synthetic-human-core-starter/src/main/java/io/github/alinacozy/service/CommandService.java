package io.github.alinacozy.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import io.github.alinacozy.model.Command;
import io.github.alinacozy.model.Priority;

@Service
public class CommandService {
    private final ThreadPoolExecutor executor;
    private final MetricsService metricsService;

    public CommandService(MetricsService metricsService) {
        this.metricsService = metricsService;
        this.executor = new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100)
        );
    }

    public void proccessCommand(Command command) {
        if (command.getPriority() == Priority.CRITICAL) {
            new Thread(() -> executeCommand(command)).start();
        } else {
            try {
                if (executor.getQueue().remainingCapacity() == 0) {
                    throw new IllegalStateException("Command queue is full!");
                }
                executor.execute(() -> executeCommand(command));
                metricsService.updateQueueSize(executor.getQueue().size());
            } catch (RejectedExecutionException e) {
                throw new IllegalStateException("Command queue is full!");
            }
        }
    }

    private void executeCommand(Command command) {
        System.out.println("Executing: " + command.getDescription());
        metricsService.updateQueueSize(executor.getQueue().size());
        metricsService.incrementCompletedTasks(command.getAuthor());
    }

}
