package com.peppa.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * 屏蔽内部微服务接口
 */
@Component
@Slf4j
public class BlockFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = getOriginalPath(exchange);
        // path可以拿到服务名称，如访问provider的test接口，则输出 req path : /provider/test
        log.info("filter() req path : " + path);

        if ("/provider/block".equalsIgnoreCase(path)) {
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            byte[] bytes = "UNAUTHORIZED".getBytes();
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
        return chain.filter(exchange);
    }

    private String getOriginalPath(ServerWebExchange exchange) {
        String defaultPath = exchange.getRequest().getPath().value();
        LinkedHashSet<URI> uris = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        if (uris == null || uris.isEmpty()) {
            return defaultPath;
        }
        Iterator<URI> iterator = uris.iterator();
        URI originURI = iterator.next();
        return originURI.getPath();
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
