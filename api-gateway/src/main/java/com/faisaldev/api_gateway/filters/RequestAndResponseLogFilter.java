package com.faisaldev.api_gateway.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class RequestAndResponseLogFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestAndResponseLogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log the incoming request
        logger.info("Incoming request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

        // Log the response once it's available
        return chain.filter(exchange).doOnSuccess(aVoid -> {
            logger.info("Outgoing response: {} {}", exchange.getResponse().getStatusCode(), exchange.getRequest().getURI());
        });
    }

    @Override
    public int getOrder() {
        return -1; // this just mean it will be called before all the other filters
    }
}
