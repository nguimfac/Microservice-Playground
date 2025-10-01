package com.playground.payment_web.steps;

import org.springframework.boot.test.context.SpringBootTest;

import com.playground.payment_web.PaymentWebApplication;
import com.playground.payment_web.config.TestConfig;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(
    classes = {PaymentWebApplication.class, TestConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
public class CucumberSpringContextConfig {
    
}

