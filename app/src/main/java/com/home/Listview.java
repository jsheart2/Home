package com.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.BokingAdapter;
import database.DataBoking;

public class Listview extends AppCompatActivity {
    private DatabaseReference reference;

    ArrayList<DataBoking> list;
    BokingAdapter adapter;

    private RecyclerView mRecyler;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        mRecyler = findViewById(R.id.list_boking);
        mRecyler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecyler.setLayoutManager(mManager);

        reference = FirebaseDatabase.getInstance().getReference().child("Boking User");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    DataBoking boking = dataSnapshot1.getValue(DataBoking.class);
                    list.add(boking);
                }
                adapter = new BokingAdapter(getApplicationContext(), list);
                mRecyler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void kembali(View view) {
        startActivity(new Intent(getApplicationContext(), Home.class));
        finish();
    }
}