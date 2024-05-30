package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.AuthController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    private AuthController authController;
    private LoginListener loginListener;

    public LoginView(LoginListener loginListener) {
        this.loginListener = loginListener;
        authController = new AuthController();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField usernameField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (authController.login(username)) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    if (loginListener != null) {
                        loginListener.onLoginSuccess();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed: Invalid username");
                }
            }
        });

        add(new JLabel("Username:"));
        add(usernameField);
        add(loginButton);
    }

    public interface LoginListener {
        void onLoginSuccess();
    }
}
