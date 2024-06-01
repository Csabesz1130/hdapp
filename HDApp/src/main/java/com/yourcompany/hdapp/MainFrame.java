package com.yourcompany.hdapp;

import com.yourcompany.hdapp.controllers.AuthController;
import com.yourcompany.hdapp.controllers.TaskController;
import com.yourcompany.hdapp.models.Task;
import com.yourcompany.hdapp.views.TaskView;
import com.yourcompany.hdapp.services.FirestoreService;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final TaskController taskController;
    private final AuthController authController;

    public MainFrame() {
        FirestoreService firestoreService = new FirestoreService(); // Initialize FirestoreService
        this.authController = new AuthController(firestoreService);
        this.taskController = new TaskController(firestoreService);

        Task task = new Task(); // Assuming a Task object is needed

        TaskView taskView = new TaskView(taskController, task);
        // Initialize and set up your main frame here

        // Adding TaskView to MainFrame for display purposes
        this.add(taskView);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    // Other methods and logic for MainFrame
}
