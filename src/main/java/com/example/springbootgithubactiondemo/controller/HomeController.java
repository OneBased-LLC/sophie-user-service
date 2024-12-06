package com.example.springbootgithubactiondemo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.example.springbootgithubactiondemo.AwsCognitoRSAKeyProvider;
import com.example.springbootgithubactiondemo.DatabaseConfig;
import com.example.springbootgithubactiondemo.ListUserPools;
import com.example.springbootgithubactiondemo.LoginUser;
import com.example.springbootgithubactiondemo.repository.UserRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    private Environment env;

    @Autowired
    public HomeController(MongoClient mongoClient, UserRepository userRepository, CognitoIdentityProviderClient cognitoClient) {
        this.mongoClient = mongoClient;
        this.userRepository = userRepository;
        this.cognitoClient = cognitoClient;
    }

    @Autowired
    private DatabaseConfig databaseProperties;

    @GetMapping("verify")
    public String index(@RequestParam String token){
        RSAKeyProvider keyProvider = new AwsCognitoRSAKeyProvider("us-west-2", "us-west-2_Ck5A798lj");
        Algorithm algorithm = Algorithm.RSA256(keyProvider);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .build();

        return jwtVerifier.verify(token).getClaim("sub").asString();
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
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        String userPoolClientId = "4t08ueomq1j8ava2ehnh1vujlt";
        String userPoolClientSecret = env.getProperty("USER_POOL_SECRET");
        System.out.println(username);
        return LoginUser.initiateAuth(cognitoClient, userPoolClientId, userPoolClientSecret, username, password);
    }
}
