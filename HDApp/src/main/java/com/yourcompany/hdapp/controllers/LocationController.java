package com.yourcompany.hdapp.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.yourcompany.hdapp.models.Location;
import com.yourcompany.hdapp.services.FirestoreService;

import java.util.List;
import java.util.stream.Collectors;

public class LocationController {
    private FirestoreService firestoreService;

    public LocationController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    public List<Location> getLocations() throws Exception {
        List<DocumentSnapshot> documents = firestoreService.getTasks("locations");
        return documents.stream()
                .map(doc -> doc.toObject(Location.class))
                .collect(Collectors.toList());
    }

    public void addLocation(Location location) throws Exception {
        firestoreService.addLocation("locations", location);
    }

    public void deleteLocation(String locationId) throws Exception {
        firestoreService.deleteLocation("locations", locationId);
    }

    public void updateLocationStatus(String locationId, String status) throws Exception {
        firestoreService.updateTaskStatus("locations", locationId, status);
    }

    public void bulkUpdateLocations(List<String> locationIds, String status) throws Exception {
        for (String id : locationIds) {
            updateLocationStatus(id, status);
        }
    }

    public void importLocationsFromSheet(String sheetId, String range) throws Exception {
        // Implement the logic to import locations from Google Sheets using the sheetId and range
    }
}
