package com.example.springbootgithubactiondemo.repository;

import com.example.springbootgithubactiondemo.Utils;
import com.example.springbootgithubactiondemo.user.User;
import com.example.springbootgithubactiondemo.Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    @Autowired
    private final CognitoIdentityProviderClient cognitoClient;

    public UserEventHandler (CognitoIdentityProviderClient cognitoClient){
        this.cognitoClient = cognitoClient;
    }

    @HandleBeforeCreate
    public void handleUserBeforeCreate(User user) throws Exception {

        String userPoolClientId = "4t08ueomq1j8ava2ehnh1vujlt";
        String userPoolClientSecret = "1ubg7lnjktnc6kft1kku5cht2l1nurfk0n5ff42gsagq7cpeh45u";

        String secretHash = Utils.calculateSecretHash(userPoolClientId, userPoolClientSecret, user.getUsername());

        AttributeType emailAttribute = AttributeType.builder()
                .name("emails")
                .value(user.getEmail())
                .build();

        AttributeType nameAttribute = AttributeType.builder()
                .name("name")
                .value("Chris")
                .build();

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .clientId(userPoolClientId)
                .username(user.getUsername())
                .password(user.getPassword())
                .userAttributes(emailAttribute, nameAttribute)
                .secretHash(secretHash)  // Include the computed secretHash
                .build();

        cognitoClient.signUp(signUpRequest);

        System.out.println("User saved: " + user.getUsername());
    }
}
