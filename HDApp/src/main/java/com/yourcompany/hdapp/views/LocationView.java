package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.LocationController;
import com.yourcompany.hdapp.models.Location;
import com.yourcompany.hdapp.services.ExcelService;
import com.yourcompany.hdapp.services.FirestoreService;
import com.yourcompany.hdapp.services.GoogleSheetsService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class LocationView extends JFrame {
    private LocationController locationController;
    private JTable locationTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton exportButton;
    private JButton importButton;
    private JButton googleExportButton;
    private JButton googleImportButton;
    private JTextField nameField;
    private JTextField statusField;
    private ExcelService excelService;
    private GoogleSheetsService googleSheetsService;

    public LocationView() {
        FirestoreService firestoreService = new FirestoreService();
        locationController = new LocationController(firestoreService);
        excelService = new ExcelService();
        googleSheetsService = new GoogleSheetsService();

        setTitle("Manage Locations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        locationTable = new JTable(); // You need to set a proper model
        addButton = new JButton("Add Location");
        deleteButton = new JButton("Delete Location");
        exportButton = new JButton("Export to Excel");
        importButton = new JButton("Import from Excel");
        googleExportButton = new JButton("Export to Google Sheets");
        googleImportButton = new JButton("Import from Google Sheets");
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
                try {
                    List<Location> locations = locationController.getLocations();
                    String filePath = "exported_locations.xlsx"; // Prompt user to select location
                    excelService.exportLocationsToExcel(locations, filePath);
                    JOptionPane.showMessageDialog(null, "Exported successfully to " + filePath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filePath = "path/to/imported_locations.xlsx"; // Prompt user to select file
                    List<Location> locations = excelService.importLocationsFromExcel(filePath);
                    for (Location location : locations) {
                        locationController.addLocation(location);
                    }
                    updateLocationTable();
                    JOptionPane.showMessageDialog(null, "Imported successfully from " + filePath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        googleExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Location> locations = locationController.getLocations();
                    List<List<Object>> values = new ArrayList<>();
                    for (Location location : locations) {
                        List<Object> row = new ArrayList<>();
                        row.add(location.getId());
                        row.add(location.getName());
                        row.add(location.getStatus());
                        values.add(row);
                    }
                    String spreadsheetId = "your-spreadsheet-id"; // Prompt user to enter ID
                    String range = "Sheet1!A1:C";
                    googleSheetsService.exportDataToSheet(spreadsheetId, range, values);
                    JOptionPane.showMessageDialog(null, "Exported successfully to Google Sheets");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        googleImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String spreadsheetId = "your-spreadsheet-id"; // Prompt user to enter ID
                    String range = "Sheet1!A2:C"; // Adjust the range accordingly
                    List<List<Object>> values = googleSheetsService.importDataFromSheet(spreadsheetId, range);
                    for (List<Object> row : values) {
                        Location location = new Location();
                        location.setId(row.get(0).toString());
                        location.setName(row.get(1).toString());
                        location.setStatus(row.get(2).toString());
                        locationController.addLocation(location);
                    }
                    updateLocationTable();
                    JOptionPane.showMessageDialog(null, "Imported successfully from Google Sheets");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
        buttonPanel.add(googleExportButton);
        buttonPanel.add(googleImportButton);

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
