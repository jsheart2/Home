package com.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mButton;
    TextView mLupapassword, mPendaftaran;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail        = findViewById(R.id.edtusername);
        mPassword     = findViewById(R.id.edtpassword);
        progressBar   = findViewById(R.id.progressBar);
        fAuth         = FirebaseAuth.getInstance();
        mButton       = findViewById(R.id.btn_login);
        mLupapassword = findViewById(R.id.forgotpassword);
        mPendaftaran  = findViewById(R.id.btn_pendaftaran);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email    = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

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

                // keaslian user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }else  {
                            Toast.makeText(Login.this, "Login Tidak Berhasil!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mPendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        mLupapassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Masukan Email terdaftar dan cek di email untuk pembaharuan password");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // menemukan email dan mengirim reset link

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset password akan dikirimkan ke email anda", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error ! Reset link tidak dikirim" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }
}