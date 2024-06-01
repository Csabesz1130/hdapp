package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.LocationController;
import com.yourcompany.hdapp.models.Location;
import com.yourcompany.hdapp.services.GoogleSheetsService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LocationManagementPanel extends JPanel {
    private LocationController locationController;
    private JTable locationTable;
    public LocationTableModel locationTableModel;

    public LocationManagementPanel(LocationController locationController) {
        this.locationController = locationController;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel filterPanel = createFilterPanel();
        add(filterPanel, BorderLayout.NORTH);

        locationTableModel = new LocationTableModel();
        locationTable = new JTable(locationTableModel);

        JScrollPane scrollPane = new JScrollPane(locationTable);
        add(scrollPane, BorderLayout.CENTER);

        loadLocations();
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout());

        JTextField nameFilterField = new JTextField(15);
        JTextField statusFilterField = new JTextField(15);
        JButton applyFilterButton = new JButton("Apply Filter");

        applyFilterButton.addActionListener(e -> {
            // Add filter logic here
        });

        JButton importButton = new JButton("Import from Google Sheets");
        importButton.addActionListener(e -> {
            try {
                importFromGoogleSheets();
                loadLocations();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        filterPanel.add(new JLabel("Name:"));
        filterPanel.add(nameFilterField);
        filterPanel.add(new JLabel("Status:"));
        filterPanel.add(statusFilterField);
        filterPanel.add(applyFilterButton);
        filterPanel.add(importButton);

        return filterPanel;
    }

    private void loadLocations() {
        SwingWorker<List<Location>, Void> worker = new SwingWorker<List<Location>, Void>() {
            @Override
            protected List<Location> doInBackground() throws Exception {
                return locationController.getLocations();
            }

            @Override
            protected void done() {
                try {
                    List<Location> locations = get();
                    locationTableModel.setLocations(locations);
                } catch (Exception e) {
                    showErrorDialog("Error loading locations: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void importFromGoogleSheets() {
        String spreadsheetId = JOptionPane.showInputDialog("Enter Google Sheets ID:");
        String range = "Sheet1!A2:C"; // Adjust the range accordingly
        if (spreadsheetId != null && !spreadsheetId.trim().isEmpty()) {
            SwingWorker<List<List<Object>>, Void> worker = new SwingWorker<List<List<Object>>, Void>() {
                @Override
                protected List<List<Object>> doInBackground() throws Exception {
                    try {
                        GoogleSheetsService googleSheetsService = new GoogleSheetsService();
                        return googleSheetsService.getSheetData(spreadsheetId, range);
                    } catch (Exception e) {
                        showErrorDialog("Error importing data from Google Sheets: " + e.getMessage());
                        return null;
                    }
                }

                @Override
                protected void done() {
                    try {
                        List<List<Object>> rows = get();
                        if (rows != null) {
                            for (List<Object> row : rows) {
                                if (row.size() >= 2) {
                                    String name = (String) row.get(0);
                                    String status = (String) row.get(1);
                                    Location location = new Location();
                                    location.setName(name);
                                    location.setStatus(status);
                                    locationController.addLocation(location);
                                }
                            }
                            loadLocations();
                        }
                    } catch (Exception e) {
                        showErrorDialog("Error processing imported data: " + e.getMessage());
                    }
                }
            };
            worker.execute();
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
