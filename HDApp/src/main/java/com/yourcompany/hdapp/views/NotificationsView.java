package com.yourcompany.hdapp.views;

import javax.swing.*;

public class NotificationsView extends JPanel {
    public NotificationsView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Notifications");
        add(label);

        // Add components to view notifications and alerts
    }
}
