package com.example.travelandtourismguide.main_recyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelandtourismguide.R;
import com.example.travelandtourismguide.Result_page;
import com.example.travelandtourismguide.firebase_database.DataFirebase;
import com.example.travelandtourismguide.firebase_database.PlaceRetriver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainTypesRecyclerAdapter extends RecyclerView.Adapter<mainTypesRecyclerAdapter.Holder> {

    private Context con;
    private ArrayList<PlaceRetriver> list;
    public mainTypesRecyclerAdapter(Context con, ArrayList<PlaceRetriver> lis) {
        Log.d("9999999", "mainTypesRecyclerAdapter: ");
        this.con = con;
        list = lis;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("9999999", "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main_types, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.d("9999999", "onBindViewHolder: " + holder.c1_text.getText().toString() + list.size());
        Log.d("999999", "onBindViewHolder: " + list.get(position).getMainImage());
        Uri uri = Uri.parse(list.get(position).getMainImage());
        Picasso.get().load(uri).into(holder.c1_image);
        holder.c1_text.setText(list.get(position).getName());
        holder.c1_address.setText(list.get(position).getAddress());
        if (list.get(position).isFavourite()) {
            holder.c1_fav.setImageResource(R.drawable.orange_favourite);
            holder.c1_fav.setTag("1");
        } else {
            holder.c1_fav.setImageResource(R.drawable.orange_unfavourite);
            holder.c1_fav.setTag("0");
        }
    }

    @Override
    public int getItemCount() {
        Log.d("999999", "getItemCount: " + list.size());
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView card1;
        ImageView c1_image;
        CircleImageView c1_fav, c1_bgWhite;
        TextView c1_text;
        TextView c1_address;
        FirebaseFirestore ff;
        String[] cats;

        public Holder(@NonNull View itemView) {
            super(itemView);
            Log.d("99999999", "Holder: ");
            itemView.setOnClickListener(this);
            card1 = itemView.findViewById(R.id.typeRes_card1);
            c1_image = itemView.findViewById(R.id.type_card1_image);
            c1_text = itemView.findViewById(R.id.type_card1_placeName);
            c1_address = itemView.findViewById(R.id.type_card1_placeAddress);
            c1_fav = itemView.findViewById(R.id.type_card1_favourite);
            c1_bgWhite = itemView.findViewById(R.id.type_cirularBg);

            ff = FirebaseFirestore.getInstance();
            cats = DataFirebase.CATEGORIES;

            c1_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c1_fav.getTag().toString() == "0") {
                        c1_fav.setImageResource(R.drawable.orange_favourite);
                        c1_fav.setTag("1");
                        for (String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(c1_text.getText().toString());
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
                    } else {
                        c1_fav.setImageResource(R.drawable.orange_unfavourite);
                        c1_fav.setTag("0");
                        for (String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(c1_text.getText().toString());
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
                }
            });

            c1_bgWhite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c1_fav.getTag().toString() == "0") {
                        c1_fav.setImageResource(R.drawable.orange_favourite);
                        c1_fav.setTag("1");
                        for (String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(c1_text.getText().toString());
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
                    } else {
                        c1_fav.setImageResource(R.drawable.orange_unfavourite);
                        c1_fav.setTag("0");
                        for (String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(c1_text.getText().toString());
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
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent in = new Intent(con, Result_page.class);
            in.putExtra("PlaceName", c1_text.getText().toString());
            con.startActivity(in);
        }
    }
}
