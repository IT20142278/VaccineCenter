package com.example.vaccinecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vaccinecenter.AdapterClasses.AllCentersAdapter;
import com.example.vaccinecenter.AdapterClasses.AllCustomerCentersAdapter;
import com.example.vaccinecenter.model.AddCenters;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class AllCentersCustomer extends AppCompatActivity {

    RecyclerView recyclerView;
    AllCustomerCentersAdapter allCustomerCentersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_centers_customer);

        recyclerView = (RecyclerView) findViewById(R.id.recview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AddCenters> options =
                new FirebaseRecyclerOptions.Builder<AddCenters>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("centerDetails"), AddCenters.class)
                        .build();

        allCustomerCentersAdapter = new AllCustomerCentersAdapter(options);
        recyclerView.setAdapter(allCustomerCentersAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        allCustomerCentersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        allCustomerCentersAdapter.stopListening();
    }
}


