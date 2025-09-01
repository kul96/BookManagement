package com.example.bookManagement.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.info("Request came to filter with details : " + servletRequest);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        log.info("Filter request " + httpServletRequest.getRequestURL());

        // after completion of filter we just pass request to servlet and our work is completed
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
