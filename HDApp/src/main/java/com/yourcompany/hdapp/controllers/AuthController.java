package com.yourcompany.hdapp.controllers;

import java.util.HashSet;
import java.util.Set;

public class AuthController {
    private Set<String> validUsernames;
    private String currentUser;

    public AuthController() {
        validUsernames = new HashSet<>();
        validUsernames.add("borisz");
        validUsernames.add("davidartur");
        // Add more usernames as needed
        currentUser = null;
    }

    public boolean login(String username) {
        if (validUsernames.contains(username)) {
            currentUser = username;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
