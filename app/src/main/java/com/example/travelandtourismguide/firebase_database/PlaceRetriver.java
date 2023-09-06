package com.example.travelandtourismguide.firebase_database;
public class PlaceRetriver {
    private String name, address, description, mainImage;
    private float rating;
    private boolean favourite = false;
    private double latitude, longitude;

    public PlaceRetriver(String name, String address, String description, String mainImage, float rating, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.mainImage = mainImage;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlaceRetriver(){}


    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getMainImage() {
        return mainImage;
    }

    public float getRating() {
        return rating;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
