package com.example.travelandtourismguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    EditText w_email,w_password;
    Button w_loginBtn;
    TextView w_signUp;
    ProgressBar w_pgbar;
    FirebaseAuth firebAuth;
    boolean passwordVisible = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        try {
            if (firebAuth.getCurrentUser()!=null){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }
        catch (Exception e){
            Log.d("99999", "onCreate: Error occured");
        }


        w_email = findViewById(R.id.LP_input_email);
        w_password = findViewById(R.id.LP_input_pass);
        w_loginBtn = findViewById(R.id.LP_login_btn);
        w_signUp = findViewById(R.id.LP_SignUp);
        w_pgbar = findViewById(R.id.LP_progressbar);

        firebAuth = FirebaseAuth.getInstance();

        w_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        w_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = w_email.getText().toString().trim();
                String password = w_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    w_email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    w_password.setError("Password is Required");
                    return;
                }
                if(password.length()<=6){
                    w_password.setError("Password is to short");
                    return;
                }

                w_pgbar.setVisibility(View.VISIBLE);

                firebAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            w_pgbar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginPage.this,"Error Occurred "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            w_pgbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}