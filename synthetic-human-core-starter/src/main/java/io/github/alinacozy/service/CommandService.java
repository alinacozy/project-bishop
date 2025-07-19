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

    public CommandService() {
        this.executor = new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100) // максимальное число задач в очереди
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
            } catch (RejectedExecutionException e) {
                throw new IllegalStateException("Command queue is full!");
            }
        }
    }

    private void executeCommand(Command command) {
        System.out.println("Executing: " + command.getDescription());
    }

}
