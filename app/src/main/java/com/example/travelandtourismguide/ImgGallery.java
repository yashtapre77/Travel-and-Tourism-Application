package com.example.travelandtourismguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.travelandtourismguide.firebase_database.PicassoImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

public class ImgGallery extends AppCompatActivity {

    ScrollGalleryView galleryView;
    FirebaseFirestore ff;
    String[] imageUrl;
    String placeType, placeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_gallery);

        galleryView = findViewById(R.id.scroll_gallery_view);
        ff = FirebaseFirestore.getInstance();

        Intent in = getIntent();
        placeType = in.getStringExtra("PlaceType");
        placeName = in.getStringExtra("PlaceName");


        galleryView
                .setThumbnailSize(200)
                .setZoom(true)
                .withHiddenThumbnails(false)
                .hideThumbnailsOnClick(true)
                .hideThumbnailsAfter(5000)
                .addOnImageClickListener((position) -> {
                    Log.i(getClass().getName(), "You have clicked on image #" + position);
                })
                .setFragmentManager(getSupportFragmentManager());

        ff.collection(placeType).document(placeName).collection("ExtraImages").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                            String imgs = snapshot.get("imgUri").toString();
                            galleryView.addMedia(MediaInfo.mediaLoader(new PicassoImageLoader(imgs)));
                        }
                    }
                });


    }
}