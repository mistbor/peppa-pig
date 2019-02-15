package com.peppa.provider.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderPOJO {
    private Long id;
    private String name;
    private Integer port;
}
