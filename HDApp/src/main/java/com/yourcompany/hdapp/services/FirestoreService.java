package com.yourcompany.hdapp.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.yourcompany.hdapp.models.Location;
import com.yourcompany.hdapp.models.Task;

import java.util.List;

public class FirestoreService {
    private Firestore db;

    public FirestoreService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<DocumentSnapshot> getTasks(String collection) throws Exception {
        ApiFuture<QuerySnapshot> future = db.collection(collection).get();
        return future.get().getDocuments();
    }

    public void updateTaskStatus(String collection, String taskId, String status) throws Exception {
        DocumentReference docRef = db.collection(collection).document(taskId);
        ApiFuture<WriteResult> writeResult = docRef.update("status", status);
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
    }

    public void addLocation(String collection, Location location) throws Exception {
        ApiFuture<DocumentReference> future = db.collection(collection).add(location);
        System.out.println("Added document with ID: " + future.get().getId());
    }

    public void deleteLocation(String collection, String locationId) throws Exception {
        DocumentReference docRef = db.collection(collection).document(locationId);
        ApiFuture<WriteResult> writeResult = docRef.delete();
        System.out.println("Delete time : " + writeResult.get().getUpdateTime());
    }
}
