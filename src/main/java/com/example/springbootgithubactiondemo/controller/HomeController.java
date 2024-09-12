package com.example.springbootgithubactiondemo.controller;

import com.example.springbootgithubactiondemo.DatabaseConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class HomeController {

    @Autowired
    private DatabaseConfig databaseProperties;


    static boolean preFlightChecks(MongoClient mongoClient) {
        Document pingCommand = new Document("ping", 1);
        Document response = mongoClient.getDatabase("admin").runCommand(pingCommand);
        System.out.println("=> Print result of the '{ping: 1}' command.");
        System.out.println(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.get("ok", Number.class).intValue() == 1;
    }

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
        String connectionString = "mongodb://" + databaseProperties.getHost() + ":" + databaseProperties.getPort();
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            System.out.println("=> Connection successful: " + preFlightChecks(mongoClient));
            System.out.println("=> Print list of databases:");

            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
        }
        return "Received data: " + data;
    }
}
