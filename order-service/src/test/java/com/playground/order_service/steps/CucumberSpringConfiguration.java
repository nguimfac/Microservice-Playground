package com.playground.order_service.steps;

import com.playground.order_service.service.facade.feignClient.InventoryClient;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@CucumberContextConfiguration
@SpringBootTest()
@ActiveProfiles("test")
@AutoConfigureMockMvc 
class CucumberSpringConfiguration {

    @MockitoBean
    private InventoryClient inventoryClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // Si ce test passe, tout est bon.
    }



}

