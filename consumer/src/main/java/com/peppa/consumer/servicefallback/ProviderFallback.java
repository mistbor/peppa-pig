package com.peppa.consumer.servicefallback;


import com.peppa.consumer.model.ProviderPOJO;
import com.peppa.consumer.service.ProviderService;
import org.springframework.stereotype.Component;

/**
 * fallback 就是一种降级实现，在方法中不根据实际情况返回别的默认值或者错误信息
 */
@Component
public class ProviderFallback implements ProviderService {
    @Override
    public ProviderPOJO test(String name) {
        return null;
    }
}
