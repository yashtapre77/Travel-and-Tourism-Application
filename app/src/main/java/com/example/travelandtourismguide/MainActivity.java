package com.example.travelandtourismguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import com.example.travelandtourismguide.home_fragments.AccountFragment;
import com.example.travelandtourismguide.home_fragments.FavouriteFragment;
import com.example.travelandtourismguide.home_fragments.HomeFragment;
import com.example.travelandtourismguide.home_fragments.RecommendationsFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation w_mbn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        w_mbn = findViewById(R.id.M_bottom_navigation);

        w_mbn.add(new MeowBottomNavigation.Model(1,R.drawable.home_24));
        w_mbn.add(new MeowBottomNavigation.Model(2,R.drawable.surfing_24));
        w_mbn.add(new MeowBottomNavigation.Model(3,R.drawable.favorite_24));
        w_mbn.add(new MeowBottomNavigation.Model(4,R.drawable.account_circle_24));

        w_mbn.show(1,true);
        replace(new HomeFragment());
        w_mbn.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;
                    case 2:
                        replace(new RecommendationsFragment());
                        break;
                    case 3:
                        replace(new FavouriteFragment());
                        break;
                    case 4:
                        replace(new AccountFragment());
                        break;
                }
                return null;
            }
        });
    }

    private void replace(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.M_frame_layout,frag);
        ft.commit();
    }
}