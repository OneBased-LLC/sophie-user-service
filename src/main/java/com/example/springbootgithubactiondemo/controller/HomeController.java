package com.example.springbootgithubactiondemo.controller;

import com.example.springbootgithubactiondemo.DatabaseConfig;
import com.example.springbootgithubactiondemo.ListUserPools;
import com.example.springbootgithubactiondemo.LoginUser;
import com.example.springbootgithubactiondemo.repository.UserRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import java.util.ArrayList;
import java.util.List;

import static com.example.springbootgithubactiondemo.Utils.*;

@RestController
@RequestMapping("/api/v1/")
public class HomeController {

    @Autowired
    private final MongoClient mongoClient;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CognitoIdentityProviderClient cognitoClient;

    @Autowired
    public HomeController(MongoClient mongoClient, UserRepository userRepository, CognitoIdentityProviderClient cognitoClient) {
        this.mongoClient = mongoClient;
        this.userRepository = userRepository;
        this.cognitoClient = cognitoClient;
    }

    @Autowired
    private DatabaseConfig databaseProperties;

    @GetMapping("home")
    public String index(){
        ListUserPools userPoolsAWS = new ListUserPools();
        ListUserPools.listAllUserPools(cognitoClient);
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

    @Operation(
            summary = "Login",
            description = "Authenticate to SOPHIE 2.0"
    )
    @PostMapping("/login")
    public String loginUser(@RequestBody String data) {
        String userPoolClientId = "4t08ueomq1j8ava2ehnh1vujlt";
        LoginUser.initiateAuth(cognitoClient, userPoolClientId,
        return "Received data: " + data;
    }
}
