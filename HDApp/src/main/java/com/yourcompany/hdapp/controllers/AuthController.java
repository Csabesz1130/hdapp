package com.yourcompany.hdapp.controllers;

public class AuthController {
    public boolean authenticate(String username, String password) {
        // Implement authentication logic
        return "admin".equals(username) && "password".equals(password);
    }
}
