package com.hourglass.order.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class TicketGenerateExecutor {
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            0, 4,
            30, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(16),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

//    public static void execute() {
//        executor.execute();
//    }
}
