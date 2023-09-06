package com.example.travelandtourismguide.main_recyclerAdapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class mainPopularRecyclerAdapter extends RecyclerView.Adapter<mainPopularRecyclerAdapter.Holder> {

    private Context con;
    private ArrayList<PlaceRetriver> lis;


      public mainPopularRecyclerAdapter(Context con, ArrayList<PlaceRetriver> lis) {
        this.con = con;
        this.lis = lis;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main_popular,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.text.setText(lis.get(position).getName());
        holder.address.setText(lis.get(position).getAddress());
        Picasso.get().load(lis.get(position).getMainImage()).into(holder.image);
        if (lis.get(position).isFavourite()) {
            holder.fav.setImageResource(R.drawable.orange_favourite);
            holder.fav.setTag("1");
        } else {
            holder.fav.setImageResource(R.drawable.orange_unfavourite);
            holder.fav.setTag("0");
        }
    }


    @Override
    public int getItemCount() {
        return lis.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image, fav;
        TextView text, address;
        CircleImageView whiteBg;
        FirebaseFirestore ff;
        String[] cats = DataFirebase.CATEGORIES;

        public Holder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            ff = FirebaseFirestore.getInstance();

            image = itemView.findViewById(R.id.MF_popu_image);
            fav = itemView.findViewById(R.id.MF_popu_favourite);
            text = itemView.findViewById(R.id.MF_popu_text);
            address = itemView.findViewById(R.id.MF_popu_address);
            whiteBg = itemView.findViewById(R.id.MF_popu_whiteBack);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fav.getTag().toString() == "0") {
                        fav.setImageResource(R.drawable.orange_favourite);
                        fav.setTag("1");
                        for (String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(text.getText().toString());
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
                        fav.setImageResource(R.drawable.orange_unfavourite);
                        fav.setTag("0");
                        for(String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(text.getText().toString());
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

            whiteBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fav.getTag().toString() == "0") {
                        fav.setImageResource(R.drawable.orange_favourite);
                        fav.setTag("1");
                        for (String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(text.getText().toString());
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
                        fav.setImageResource(R.drawable.orange_unfavourite);
                        fav.setTag("0");
                        for(String str : cats) {
                            DocumentReference docIdRef = ff.collection(str).document(text.getText().toString());
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
        }

        @Override
        public void onClick(View view) {
            Intent in = new Intent(con, Result_page.class);
            in.putExtra("PlaceName",text.getText().toString());
            con.startActivity(in);
        }
    }
}

