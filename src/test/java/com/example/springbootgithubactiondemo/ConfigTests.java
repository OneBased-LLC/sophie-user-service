package com.example.springbootgithubactiondemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfigTests {

    @Autowired
    private DatabaseConfig databaseProperties;

    @Test
    void getDatabaseConfig() {
        assertNotNull(databaseProperties.getHost());
        assertNotNull(databaseProperties.getPort());
    }
}
