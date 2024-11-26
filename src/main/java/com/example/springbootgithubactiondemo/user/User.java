package com.example.springbootgithubactiondemo.user;

import com.example.springbootgithubactiondemo.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "users")  // Collection name in MongoDB
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;  // This is the primary key

    private String username;

    private String email;

    private String password; // Make sure to handle passwords securely
    private String role;

    private String name;

    private String profilePicture; // Storing the profile picture as a byte array


    // Constructors
    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String username, String password) { this.username = username; this.password = password; }

    public User(String username, String email, String password, String role, String profilePicture, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id = UUID.randomUUID();
        this.profilePicture = profilePicture;
        this.name = name;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    };
    public void setName(String name) { this.name = name; }

//    public void loadUser(UserRepository userRepository) {
//        userRepository.findOne()
//    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id.toString() + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
