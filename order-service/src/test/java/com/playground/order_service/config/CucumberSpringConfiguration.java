package com.playground.order_service.config;


import com.playground.order_service.CartStrategy;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@CucumberContextConfiguration
@WebMvcTest(CartC.class)  // Plus léger que @SpringBootTest !
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

    @MockitoBean
    private CartStrategy cartStrategy;
}

