package com.example.travelandtourismguide.home_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travelandtourismguide.R;
import com.example.travelandtourismguide.firebase_database.Recommendations_modal;
import com.example.travelandtourismguide.main_recyclerAdapters.recommendationsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager2 vp;

    List<Recommendations_modal> lis;
    Recommendations_modal modal;
    recommendationsAdapter ra;
    FirebaseFirestore ff;
    public RecommendationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendationsFragment newInstance(String param1, String param2) {
        RecommendationsFragment fragment = new RecommendationsFragment();
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
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_recommendations, container, false);


        ff = FirebaseFirestore.getInstance();

        vp = cl.findViewById(R.id.reels_viewpager2);
        lis = new ArrayList<>();
        ra = new recommendationsAdapter(getContext(),lis);
        vp.setAdapter(ra);

        ff.collection("Videos").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()){
                            modal = new Recommendations_modal(snapshot.getString("videoUri"),snapshot.getId(), snapshot.getString("description"), snapshot.getString("place"),snapshot.getBoolean("favourite"));
                            lis.add(modal);
                            ra.notifyDataSetChanged();
                        }
                    }
                });


        return cl;
    }
}