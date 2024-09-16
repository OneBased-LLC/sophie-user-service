package com.example.springbootgithubactiondemo.controller;

import com.example.springbootgithubactiondemo.DatabaseConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.springbootgithubactiondemo.Utils.*;

@RestController
@RequestMapping("/api/v1/")
public class HomeController {

    @Autowired
    private final MongoClient mongoClient;

    @Autowired
    public HomeController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Autowired
    private DatabaseConfig databaseProperties;


    @GetMapping("home")
    public String index(){
        return "OneBased Backend Service home endpoint running as expected.";
    }

    @Operation(
            summary = "Submit data",
            description = "Tests submitting to the database from MongoDB"
    )
    @PostMapping("/submit")
    public String submitData(@RequestBody String data) {
        System.out.println("=> Connection successful: " + preFlightChecks(mongoClient));
        System.out.println("=> Print list of databases:");
        List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
        databases.forEach(db -> System.out.println(db.toJson()));
        return "Received data: " + data;
    }
}
