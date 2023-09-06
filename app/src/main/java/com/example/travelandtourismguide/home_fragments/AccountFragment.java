package com.example.travelandtourismguide.home_fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.travelandtourismguide.LoginPage;
import com.example.travelandtourismguide.MainActivity;
import com.example.travelandtourismguide.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    CircleImageView circleImageView;
    ImageView change_image, back_btn;
    TextView name, first, last, email, gender;
    AppCompatButton  change_password;
    TextView logout;

    FirebaseFirestore ff;
    CollectionReference cr;
    DocumentReference dr;
    FirebaseAuth fa;
    FirebaseUser fuser;
    StorageReference sr;
    ActivityResultLauncher<String> launcher;
    Uri imageUri, downloadUri;
    ProgressDialog p;
    UploadTask uptask;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_account, container, false);

        circleImageView = cl.findViewById(R.id.acc_profile_image);
        change_image = cl.findViewById(R.id.acc_change_image);
        name = cl.findViewById(R.id.acc_user_name);
        first = cl.findViewById(R.id.acc_firstName);
        last = cl.findViewById(R.id.acc_lastName);
        email = cl.findViewById(R.id.acc_email);
        gender = cl.findViewById(R.id.acc_gender);

        change_password = cl. findViewById(R.id.acc_btn_change_password);
        logout = cl. findViewById(R.id.acc_text_logout);
        back_btn = cl.findViewById(R.id.acc_back_btn);

        ff = FirebaseFirestore.getInstance();
        cr = ff.collection("Users");
        fa = FirebaseAuth.getInstance();
        sr = FirebaseStorage.getInstance().getReference("ProfilePics");

        p = new ProgressDialog(getActivity());
        p.setTitle("Please Wait");


        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                circleImageView.setImageURI(result);
                imageUri = result;

                String sname = name.getText().toString()+" ProfilePic";

                p = new ProgressDialog(getActivity());
                p.setTitle("Please wait");
                p.show();
                StorageReference str = sr.child(sname);

                uptask = str.putFile(imageUri);
                Task<Uri> uriTask = uptask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return str.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadUri = task.getResult();
                            fuser = FirebaseAuth.getInstance().getCurrentUser();
                            HashMap<String,Object> hash = new HashMap<>();
                            hash.put("ProfilePic",downloadUri.toString());

                            ff.collection("Users").document(fuser.getUid().toString()).update(hash);
                            p.dismiss();
                            circleImageView.setImageURI(result);
                            Toast.makeText(getActivity(), "Image Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Unable to upload Image", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = email.getText().toString();
                fa.sendPasswordResetEmail(emailId).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Reset Email is send.\nPlease check your email", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginPage.class));
                        getActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error Occured "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });
        return cl;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("999999", "1 onStart: ");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = fuser.getUid();
        Log.d("999999", "2 onStart: "+userId);


        dr =  cr.document(userId);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              DocumentSnapshot value = task.getResult();

                String gen = value.getString("Gender");
                String mail = value.getString("Email");
                String imgUrl = value.getString("ProfilePic");

                String Username = value.getString("FullName");
                String[] names = Username.split(" ");
                String fname = names[0];
                String lname = names[names.length-1];
                Uri iuri = Uri.parse(imgUrl);

                first.setText(fname);
                last.setText(lname);
                email.setText(mail);
                gender.setText(gen);
                name.setText(Username);

                Picasso.get().load(iuri).into(circleImageView);
            }
        });

        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
                circleImageView.setImageURI(imageUri);



            }
        });
            }

    public void logOut(){
        fa.signOut();
        startActivity(new Intent(getActivity(), LoginPage.class));
        getActivity().finish();
    }
}