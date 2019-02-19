package com.peppa.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // path不可以拿到服务名称，如访问provider的test接口，则输出 req path : /test
        log.info("filter() req path: " + request.getPath().value());

        HttpHeaders reqHeaders = request.getHeaders();
        log.info("req headers: \n" + mapToString(reqHeaders.toSingleValueMap()));

        return chain.filter(exchange).then(Mono.defer(() -> {
            log.info("filter res");
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders resHeaders = response.getHeaders();
            log.info("res headers: \n" + mapToString(resHeaders.toSingleValueMap()));
            return Mono.empty();
        }));
    }

    private String mapToString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        map.forEach((k, v) -> sb.append(k).append(":").append(v).append("\n"));
        return sb.toString();
    }

    /**
     * order值建议在1-10000之间
     * LogFilter可以设计在比较靠近10000的位置
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 9000;
    }
}