package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.TaskController;
import com.yourcompany.hdapp.models.Task;
import com.yourcompany.hdapp.services.FirestoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardView extends JFrame {
    private TaskController taskController;
    private JTable taskTable;
    private JButton refreshButton;

    public DashboardView() {
        FirestoreService firestoreService = new FirestoreService();
        taskController = new TaskController(firestoreService);

        setTitle("HD App Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        taskTable = new JTable(); // You need to set a proper model
        refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateTaskTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(taskTable), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
        add(panel);

        try {
            updateTaskTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTaskTable() throws Exception {
        List<Task> tasks = taskController.getTasks();
        String[][] data = new String[tasks.size()][3];
        for (int i = 0; i < tasks.size(); i++) {
            data[i][0] = tasks.get(i).getId();
            data[i][1] = tasks.get(i).getName();
            data[i][2] = tasks.get(i).getStatus();
        }
        String[] columnNames = {"ID", "Name", "Status"};
        taskTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}
