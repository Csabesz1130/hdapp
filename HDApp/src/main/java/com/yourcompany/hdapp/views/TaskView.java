package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.TaskController;
import com.yourcompany.hdapp.models.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskView extends JFrame {
    private TaskController taskController;
    private Task task;

    private JTextField nameField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;

    public TaskView(TaskController taskController, Task task) {
        this.taskController = taskController;
        this.task = task;

        setTitle("Task Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        nameField = new JTextField(task.getName(), 20);
        statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
        statusComboBox.setSelectedItem(task.getStatus());
        saveButton = new JButton("Save");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(saveButton);
        add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task.setName(nameField.getText());
                task.setStatus((String) statusComboBox.getSelectedItem());
                try {
                    taskController.updateTaskStatus(task.getId(), task.getStatus());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
    }
}
