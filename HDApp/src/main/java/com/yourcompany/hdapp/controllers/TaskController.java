package com.yourcompany.hdapp.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.yourcompany.hdapp.models.Task;
import com.yourcompany.hdapp.services.FirestoreService;

import java.util.List;
import java.util.stream.Collectors;

public class TaskController {
    private FirestoreService firestoreService;

    public TaskController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    public List<Task> getTasks() throws Exception {
        List<DocumentSnapshot> documents = firestoreService.getTasks();
        return documents.stream()
                .map(doc -> doc.toObject(Task.class))
                .collect(Collectors.toList());
    }

    public void updateTaskStatus(String taskId, String status) throws Exception {
        firestoreService.updateTaskStatus(taskId, status);
    }
}
