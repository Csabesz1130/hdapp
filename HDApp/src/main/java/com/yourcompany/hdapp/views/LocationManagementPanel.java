package com.yourcompany.hdapp.views;

import com.yourcompany.hdapp.controllers.LocationController;
import com.yourcompany.hdapp.models.Location;
import com.yourcompany.hdapp.services.FirestoreService;
import com.yourcompany.hdapp.services.ExcelService;
import com.yourcompany.hdapp.services.GoogleSheetsService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class LocationManagementPanel extends JPanel {
    private LocationController locationController;
    private JTextField nameFilterField;
    private JTextField statusFilterField;
    private JPanel locationPanel;
    private JScrollPane locationScrollPane;

    public LocationManagementPanel() {
        FirestoreService firestoreService = new FirestoreService();
        ExcelService excelService = new ExcelService();
        GoogleSheetsService googleSheetsService = new GoogleSheetsService();
        locationController = new LocationController(firestoreService, googleSheetsService, excelService);

        setLayout(new BorderLayout());

        add(createFilterPanel(), BorderLayout.NORTH);
        locationScrollPane = createLocationPanel();
        add(locationScrollPane, BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout());

        nameFilterField = new JTextField(15);
        statusFilterField = new JTextField(15);
        JButton applyFilterButton = new JButton("Apply Filter");

        applyFilterButton.addActionListener(e -> {
            try {
                updateLocationTable();
            } catch (Exception ex) {
                showErrorDialog("Error applying filter: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        JButton importButton = new JButton("Import from Google Sheets");
        importButton.addActionListener(e -> {
            try {
                importFromGoogleSheets();
                updateLocationTable();
            } catch (Exception ex) {
                showErrorDialog("Error importing data: " + ex.getMessage());
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

    private JScrollPane createLocationPanel() {
        locationPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 cards per row
        loadData();
        return new JScrollPane(locationPanel);
    }

    private void loadData() {
        SwingWorker<List<Location>, Void> worker = new SwingWorker<List<Location>, Void>() {
            @Override
            protected List<Location> doInBackground() throws Exception {
                showProgressIndicator(true);
                return locationController.getLocations();
            }

            @Override
            protected void done() {
                try {
                    List<Location> locations = get();
                    locationPanel.removeAll();
                    for (Location location : locations) {
                        locationPanel.add(createLocationCard(location));
                    }
                    locationPanel.revalidate();
                    locationPanel.repaint();
                } catch (Exception e) {
                    showErrorDialog("Error loading data: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    showProgressIndicator(false);
                }
            }
        };
        worker.execute();
    }

    private JPanel createLocationCard(Location location) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(200, 150));

        JLabel nameLabel = new JLabel(location.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(nameLabel, BorderLayout.NORTH);

        JLabel statusLabel = new JLabel("Status: " + location.getStatus());
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(statusLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        editButton.addActionListener(e -> editLocation(location));

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this location?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        showProgressIndicator(true);
                        locationController.deleteLocation(location.getId());
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            refreshLocationPanel();
                        } catch (Exception e) {
                            showErrorDialog("Error refreshing data: " + e.getMessage());
                            e.printStackTrace();
                        } finally {
                            showProgressIndicator(false);
                        }
                    }
                };
                worker.execute();
            }
        });

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private void editLocation(Location location) {
        JTextField nameField = new JTextField(location.getName());
        JTextField statusField = new JTextField(location.getStatus());
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Location", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    showProgressIndicator(true);
                    location.setName(nameField.getText());
                    location.setStatus(statusField.getText());
                    locationController.updateLocationStatus(location.getId(), location.getStatus());
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        refreshLocationPanel();
                    } catch (Exception e) {
                        showErrorDialog("Error refreshing data: " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        showProgressIndicator(false);
                    }
                }
            };
            worker.execute();
        }
    }

    private void refreshLocationPanel() {
        locationPanel.removeAll();
        loadData();
        revalidate();
        repaint();
    }

    private void updateLocationTable() throws Exception {
        List<Location> locations = locationController.getLocations();
        String nameFilter = nameFilterField.getText().toLowerCase();
        String statusFilter = statusFilterField.getText().toLowerCase();

        List<Location> filteredLocations = locations.stream()
                .filter(location -> location.getName().toLowerCase().contains(nameFilter))
                .filter(location -> location.getStatus().toLowerCase().contains(statusFilter))
                .collect(Collectors.toList());

        locationPanel.removeAll();
        for (Location location : filteredLocations) {
            locationPanel.add(createLocationCard(location));
        }
        locationPanel.revalidate();
        locationPanel.repaint();
    }

    private void showProgressIndicator(boolean show) {
        JDialog progressDialog = new JDialog((Frame) null, "Loading", true);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        progressDialog.setSize(200, 100);
        progressDialog.setLocationRelativeTo(this);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressDialog.add(BorderLayout.CENTER, progressBar);
        if (show) {
            SwingUtilities.invokeLater(() -> progressDialog.setVisible(true));
        } else {
            SwingUtilities.invokeLater(() -> progressDialog.dispose());
        }
    }

    private void importFromGoogleSheets() {
        String spreadsheetId = JOptionPane.showInputDialog("Enter Google Sheets ID:");
        String range = "Sheet1!A2:C"; // Adjust the range accordingly
        if (spreadsheetId != null && !spreadsheetId.trim().isEmpty()) {
            SwingWorker<List<List<Object>>, Void> worker = new SwingWorker<List<List<Object>>, Void>() {
                @Override
                protected List<List<Object>> doInBackground() throws Exception {
                    try {
                        showProgressIndicator(true);
                        return locationController.importLocationsFromSheet(spreadsheetId, range);
                    } catch (Exception e) {
                        showErrorDialog("Error importing data from Google Sheets: " + e.getMessage());
                        e.printStackTrace();
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
                        }
                    } catch (Exception e) {
                        showErrorDialog("Error processing imported data: " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        showProgressIndicator(false);
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
