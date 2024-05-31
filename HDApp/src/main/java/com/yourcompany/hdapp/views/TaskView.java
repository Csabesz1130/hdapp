package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.TaskController;
import com.yourcompany.hdapp.models.Task;

public class TaskView {
    private TaskController taskController;
    private Task task;

    public TaskView(TaskController taskController, Task task) {
        this.taskController = taskController;
        this.task = task;
    }

    // Other methods and logic for TaskView
}
