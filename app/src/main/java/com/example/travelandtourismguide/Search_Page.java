package com.example.travelandtourismguide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.adapters.SearchViewBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelandtourismguide.firebase_database.DataFirebase;
import com.example.travelandtourismguide.firebase_database.PlaceRetriver;
import com.example.travelandtourismguide.firebase_database.SearchObjects;
import com.example.travelandtourismguide.home_fragments.HomeFragment;
import com.example.travelandtourismguide.home_fragments.RecyclerViewAdapter;
import com.example.travelandtourismguide.main_recyclerAdapters.mainTypesRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Search_Page extends AppCompatActivity  {

    SearchView searchView;
    TextView noResult;
    RecyclerView recycler;
    ImageView back_btn;
    mainTypesRecyclerAdapter adpter;
    ArrayList<PlaceRetriver> lis;
    PlaceRetriver placeModal;
    FirebaseFirestore ff;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        back_btn = findViewById(R.id.search_back_btn);
        searchView = findViewById(R.id.SP_searchView);
        recycler = findViewById(R.id.SP_recyclerView);
        noResult = findViewById(R.id.search_no_result);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

       ff = FirebaseFirestore.getInstance();
       adpter = new mainTypesRecyclerAdapter(getApplicationContext(), HomeFragment.lis);
       recycler.setAdapter(adpter);
       adpter.notifyDataSetChanged();
       lis = new ArrayList<>();


       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

           @Override
           public boolean onQueryTextSubmit(String s) {

               adpter = new mainTypesRecyclerAdapter(getApplicationContext(), lis);
               recycler.setAdapter(adpter);
               lis.removeAll(lis);
               String[] tys = DataFirebase.CATEGORIES;
               for(String col : tys){
                   ff.collection(col).whereEqualTo("search",s).get()
                           .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                               @Override
                               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                   for (QueryDocumentSnapshot snap : queryDocumentSnapshots){
                                       noResult.setVisibility(View.INVISIBLE);
                                       recycler.setVisibility(View.VISIBLE);
                                       placeModal = snap.toObject(PlaceRetriver.class);
                                       lis.add(placeModal);
                                       if(lis.size()>=2){
                                           lis.remove(1);
                                       }
                                       adpter.notifyDataSetChanged();

                                   }
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                               }
                           });
               }
               if(lis.size()==0){
                   recycler.setVisibility(View.INVISIBLE);
                   noResult.setVisibility(View.VISIBLE);
               }
               else {
                   noResult.setVisibility(View.INVISIBLE);
                   recycler.setVisibility(View.VISIBLE);
               }
               return true;
           }
           @Override
           public boolean onQueryTextChange(String s) {
               return true;
           }
       });

       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
               startActivity(new Intent(Search_Page.this, MainActivity.class));
           }
       });
    }
}