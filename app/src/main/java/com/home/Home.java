package com.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Home extends AppCompatActivity {

    TextView dashboard;
    LinearLayout boking, bokinglist, review;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fAuth      = FirebaseAuth.getInstance();
        fStore     = FirebaseFirestore.getInstance();

        userID     = fAuth.getCurrentUser().getUid();

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//keluar dari account
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void boking(View view) {
        startActivity(new Intent(getApplicationContext(), boking.class));
        finish();
    }

    public void Review(View view) {
        startActivity(new Intent(getApplicationContext(), Review.class));
        finish();
    }

    public void bokinglist(View view) {
        startActivity(new Intent(getApplicationContext(), Listview.class));
        finish();
    }
}
