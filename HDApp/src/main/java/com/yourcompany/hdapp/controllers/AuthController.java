package com.yourcompany.hdapp.controllers;

import com.yourcompany.hdapp.services.FirestoreService;

import java.util.HashSet;
import java.util.Set;

public class AuthController {
    private Set<String> validUsernames;
    private String loggedInUser;
    private FirestoreService firestoreService;


    public AuthController() {
        validUsernames = new HashSet<>();
        validUsernames.add("borisz");
        validUsernames.add("davidartur");
        // Add more usernames as needed
        loggedInUser = null;
    }

    public AuthController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    public boolean login(String username) {
        if (validUsernames.contains(username)) {
            loggedInUser = username;
            return true;
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
