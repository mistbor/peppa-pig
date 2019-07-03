package com.peppa.provider.demo;

import com.peppa.provider.demo.controller.ProviderController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;

public class ProviderBaseTest {
    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new ProviderController());
    }
}