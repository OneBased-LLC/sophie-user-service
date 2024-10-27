package com.example.springbootgithubactiondemo;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUserPoolsRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUserPoolsResponse;

public class ListUserPools {

    CognitoIdentityProviderClient activeCognitoClient;

    public CognitoIdentityProviderClient getActiveCognitoClient(){
        return activeCognitoClient;
    }

    public void initializeClient() {
        String clientId = "YOUR_APP_CLIENT_ID"; // Replace with your app client ID
        String userPoolId = "YOUR_USER_POOL_ID"; // Replace with your Cognito user pool ID
        String accessKey = "";
        String secretKey = "";
        activeCognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    };

    public static void listAllUserPools(CognitoIdentityProviderClient cognitoClient) {
        try {
            ListUserPoolsRequest request = ListUserPoolsRequest.builder()
                    .maxResults(10)
                    .build();

            ListUserPoolsResponse response = cognitoClient.listUserPools(request);
            response.userPools().forEach(userpool -> {
                System.out.println("User pool " + userpool.name() + ", User ID " + userpool.id());
            });

        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
