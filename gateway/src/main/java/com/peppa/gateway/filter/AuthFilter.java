package com.peppa.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 请求白名单
     */
    private static List<String> WHITE_LIST_URLS = new ArrayList<String>(Arrays.asList("/provider/login"));


    /**
     * 不是白名单url，且没有传递http header: X-TOKEN ,就会被拦截
     * 白名单url是： 数组WHITE_LIST_URLS，目前仅有/provider/login
     * <p>
     * 通过命令行curl访问，或者使用postman等HTTP工具来实现发送带自定义header的请求
     * curl -H "X-TOKEN: XXXX" http://localhost:9999/provider/test  正常返回
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = getOriginalPath(exchange);
        log.info("filter() req path: " + path);

        if (WHITE_LIST_URLS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();

        if (!headers.containsKey("X-TOKEN")) {
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
            byte[] bytes = "Bad Request".getBytes();
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
        // String token = headers.getFirst("X-TOKEN");
        return chain.filter(exchange);
    }

    private String getOriginalPath(ServerWebExchange exchange) {
        String defaultPath = exchange.getRequest().getPath().value();
        LinkedHashSet<URI> uris = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        if (uris == null || uris.isEmpty()) {
            return defaultPath;
        }
        Iterator<URI> iterator = uris.iterator();
        URI originURL = iterator.next();
        return originURL.getPath();
    }

    @Override
    public int getOrder() {
        return 200;
    }
}
