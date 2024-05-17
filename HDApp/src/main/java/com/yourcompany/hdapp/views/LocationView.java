package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.LocationController;
import com.yourcompany.hdapp.models.Location;
import com.yourcompany.hdapp.services.FirestoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LocationView extends JFrame {
    private LocationController locationController;
    private JTable locationTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton exportButton;
    private JButton importButton;
    private JTextField nameField;
    private JTextField statusField;

    public LocationView() {
        FirestoreService firestoreService = new FirestoreService();
        locationController = new LocationController(firestoreService);

        setTitle("Manage Locations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        locationTable = new JTable(); // You need to set a proper model
        addButton = new JButton("Add Location");
        deleteButton = new JButton("Delete Location");
        exportButton = new JButton("Export SN List");
        importButton = new JButton("Import Locations");
        nameField = new JTextField(15);
        statusField = new JTextField(15);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Location location = new Location();
                    location.setName(nameField.getText());
                    location.setStatus(statusField.getText());
                    locationController.addLocation(location);
                    updateLocationTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = locationTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String locationId = locationTable.getValueAt(selectedRow, 0).toString();
                        locationController.deleteLocation(locationId);
                        updateLocationTable();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement export functionality
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement import functionality
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(importButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(locationTable), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        try {
            updateLocationTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLocationTable() throws Exception {
        List<Location> locations = locationController.getLocations();
        String[][] data = new String[locations.size()][3];
        for (int i = 0; i < locations.size(); i++) {
            data[i][0] = locations.get(i).getId();
            data[i][1] = locations.get(i).getName();
            data[i][2] = locations.get(i).getStatus();
        }
        String[] columnNames = {"ID", "Name", "Status"};
        locationTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}
