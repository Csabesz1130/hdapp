package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.AuthController;
import com.yourcompany.hdapp.services.FirestoreService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField userField;
    private JButton loginButton;
    private AuthController authController;

    public LoginView() {
        FirestoreService firestoreService = new FirestoreService();
        authController = new AuthController(firestoreService);

        setTitle("HD App Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userField = new JTextField(15);
        loginButton = new JButton("Login");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(loginButton);
        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                try {
                    if (authController.authenticate(username)) {
                        new DashboardView(username).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
