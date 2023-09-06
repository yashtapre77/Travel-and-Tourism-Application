package com.example.travelandtourismguide.home_fragments;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelandtourismguide.R;
import com.example.travelandtourismguide.firebase_database.SearchObjects;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VHolder>{

    private Context con;
    private List<SearchObjects> lis;

    public RecyclerViewAdapter(Context con, List<SearchObjects> lis) {
        this.con = con;
        this.lis = lis;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        SearchObjects so = lis.get(position);
        holder.placeName.setText(so.getPlace());
        holder.placeAddress.setText(so.getAddress());
        Uri uri = Uri.parse(so.getImage());
        holder.placeImage.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
        return lis.size();
    }

    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView placeImage;
        TextView placeName, placeAddress;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            placeImage = itemView.findViewById(R.id.search_place_image);
            placeName = itemView.findViewById(R.id.search_place_name);
            placeAddress = itemView.findViewById(R.id.search_place_address);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
