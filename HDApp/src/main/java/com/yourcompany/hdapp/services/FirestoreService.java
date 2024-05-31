package com.yourcompany.hdapp.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.yourcompany.hdapp.models.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreService {
    private Firestore firestore;

    public FirestoreService(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Location> getLocations() throws InterruptedException, ExecutionException {
        List<Location> locations = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("locations").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Location location = document.toObject(Location.class);
            locations.add(location);
        }
        return locations;
    }
}
