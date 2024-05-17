package com.yourcompany.hdapp.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;

public class FirestoreService {
    private Firestore db;

    public FirestoreService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<DocumentSnapshot> getTasks() throws Exception {
        ApiFuture<QuerySnapshot> future = db.collection("tasks").get();
        return future.get().getDocuments();
    }

    //megadni a collectiont, keresőfüggvény és visszadja a dokumentumokat, aszinkron működik, meg kell várni a választ.
    //collection name is angol legyen.
    //initialaze the database maybe using .json file


    public void updateTaskStatus(String taskId, String status) throws Exception {
        DocumentReference docRef = db.collection("tasks").document(taskId);
        ApiFuture<WriteResult> writeResult = docRef.update("status", status);
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
    }
}
