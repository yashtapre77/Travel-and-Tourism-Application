package com.example.travelandtourismguide.home_fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelandtourismguide.R;
import com.example.travelandtourismguide.Search_Page;
import com.example.travelandtourismguide.TypesResult;
import com.example.travelandtourismguide.firebase_database.DataFirebase;
import com.example.travelandtourismguide.firebase_database.PlaceRetriver;
import com.example.travelandtourismguide.main_recyclerAdapters.SliderAdapterExample;
import com.example.travelandtourismguide.main_recyclerAdapters.mainPopularRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment implements Serializable {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AppCompatButton w_btn;

    SliderView sv;
    int[] ids = {R.drawable.main_top5,R.drawable.main_top4,R.drawable.main_top3,R.drawable.main_top2,R.drawable.main_top1};
    SliderAdapterExample adapter ;

    RecyclerView rvPopular;
    mainPopularRecyclerAdapter popularRA;
    ArrayList<PlaceRetriver> populars;
    String[]  docs = {"Radhangar Beach ","Taj Mahal ","Juhu Beach ","Jantar Mantar","Red Fort","Haridwar ","Shirdi","Jog Falls "};

    SearchView searchView;
    ProgressDialog p;
    FirebaseFirestore ff;
    public static ArrayList<PlaceRetriver> lis;
    PlaceRetriver placeModal;
    static TextView text;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_home, container, false);
        ff = FirebaseFirestore.getInstance();

        //Top Image Slider
        sv = cl.findViewById(R.id.main_top_slider_view);
        adapter = new SliderAdapterExample(getContext(),ids);
        sv.setSliderAdapter(adapter);
        sv.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sv.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sv.startAutoCycle();

        //Middle Popular Places
        rvPopular = cl.findViewById(R.id.main_Popular_recycler_View);
        rvPopular.setHasFixedSize(true);
        rvPopular.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        populars = new ArrayList<>();
//        String[] typs = DataFirebase.CATEGORIES;
        String[] typs = {"Beaches ", "Monuments ","Beaches ", "Educational", "Monuments ", "Religious ", "Religious ", "WaterFalls "};
        int i = 0;
        for(String str : typs){
            ff.collection(str).document(docs[i]).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            placeModal = documentSnapshot.toObject(PlaceRetriver.class);
                            populars.add(placeModal);
                            popularRA.notifyDataSetChanged();
                        }
                    });
            i = i+1;
        }
        popularRA = new mainPopularRecyclerAdapter(getContext(),populars);
        rvPopular.setAdapter(popularRA);

        //SearchView
        lis = new ArrayList<>();
        p = new ProgressDialog(getContext());
        p.setTitle("Searching ...");
        searchView = cl.findViewById(R.id.main_search_view);
        searchView.clearFocus();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                p.show();
                String[] tys = DataFirebase.CATEGORIES;
                for (String str:tys){
                    ff.collection(str).whereEqualTo("search",s.toLowerCase()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for(QueryDocumentSnapshot snapshot: task.getResult()){
                                        lis.add(snapshot.toObject(PlaceRetriver.class));
                                        adapter.notifyDataSetChanged();
                                        p.dismiss();
                                        if(lis.size()>=2){
                                            lis.remove(1);
                                        }
                                        break;
                                    }
                                }
                            });
                }
                Intent in = new Intent(getContext(), Search_Page.class);
                startActivity(in);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        // Types Listeners
        CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10;
        card1 = cl.findViewById(R.id.card1_beaches);
        card2 = cl.findViewById(R.id.card2_hillstation);
        card3 = cl.findViewById(R.id.card3_waterfalls);
        card4 = cl.findViewById(R.id.card4_monuments);
        card5 = cl.findViewById(R.id.card5_islands);
        card6 = cl.findViewById(R.id.card6_religious);
        card7 = cl.findViewById(R.id.card7_wildlifeparks);
        card8 = cl.findViewById(R.id.card8_educational);
        card9 = cl.findViewById(R.id.card9_historical);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.BeachesText);
                gotoTypesPage(text.getText().toString());
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text2_hillstation);
                gotoTypesPage(text.getText().toString());
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text3_waterfalls);
                gotoTypesPage(text.getText().toString());
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text4_monuments);
                gotoTypesPage(text.getText().toString());
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text5_islands);
                gotoTypesPage(text.getText().toString());
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text6_religious);
                gotoTypesPage(text.getText().toString());
            }
        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text7_wildlifeparks);
                gotoTypesPage(text.getText().toString());
            }
        });

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text8_educational);
                gotoTypesPage(text.getText().toString());
            }
        });

        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = cl.findViewById(R.id.text9_sporty);
                gotoTypesPage(text.getText().toString());
            }
        });


        return cl;
    }

    public void gotoTypesPage(String type){
        Intent in = new Intent(getActivity(), TypesResult.class);
        in.putExtra("TypeName",type);
        startActivity(in);
    }
}