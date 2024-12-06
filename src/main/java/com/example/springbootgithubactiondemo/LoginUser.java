package com.example.springbootgithubactiondemo;

import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.springbootgithubactiondemo.Utils.calculateSecretHash;

/**
 * To run this Java code example, you need to create a client app in a user pool
 * with a secret key. For details, see:
 * https://docs.aws.amazon.com/cognito/latest/developerguide/user-pool-settings-client-apps.html
 *
 * In addition, set up your development environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 *
 */

public class LoginUser {

    public static String initiateAuth(CognitoIdentityProviderClient cognitoClient, String clientId, String secretKey, String username, String password) {

        try {
            String secretVal = calculateSecretHash(clientId, secretKey, username);
            InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .clientId(clientId)
                    .authParameters(Map.of("USERNAME", username, "PASSWORD", password, "SECRET_HASH", secretVal))
                    .build();
            InitiateAuthResponse authResult = cognitoClient.initiateAuth(authRequest);
            return authResult.authenticationResult().idToken(); // Return the ID token
        } catch (NotAuthorizedException e) {
            // Handle incorrect username or password
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Authentication error: " + e.getMessage());
        }
    }
}
// snippet-end:[cognito.java2.signup.main]