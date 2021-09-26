package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.vaccinecenter.AdapterClasses.SymptomAdapter;
import com.example.vaccinecenter.model.Symptom;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SymptomList extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fb;
    SymptomAdapter symptomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_list);

        recyclerView = (RecyclerView) findViewById(R.id.symptomList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fb = (FloatingActionButton) findViewById(R.id.fbadd);


        FirebaseRecyclerOptions<Symptom> options =
                new FirebaseRecyclerOptions.Builder<Symptom>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("symptomDetails"), Symptom.class)
                        .build();

        symptomAdapter = new SymptomAdapter(options);
        recyclerView.setAdapter(symptomAdapter);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddSymptoms.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        symptomAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        symptomAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                txtSearch(query);
                return false;


            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private  void txtSearch(String str){

        FirebaseRecyclerOptions<Symptom> options =
                new FirebaseRecyclerOptions.Builder<Symptom>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("symptomDetails").orderByChild("symptomName").startAt(str).endAt(str+"~"), Symptom.class)
                        .build();

        symptomAdapter = new SymptomAdapter(options);
        symptomAdapter.startListening();
        recyclerView.setAdapter(symptomAdapter);

    }


}