package com.arielmolina.tasksapi.infrastructure.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Component
public class LoggingRequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        long startTime = Instant.now().toEpochMilli();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        long startTime = (Long) request.getAttribute("startTime");

        logger.info(", url=" + request.getRequestURL() +
                ", time=" + (Instant.now().toEpochMilli() - startTime) +
                ", uri=" + request.getRequestURI());
    }
}