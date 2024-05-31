package com.yourcompany.hdapp;

import com.yourcompany.hdapp.controllers.AuthController;
import com.yourcompany.hdapp.services.FirestoreService;
import com.yourcompany.hdapp.views.DashboardView;
import com.yourcompany.hdapp.views.TaskView;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private AuthController authController;

    public MainFrame() {
        // Initialize the main frame
        setTitle("HDApp");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        FirestoreService firestoreService = new FirestoreService();
        authController = new AuthController(firestoreService);

        // Initialize UI components
        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        DashboardView dashboardView = new DashboardView(authController);
        TaskView taskView = new TaskView(authController);

        tabbedPane.addTab("Dashboard", dashboardView);
        tabbedPane.addTab("Tasks", taskView);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
