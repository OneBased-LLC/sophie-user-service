package com.example.springbootgithubactiondemo;

import com.example.springbootgithubactiondemo.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootGithubActionDemoApplicationTests {

    @Autowired
    private HomeController controller;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void contextLoads() {
    }

    @Test
    void getHomeController() throws Exception {
        // perform request and verify status
        mockMvc.perform(get("/api/v1/home"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("OneBased Backend Service home endpoint running as expected."));
    }

}
