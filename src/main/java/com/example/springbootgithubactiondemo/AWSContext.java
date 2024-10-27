package com.example.springbootgithubactiondemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class AWSContext {

    @Bean
    public CognitoIdentityProviderClient getActiveCognitoClient() {
        return initializeClient();
    }

    public CognitoIdentityProviderClient initializeClient() {
        String accessKey = "";
        String secretKey = "";
        return CognitoIdentityProviderClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    };
}
