package com.example.travelandtourismguide.firebase_database;

import android.util.Log;

public class Recommendations_modal {
    private String videoUri, name, description, place;
    private boolean favourite;

    public Recommendations_modal(String videoUri, String name, String description,String place, boolean favourite) {
        this.videoUri = videoUri;
        this.name = name;
        this.description = description;
        this.place = place;
        this.favourite = favourite;

    }

    public Recommendations_modal() {
        Log.d("9999999", "Recommendations_modal: "+name);
        Log.d("9999999", "Recommendations_modal: "+videoUri);
        Log.d("9999999", "Recommendations_modal: "+description);
        Log.d("9999999", "Recommendations_modal: "+place);
        Log.d("9999999", "Recommendations_modal: "+favourite);
    }

    public void setVideoUri(String videoUrl) {
        this.videoUri = videoUrl;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean fav) {
        favourite = fav;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public String getTitle() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace(){return place;}
}
