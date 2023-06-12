package com.gungor.ska.intercept;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(
        value="metric.interceptor",
        havingValue = "enabled",
        matchIfMissing = true)
public class InterceptorConfig implements WebMvcConfigurer {

    private MetricInterceptor metricInterceptor;

    public InterceptorConfig(MetricInterceptor myInterceptor) {
        this.metricInterceptor = myInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(metricInterceptor);
    }
}