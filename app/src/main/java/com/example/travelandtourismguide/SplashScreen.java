package com.example.travelandtourismguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseAuth fa = FirebaseAuth.getInstance();
        FirebaseUser user = fa.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user == null){
                    startActivity(new Intent(getApplicationContext(),LoginPage.class));
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        },3000);
    }
}