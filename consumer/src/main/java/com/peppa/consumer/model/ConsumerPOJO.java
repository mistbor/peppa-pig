package com.peppa.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerPOJO {
    private Long id;
    private String name;
    private Integer port;
    private ProviderPOJO providerPOJO;
}
