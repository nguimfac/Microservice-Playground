package com.playground.payment_web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.context.WebApplicationContext;

@TestConfiguration
public class TestConfig {
    
    @Bean
    public MockMvcTester mockMvcTester(@Autowired WebApplicationContext context) {
        return MockMvcTester.from(context);
    }
}
