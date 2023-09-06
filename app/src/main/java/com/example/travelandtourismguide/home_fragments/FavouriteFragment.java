package com.example.travelandtourismguide.home_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelandtourismguide.MainActivity;
import com.example.travelandtourismguide.R;
import com.example.travelandtourismguide.firebase_database.DataFirebase;
import com.example.travelandtourismguide.firebase_database.PlaceRetriver;
import com.example.travelandtourismguide.main_recyclerAdapters.mainTypesRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    PlaceRetriver modal;
    mainTypesRecyclerAdapter adapter;
    FirebaseFirestore ff;
    ArrayList<PlaceRetriver> lis;
    RecyclerView recycler;
    ImageView back_btn;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_favourite, container, false);

        recycler = cl.findViewById(R.id.fav_recyclerView);
        back_btn = cl.findViewById(R.id.fav_back_btn);

        lis = new ArrayList<>();
        String[] str = DataFirebase.CATEGORIES;
        ff = FirebaseFirestore.getInstance();
        adapter = new mainTypesRecyclerAdapter(getActivity(),lis);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        recycler.setAdapter(adapter);

        for(String col : str){
            ff.collection(col).whereEqualTo("favourite",true).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot snap : queryDocumentSnapshots){
                                  modal = snap.toObject(PlaceRetriver.class);
                                  lis.add(modal);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        return cl;
    }
}