package com.example.springbootgithubactiondemo;

import com.example.springbootgithubactiondemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class OneBasedServiceSpringApplication {

    @Autowired
    private UserRepository repository;


    public static void main(String[] args) {
        SpringApplication.run(OneBasedServiceSpringApplication.class, args);
    }

}
