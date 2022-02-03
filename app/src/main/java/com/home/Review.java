package com.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import database.DBReview;

public class Review extends AppCompatActivity {

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;

    EditText edtcomentar;
    RatingBar mratingreview;
    Button msubmit, mcancel;

    float reting = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mratingreview = findViewById(R.id.ratingreview);
        edtcomentar   = findViewById(R.id.edtcomentar);
        msubmit       = findViewById(R.id.btnsubmit);
        mcancel       = findViewById(R.id.btncancel);

        mratingreview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                int rating = (int) v;
                String message = null;

                reting = ratingBar.getRating();

                switch (rating){
                    case 1:
                        message = "Sangat Jelek :(";
                        break;
                    case 2:
                        message = "Jelek!";
                        break;
                    case 3:
                        message = "Cukup bagus";
                        break;
                    case 4:
                        message = "Sangat bagus! Terimakasih";
                        break;
                    case 5:
                        message = "Sangat sangat bagus! Terimakasih";
                        break;
                }

                Toast.makeText(Review.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpandata();
                Toast.makeText(Review.this, String.valueOf(reting), Toast.LENGTH_SHORT).show();
                }
            });

        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
    }

    private void simpandata(){
        rootnode  = FirebaseDatabase.getInstance("https://home-7e2a4.firebaseio.com");
        reference = rootnode.getReference("Review");

        Toast.makeText(Review.this, "Terimakasih sudah memberikan masukan kepada jasa layanan kami !! Terimakasih", Toast.LENGTH_LONG).show();

        String commentar = edtcomentar.getText().toString();
        DBReview dbReview = new DBReview(commentar);

        reference.child(commentar).setValue(dbReview);

    }

}