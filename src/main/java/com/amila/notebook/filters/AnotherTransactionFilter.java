package com.amila.notebook.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class AnotherTransactionFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(AnotherTransactionFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Starting another transaction for req : {}", req.getRequestURI());

        chain.doFilter(request, response);
        logger.info("Committing another transaction for req : {}", req.getRequestURI());
    }
}
