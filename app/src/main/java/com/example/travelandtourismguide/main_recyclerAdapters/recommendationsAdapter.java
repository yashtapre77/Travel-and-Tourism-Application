package com.example.travelandtourismguide.main_recyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelandtourismguide.R;
import com.example.travelandtourismguide.Result_page;
import com.example.travelandtourismguide.firebase_database.DataFirebase;
import com.example.travelandtourismguide.firebase_database.Recommendations_modal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class recommendationsAdapter extends RecyclerView.Adapter<recommendationsAdapter.holder> {

    private Context con;
    private List<Recommendations_modal> videoList;

    public recommendationsAdapter(Context con, List<Recommendations_modal> videoList) {
        this.con = con;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reels,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.setVideo(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }



    public class holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        VideoView vv;
        TextView w_title, w_description;
        ImageView w_place, w_isFav;
        FirebaseFirestore ff;
        public holder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            vv = itemView.findViewById(R.id.reels_videoview);
            w_title = itemView.findViewById(R.id.reels_video_title);
            w_description = itemView.findViewById(R.id.reels_video_decription);
            w_place = itemView.findViewById(R.id.reel_readmore);
            w_isFav = itemView.findViewById(R.id.reel_isFav);

            ff = FirebaseFirestore.getInstance();

        }

        public void setVideo(Recommendations_modal video){
            Log.d("9999999", "setVideo: "+video.getTitle()+ " No Title");
            w_title.setText(video.getTitle());
            w_description.setText(video.getDescription());
            vv.setVideoURI(Uri.parse(video.getVideoUri()));
            if (video.isFavourite()) {
                w_isFav.setImageResource(R.drawable.orange_favourite);
                w_isFav.setTag("1");
            } else {
                w_isFav.setImageResource(R.drawable.orange_unfavourite);
                w_isFav.setTag("0");
            }

            w_isFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (w_isFav.getTag().toString() == "0") {
                        w_isFav.setImageResource(R.drawable.orange_favourite);
                        w_isFav.setTag("1");
                        DocumentReference docIdRef = ff.collection("Videos").document(w_title.getText().toString());
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
                    } else {
                        w_isFav.setImageResource(R.drawable.orange_unfavourite);
                        w_isFav.setTag("0");
                        DocumentReference docIdRef = ff.collection("Videos").document(w_title.getText().toString());
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
            });


            w_place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent in = new Intent(con.getApplicationContext(), Result_page.class);
                   in.putExtra("PlaceName",video.getPlace());
                    Log.d("999999", "recommendations Adapter "+video.getPlace());
                   con.startActivity(in);
                }
            });

            vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    mediaPlayer.start();
                }
            });
            vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });



        }

        @Override
        public void onClick(View view) {

        }
    }


}
