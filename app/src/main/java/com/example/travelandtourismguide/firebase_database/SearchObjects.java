package com.example.travelandtourismguide.firebase_database;

public class SearchObjects {
    private String Place;
    private String Address;
    private  String Image;

    public SearchObjects(String place, String address,String image) {
        Place = place;
        Address = address;
        Image = image;
    }

    public SearchObjects() {
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
