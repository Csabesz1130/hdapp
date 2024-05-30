package com.yourcompany.hdapp.controllers;

import java.util.HashSet;
import java.util.Set;

public class AuthController {
    private Set<String> validUsernames;

    public AuthController() {
        validUsernames = new HashSet<>();
        validUsernames.add("borisz");
        validUsernames.add("davidartur");
        // Add more usernames as needed
    }

    public boolean login(String username) {
        return validUsernames.contains(username);
    }

    public void logout() {
        // Implement logout logic if needed
    }
}
