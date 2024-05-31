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

    public List<DocumentSnapshot> getTasks(String collection) throws InterruptedException, ExecutionException {
        List<DocumentSnapshot> documents = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(collection).get();
        documents.addAll(future.get().getDocuments());
        return documents;
    }

    public void addLocation(String collection, Location location) throws InterruptedException, ExecutionException {
        firestore.collection(collection).add(location).get();
    }

    public void deleteLocation(String collection, String documentId) throws InterruptedException, ExecutionException {
        firestore.collection(collection).document(documentId).delete().get();
    }

    public void updateTaskStatus(String collection, String documentId, String status) throws InterruptedException, ExecutionException {
        firestore.collection(collection).document(documentId).update("status", status).get();
    }
}
