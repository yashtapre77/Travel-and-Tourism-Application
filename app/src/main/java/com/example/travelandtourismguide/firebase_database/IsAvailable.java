package com.example.travelandtourismguide.firebase_database;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class IsAvailable {
    boolean isAvailable;
    QueryDocumentSnapshot snap;

    public IsAvailable(boolean isAvailable, QueryDocumentSnapshot snap) {
        this.isAvailable = isAvailable;
        this.snap = snap;
    }

    public IsAvailable(){

    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public QueryDocumentSnapshot getSnap() {
        return snap;
    }

    public void setSnap(QueryDocumentSnapshot snap) {
        this.snap = snap;
    }
}
