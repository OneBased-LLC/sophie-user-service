package com.example.springbootgithubactiondemo;
import com.example.springbootgithubactiondemo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "test@example.com", "password123", "USER", "profilePic.png");
    }

    @Test
    void testUserConstructor() {
        assertNotNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals("profilePic.png", user.getProfilePicture());
    }

    @Test
    void testGettersAndSetters() {
        user.setUsername("newuser");
        user.setEmail("new@example.com");
        user.setPassword("newpassword");
        user.setRole("ADMIN");
        user.setProfilePicture("newProfilePic.png");
        UUID newId = UUID.randomUUID();
        user.setId(newId);

        assertEquals("newuser", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
        assertEquals("ADMIN", user.getRole());
        assertEquals("newProfilePic.png", user.getProfilePicture());
        assertEquals(newId, user.getId());
    }

    @Test
    void testToString() {
        String expectedString = "User{id='" + user.getId().toString() + '\'' +
                ", username='testuser'" +
                ", email='test@example.com'}";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void testDefaultIdGeneration() {
        User newUser = new User();
        assertNotNull(newUser.getId());
        assertNotEquals(user.getId(), newUser.getId()); // Ensure IDs are different
    }
}
