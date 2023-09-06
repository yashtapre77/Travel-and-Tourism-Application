package com.example.travelandtourismguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelandtourismguide.firebase_database.DataFirebase;
import com.example.travelandtourismguide.firebase_database.IsAvailable;
import com.example.travelandtourismguide.firebase_database.PlaceRetriver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Result_page extends AppCompatActivity {

    TextView pName, pDescription, pRatings, pAddresss;
    Button pTrackLoc;
    ImageView pImage, pFavourites, pMoreImages;
    CircleImageView pFavBack;
    private String PlaceName;

    FirebaseFirestore ff;
    PlaceRetriver modal;
    String[] typs;
    String placeType;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        pName = findViewById(R.id.result_place_name);
        pDescription = findViewById(R.id.results_place_description);
        pRatings = findViewById(R.id.results_place_ratings);
        pAddresss = findViewById(R.id.results_place_address);

        pImage = findViewById(R.id.results_place_image);
        pFavBack = findViewById(R.id.results_place_circleFav);
        pFavourites = findViewById(R.id.results_place_favourites);
        pMoreImages = findViewById(R.id.results_place_MoreImages);

        pTrackLoc = findViewById(R.id.results_place_btn_track);

        Intent in = getIntent();
        PlaceName = in.getStringExtra("PlaceName");
        typs = DataFirebase.CATEGORIES;
        ff = FirebaseFirestore.getInstance();



        for(String str : typs) {
            DocumentReference docIdRef = ff.collection(str).document(PlaceName);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("999999", "Document exists!"+document.get("search"));
                            modal = document.toObject(PlaceRetriver.class);
                            placeType = str;
                            pName.setText(modal.getName());
                            pAddresss.setText(modal.getAddress());
                            pDescription.setText(modal.getDescription());
                            pRatings.setText(Float.toString(modal.getRating()));
                            latitude = modal.getLatitude();
                            longitude = modal.getLongitude();
                            Picasso.get().load(modal.getMainImage()).into(pImage);
                            if(modal.isFavourite()){
                                pFavourites.setImageResource(R.drawable.orange_favourite);
                                pFavourites.setTag("1");
                            }
                            else {
                                pFavourites.setImageResource(R.drawable.orange_unfavourite);
                                pFavourites.setTag("0");
                            }
                        } else {
                            Log.d("9999999", "Document does not exist!");
                        }
                    } else {
                        Log.d("9999999", "Failed with: ", task.getException());
                    }
                }
            });
        }




        pFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pFavourites.getTag().toString() == "0") {
                    pFavourites.setImageResource(R.drawable.orange_favourite);
                    pFavourites.setTag("1");
                    for (String str : typs) {
                        DocumentReference docIdRef = ff.collection(str).document(pName.getText().toString());
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        docIdRef.update("favourite", true);
                                    } else {
                                        Log.d("9999999", "Unable to update");
                                    }
                                } else {
                                    Log.d("9999999", "Failed with: ", task.getException());
                                }
                            }
                        });
                    }
                }
                else {
                    pFavourites.setImageResource(R.drawable.orange_unfavourite);
                    pFavourites.setTag("0");
                    for(String str : typs) {
                        DocumentReference docIdRef = ff.collection(str).document(pName.getText().toString());
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        docIdRef.update("favourite",true);
                                    } else {
                                        Log.d("9999999", "Unable to update");
                                    }
                                } else {
                                    Log.d("9999999", "Failed with: ", task.getException());
                                }
                            }
                        });
                    }
                }
            }
        });

        pFavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pFavourites.getTag().toString() == "0") {
                    pFavourites.setImageResource(R.drawable.orange_favourite);
                    pFavourites.setTag("1");
                    for (String str : typs) {
                        DocumentReference docIdRef = ff.collection(str).document(pName.getText().toString());
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        docIdRef.update("favourite", true);
                                    } else {
                                        Log.d("9999999", "Unable to update");
                                    }
                                } else {
                                    Log.d("9999999", "Failed with: ", task.getException());
                                }
                            }
                        });
                    }
                }
                else {
                    pFavourites.setImageResource(R.drawable.orange_unfavourite);
                    pFavourites.setTag("0");
                    for(String str : typs) {
                        DocumentReference docIdRef = ff.collection(str).document(pName.getText().toString());
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        docIdRef.update("favourite",true);
                                    } else {
                                        Log.d("9999999", "Unable to update");
                                    }
                                } else {
                                    Log.d("9999999", "Failed with: ", task.getException());
                                }
                            }
                        });
                    }
                }
            }
        });

        pMoreImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),ImgGallery.class);
                in.putExtra("PlaceType",placeType);
                in.putExtra("PlaceName",pName.getText());
                startActivity(in);
            }
        });

        pTrackLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + pName.getText().toString() + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
    }
}