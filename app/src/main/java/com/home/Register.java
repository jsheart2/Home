package com.home;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mUsername, mEmail, mPassword, mphone;
    Button mPendaftaran;
    TextView mMasuk;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername    = findViewById(R.id.edtusername);
        mEmail       = findViewById(R.id.edtemail);
        mPassword    = findViewById(R.id.edtpassword);
        mPendaftaran = findViewById(R.id.btn_register);
        mphone       = findViewById(R.id.edtphone);
        mMasuk       = findViewById(R.id.txt_masuk);

        fAuth        = FirebaseAuth.getInstance();
        fStore       = FirebaseFirestore.getInstance();
        progressBar  = findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        mPendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email    = mEmail.getText().toString().trim();
                String password       = mPassword.getText().toString().trim();
                final String username = mUsername.getText().toString();
                final String phone    = mphone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required!!");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required!!");
                    return;
                }

                if (password.length() <6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // pendaftaran user di firebase

                fAuth.createUserWithEmailAndPassword(email,password) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // mengirim link untuk verifikasi email pendaftaran

                            FirebaseUser User = fAuth.getCurrentUser();
                            User.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Pembuatan Akun Berhasil", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "" + e.getMessage());
                                }
                            });
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user =new HashMap<>();
                            user.put("email",email);
                            user.put("Name",username);
                            user.put("telepon",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for  "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }else  {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
