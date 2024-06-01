package com.yourcompany.hdapp.controllers;

import com.yourcompany.hdapp.models.Task;
import com.yourcompany.hdapp.services.FirestoreService;
import java.util.List;

public class TaskController {
    private FirestoreService firestoreService;

    public TaskController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    public List<Task> getTasks(String userId) {
        return firestoreService.getTasks(userId); // Ensure getTasks is implemented in FirestoreService
    }

    public void addTask(String userId, Task task) {
        firestoreService.addTask(userId, task); // Ensure addTask is implemented in FirestoreService
    }

    public void deleteTask(String userId, String taskId) {
        firestoreService.deleteTask(userId, taskId); // Ensure deleteTask is implemented in FirestoreService
    }

    public void updateTaskStatus(String userId, String taskId, String status) {
        firestoreService.updateTaskStatus(userId, taskId, status); // Ensure updateTaskStatus is implemented in FirestoreService
    }
}
