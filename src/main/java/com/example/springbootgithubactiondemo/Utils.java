package com.example.springbootgithubactiondemo;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

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

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String username) throws Exception {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        SecretKeySpec signingKey = new SecretKeySpec(userPoolClientSecret.getBytes(), HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal((username + userPoolClientId).getBytes());
        return Base64.getEncoder().encodeToString(rawHmac);
    }
}
