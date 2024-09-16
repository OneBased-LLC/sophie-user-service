package com.example.springbootgithubactiondemo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class DatabaseContext extends AbstractMongoClientConfiguration {

    @Autowired
    private DatabaseConfig databaseProperties;

    @Override
    protected String getDatabaseName(){
        return "sophie";
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://" + databaseProperties.getHost() + ":" + databaseProperties.getPort());
    }
}
