package com.playground.order_service.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@CucumberContextConfiguration
@SpringBootTest()
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

    @Test
    void contextLoads() {
        // Si ce test passe, tout est bon.
    }


}

