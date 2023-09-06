package com.example.travelandtourismguide.firebase_database;

public class TypesListModal {
    private String imgUrl, pName, pAddress;
    boolean isFav;

    public TypesListModal(String imgUrl, String pName, String pAddress, boolean isFav) {
        this.imgUrl = imgUrl;
        this.pName = pName;
        this.pAddress = pAddress;
        this.isFav = isFav;
    }

    public TypesListModal() {

    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getpName() {
        return pName;
    }

    public String getpAddress() {
        return pAddress;
    }

    public boolean isFav() {
        return isFav;
    }
}
