package com.example.springbootgithubactiondemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
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
        return CognitoIdentityProviderClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(Region.US_WEST_2)
                .build();
    };
}
