package com.yourcompany.hdapp.controllers;

import com.yourcompany.hdapp.models.Location;
import com.yourcompany.hdapp.services.FirestoreService;
import java.util.List;

public class LocationController {
    private FirestoreService firestoreService;

    public LocationController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    public List<Location> getLocations(String userId) {
        return firestoreService.getLocations(userId); // Ensure getLocations is implemented in FirestoreService
    }

    public void addLocation(String userId, Location location) {
        firestoreService.addLocation(userId, location); // Ensure addLocation is implemented in FirestoreService
    }

    public void deleteLocation(String userId, String locationId) {
        firestoreService.deleteLocation(userId, locationId); // Ensure deleteLocation is implemented in FirestoreService
    }

    public void updateLocationStatus(String userId, String locationId, String status) {
        firestoreService.updateLocationStatus(userId, locationId, status); // Ensure updateLocationStatus is implemented in FirestoreService
    }
}
