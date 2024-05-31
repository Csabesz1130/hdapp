package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.AuthController;

import javax.swing.*;

public class DashboardView extends JPanel {
    private AuthController authController;

    public DashboardView(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }

    private void initializeUI() {
        // Initialize the dashboard UI
        JLabel label = new JLabel("Dashboard");
        add(label);
    }
}
