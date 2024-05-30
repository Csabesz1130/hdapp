package com.yourcompany.hdapp;

import com.yourcompany.hdapp.controllers.AuthController;
import com.yourcompany.hdapp.views.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements LoginView.LoginListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AuthController authController;
    private static final String LOGIN_VIEW = "LoginView";
    private static final String MAIN_VIEW = "MainView";

    public MainFrame() {
        setTitle("HDApp - Firestore Integration");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        authController = new AuthController();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginView(), LOGIN_VIEW);
        mainPanel.add(createMainView(), MAIN_VIEW);

        add(mainPanel);
        showLoginView();
    }

    private JPanel createLoginView() {
        return new LoginView(this);
    }

    private JPanel createMainView() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Dashboard", new DashboardView());
        tabbedPane.addTab("Location Management", new LocationManagementPanel());
        tabbedPane.addTab("Tasks", new TaskView());
        tabbedPane.addTab("User Management", new UserManagementView());
        tabbedPane.addTab("Reports", new ReportsView());
        tabbedPane.addTab("Settings", new SettingsView());
        tabbedPane.addTab("Notifications", new NotificationsView());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);

        // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        panel.add(logoutButton, BorderLayout.NORTH);

        return panel;
    }

    private void showLoginView() {
        cardLayout.show(mainPanel, LOGIN_VIEW);
    }

    private void showMainView() {
        cardLayout.show(mainPanel, MAIN_VIEW);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            authController.logout();
            showLoginView();
        }
    }

    @Override
    public void onLoginSuccess() {
        showMainView();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
