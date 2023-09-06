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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText w_fullName,w_email,w_password;
    Button w_signUp;
    TextView w_go_signIn;
    ProgressBar w_progressbar;
    RadioGroup w_radioGroup;
    RadioButton w_selectedRadio;

    public static final String TAG = "TAG";
    FirebaseFirestore fbfs ;
    String userid;
    FirebaseAuth fbAuth ;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    w_fullName = findViewById(R.id.RG_input_fullName);
    w_email = findViewById(R.id.RG_input_email);
    w_password = findViewById(R.id.RG_input_pass);
    w_radioGroup = findViewById(R.id.RG_radioGroup);

    w_signUp = findViewById(R.id.RG_register_btn);
    w_go_signIn = findViewById(R.id.RG_logIn);
    w_progressbar = findViewById(R.id.RG_progressbar);

    fbAuth = FirebaseAuth.getInstance();
    fbfs = FirebaseFirestore.getInstance();

    w_go_signIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),LoginPage.class));
        }
    });

    w_signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = w_email.getText().toString().trim();
            String password = w_password.getText().toString().trim();
            String fullName = w_fullName.getText().toString().trim();
            w_selectedRadio =  (RadioButton)findViewById(w_radioGroup.getCheckedRadioButtonId());
            String gender = w_selectedRadio.getText().toString();
            String profilePic = "https://firebasestorage.googleapis.com/v0/b/travel-and-tourism-guide.appspot.com/o/Images%2FProfilePicture?alt=media&token=c5343870-0cd1-4a3f-9f86-9e8967868ec5";

            if(TextUtils.isEmpty(fullName)){
                w_fullName.setError("Name is Required");
                return;
            }
            if(TextUtils.isEmpty(email)){
                w_email.setError("Email is Required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                w_password.setError("Password is Required");
                return;
            }
            if(password.length()<=6){
                w_password.setError("Password is to short");
                return;
            }
            w_progressbar.setVisibility(View.VISIBLE);

            fbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser fbUser = fbAuth.getCurrentUser();
                        fbUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Register.this,"Registered Succcessfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onFailure : Email not Sent"+e.getMessage());
                            }
                        });

                        Map<String,Object> obj = new HashMap<>();
                        obj.put("Email",email);
                        obj.put("Password",password);
                        obj.put("FullName",fullName);
                        obj.put("Gender",gender);
                        obj.put("ProfilePic",profilePic);

                        String userId = fbUser.getUid();

                        fbfs.collection("Users").document(userId).set(obj)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                      System.out.println();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Register.this,"Error in Adding to Database",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else{
                        Toast.makeText(Register.this,"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        w_progressbar.setVisibility(View.GONE);
                    }
                }
            });

        }
    });
    }

     public void func_back_to_login(View view) {
        startActivity(new Intent(getApplicationContext(),LoginPage.class));

    }
}