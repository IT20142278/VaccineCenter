package com.example.vaccinecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vaccinecenter.AdapterClasses.AllCentersAdapter;
import com.example.vaccinecenter.model.AddCenters;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class AllAddCenters extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fb;
    FloatingActionButton fba;
    AllCentersAdapter allCentersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_add_centers);
        setTitle("Search here..");

        recyclerView = (RecyclerView) findViewById(R.id.recview);
        fb = (FloatingActionButton) findViewById(R.id.fadd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AddCenters> options =
                new FirebaseRecyclerOptions.Builder<AddCenters>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("centerDetails"), AddCenters.class)
                        .build();

        allCentersAdapter = new AllCentersAdapter(options);
        recyclerView.setAdapter(allCentersAdapter);

        fb = (FloatingActionButton) findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(AllAddCenters.this, AddVaccineCenter.class);
               startActivity(intent);
            }
        });

        fba = (FloatingActionButton) findViewById(R.id.fadb);
        fba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllAddCenters.this, Admin_NavBar.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        allCentersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        allCentersAdapter.stopListening();
    }
}