package com.example.vaccinecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vaccinecenter.AdapterClasses.AllVacineAdapter;
import com.example.vaccinecenter.model.AddCenters;
import com.example.vaccinecenter.model.ApplyVaccine;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AllApplyVaccineCenters extends AppCompatActivity {

    RecyclerView recyclerView;
    AllVacineAdapter allVacineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apply_vaccine_centers);

        recyclerView = (RecyclerView) findViewById(R.id.recview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ApplyVaccine> options =
                new FirebaseRecyclerOptions.Builder<ApplyVaccine>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ApplyDetails"), ApplyVaccine.class)
                        .build();

        allVacineAdapter = new AllVacineAdapter(options);
        recyclerView.setAdapter(allVacineAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        allVacineAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        allVacineAdapter.stopListening();
    }
}