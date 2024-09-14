package com.example.springbootgithubactiondemo;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

public class Utils {

    private Utils() {
        // Prevent instantiation
    }

    public static boolean preFlightChecks(MongoClient mongoClient) {
        Document pingCommand = new Document("ping", 1);
        Document response = mongoClient.getDatabase("admin").runCommand(pingCommand);
        System.out.println("=> Print result of the '{ping: 1}' command.");
        System.out.println(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.get("ok", Number.class).intValue() == 1;
    }
}
