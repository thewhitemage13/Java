package org.example.hw1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Hw1Application {
    public static void main(String[] args) {
        SpringApplication.run(Hw1Application.class, args);
    }
}

@Aspect
@Component
class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private final Map<String, AtomicInteger> methodCounters = new ConcurrentHashMap<>();

    @Around("execution(* org.example.hw1.HelloController.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        int currentCount = methodCounters
                .computeIfAbsent(methodName, key -> new AtomicInteger(0))
                .incrementAndGet();

        long startTime = System.nanoTime();

        logger.info("Заходимо в метод: {}", methodName);
        logger.info("Кількість заходів у метод {}: {}", methodName, currentCount);

        try {
            Object result = joinPoint.proceed();

            long endTime = System.nanoTime();
            long executionTimeMs = (endTime - startTime) / 1_000_000;

            logger.info("Виходимо з методу: {}", methodName);
            logger.info("Час виконання методу {}: {} мс", methodName, executionTimeMs);

            return result;
        } catch (Throwable ex) {
            long endTime = System.nanoTime();
            long executionTimeMs = (endTime - startTime) / 1_000_000;

            logger.error("Помилка в методі {}. Час до помилки: {} мс", methodName, executionTimeMs);
            throw ex;
        }
    }
}

@RestController
class HelloController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello Spring with AOP!";
    }

    @GetMapping("/test")
    public String test() throws InterruptedException {
        Thread.sleep(150);
        return "Test endpoint";
    }
}

@Component
class BrowserLauncher {
    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        System.setProperty("java.awt.headless", "false");
        var desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("http://localhost:8080"));
        } catch (IOException | URISyntaxException e) {
        }
    }
}