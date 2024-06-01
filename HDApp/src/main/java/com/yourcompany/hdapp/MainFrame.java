package com.yourcompany.hdapp;

import com.yourcompany.hdapp.controllers.AuthController;
import com.yourcompany.hdapp.controllers.TaskController;
import com.yourcompany.hdapp.models.Task;
import com.yourcompany.hdapp.views.TaskView;
import com.yourcompany.hdapp.services.FirestoreService;

import javax.swing.*;

public class MainFrame extends JFrame {
    private TaskController taskController;
    private AuthController authController;

    public MainFrame() {
        FirestoreService firestoreService = new FirestoreService(); // Initialize FirestoreService
        this.authController = new AuthController();
        this.taskController = new TaskController(firestoreService);

        Task task = new Task(); // Assuming a Task object is needed

        TaskView taskView = new TaskView(taskController, task);
        // Initialize and set up your main frame here
    }

    // Other methods and logic for MainFrame
}
