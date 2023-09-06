package com.example.travelandtourismguide.firebase_database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DataFirebase {
    public static final String[] CATEGORIES = {"Beaches ", "Hill Station ", "WaterFalls ", "Monuments ", "Religious ", "Island ", "Wildlife Park", "Educational", "Sporty", "Others"};


    public IsAvailable searchDocument(String search){
        Log.d("999999", "2 searchDocument: Enterd ");
        IsAvailable isAvailable = new IsAvailable();

        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        CollectionReference cr;
        for(String type : CATEGORIES){
            cr = ff.collection(type);
            Log.d("9999999", "searchDocument: ");
            cr.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        String Uid = documentSnapshot.getId();
                        if(search == Uid){
                           isAvailable.setAvailable(true);
                           isAvailable.setSnap(documentSnapshot);
                            Log.d("9999999", "3 searchDocument If condition satisfied");
                           break;
                        }
                        Log.d("999999", "1  1 1 1 1 1 1 1  1 ");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("999999", "onFailure: ");
                }
            });
            Log.d("99999", "CATEGORIES "+type);
        }
        Log.d("9999999", "searchDocument: Exiting "+isAvailable.getAvailable());
        return isAvailable;
    }


}
