package com.example.springbootgithubactiondemo;

import com.mongodb.client.*;
import org.bson.Document;

import static com.example.springbootgithubactiondemo.Utils.preFlightChecks;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.example.springbootgithubactiondemo.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        properties = {
        "onebased.db.host=unittest",
        "onebased.db.port=2020"
})
class OneBasedServiceApplicationTests {

    @Autowired
    private HomeController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }


    @Test
    void TestGetHomeController() throws Exception {
        // perform request and verify status
        mockMvc.perform(get("/api/v1/home"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("OneBased Backend Service home endpoint running as expected."));
    }

    @Test
    void TestSubmitData() throws Exception {

        // Mock Mongo Client methods
        MongoClient mockMongoClient = mock(MongoClient.class);
        ListDatabasesIterable<Document> mockDatabases = mock(ListDatabasesIterable.class);
        when(mockMongoClient.listDatabases()).thenReturn(mockDatabases);

        // Mock preFlight Checks from utils
        try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)) {
            mockedUtils.when(() -> preFlightChecks(mockMongoClient)).thenReturn(true);

            try (MockedStatic<MongoClients> mockedStatic = mockStatic(MongoClients.class)) {

                // Set up the behavior of the static method
                mockedStatic.when(() -> MongoClients.create(any(String.class))).thenReturn(mockMongoClient);

                when(MongoClients.create(any(String.class))).thenReturn(mockMongoClient);

                // perform request and verify status
                mockMvc.perform(post("/api/v1/submit").content("submission"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string("Received data: submission"));
            }
        }
    }

    @Test
    public void testPreFlightChecksSuccess() {
        // Mocking the MongoClient and related objects
        MongoClient mockMongoClient = mock(MongoClient.class);
        MongoDatabase mockDatabase = mock(MongoDatabase.class);
        MongoCollection<Document> mockCollection = mock(MongoCollection.class);

        // Creating a mock response document with "ok" field equal to 1
        Document mockResponse = new Document("ok", 1);

        // Setting up mock interactions
        when(mockMongoClient.getDatabase("admin")).thenReturn(mockDatabase);
        when(mockDatabase.runCommand(any(Document.class))).thenReturn(mockResponse);

        // Testing the method
        boolean result = preFlightChecks(mockMongoClient);

        // Asserting the result
        assertTrue(result, "Expected preFlightChecks to return true");
    }

    @Test
    public void testPreFlightChecksFailure() {
        // Mocking the MongoClient and related objects
        MongoClient mockMongoClient = mock(MongoClient.class);
        MongoDatabase mockDatabase = mock(MongoDatabase.class);
        MongoCollection<Document> mockCollection = mock(MongoCollection.class);

        // Creating a mock response document with "ok" field not equal to 1
        Document mockResponse = new Document("ok", 0);

        // Setting up mock interactions
        when(mockMongoClient.getDatabase("admin")).thenReturn(mockDatabase);
        when(mockDatabase.runCommand(any(Document.class))).thenReturn(mockResponse);

        // Testing the method
        boolean result = preFlightChecks(mockMongoClient);

        // Asserting the result
        assertFalse(result, "Expected preFlightChecks to return false");
    }
}
