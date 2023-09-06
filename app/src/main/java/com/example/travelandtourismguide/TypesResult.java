package com.example.travelandtourismguide;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelandtourismguide.firebase_database.PlaceRetriver;
import com.example.travelandtourismguide.main_recyclerAdapters.mainTypesRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class TypesResult extends AppCompatActivity {
    TextView tittle;
    String Type;
    RecyclerView recycler;
    PlaceRetriver modal;
    ImageView back;

    FirebaseFirestore ff;
    CollectionReference cr;
    ArrayList<PlaceRetriver> lis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_result);
        tittle = findViewById(R.id.typeRes_title);
        back = findViewById(R.id.type_back_btn);

        Intent in = getIntent();
        Type = in.getStringExtra("TypeName");
        tittle.setText(tittle.getText().toString()+" "+Type);


        ff = FirebaseFirestore.getInstance();
        cr = ff.collection(Type);
        lis = new ArrayList<>();

        recycler = findViewById(R.id.types_recyclerView);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        mainTypesRecyclerAdapter adapter = new mainTypesRecyclerAdapter(TypesResult.this,lis);
        recycler.setAdapter(adapter);

        cr.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    modal = queryDocumentSnapshot.toObject(PlaceRetriver.class);
                    lis.add(modal);
                    Log.d("9999999", "onSuccess: "+lis.size());
                    Log.d("9999999", "modal  : "+modal.getName());
                    adapter.notifyDataSetChanged();
                }

//                Log.d("9999999", "onSuccess: "+lis.get(2).getMainImage());
                Log.d("99999999", "onSuccess: Objects successfully Retrived");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("99999999", "OnFailureListener Unable to Retrive");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(TypesResult.this, MainActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}