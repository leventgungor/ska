package com.gungor.ska.intercept;

import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MetricInterceptor implements HandlerInterceptor {

    private Timer timer;

    private SimpleMeterRegistry simpleMeterRegistry;

    @Autowired
    public MetricInterceptor(SimpleMeterRegistry simpleMeterRegistry) {
        this.simpleMeterRegistry = simpleMeterRegistry;
        timer = simpleMeterRegistry.timer("controller.methods.timer");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Capture start time of API call
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Capture end time of API call and calculate response time
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Record API response time using Micrometer
        timer.record(timeTaken, TimeUnit.MILLISECONDS);

        if(timeTaken > 5) {
            log.info("API response time for path " + request.getRequestURI() + " has taken "+ timeTaken + " ms");
        }

    }
}