package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vaccinecenter.model.AddCenters;
import com.example.vaccinecenter.model.ApplyVaccine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplyCenterTwo extends AppCompatActivity {

    public static final String ADD_CENTER_PREFIX = "VAP-00";
    EditText Division, Age, Occupation, Gender;
    int ID = 0;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    Button Apply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_center_two);


        Division = findViewById(R.id.divisionGram);
        Age = findViewById(R.id.divisionGram);
        Occupation = findViewById(R.id.divisionGram);
        Gender = findViewById(R.id.divisionGram);
        Apply = findViewById(R.id.buttonSubmit);

        Intent intent = getIntent();
        String NIC = intent.getStringExtra("nic").toString();
        String Does = intent.getStringExtra("does").toString();
        String Address = intent.getStringExtra("address").toString();
        String District = intent.getStringExtra("district").toString();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("ApplyDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID = (int) snapshot.getChildrenCount();
                System.out.println(ID);
                ID = ID + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String div = Division.getText().toString();
                String age = Age.getText().toString();
                String occu = Occupation.getText().toString();
                String gender = Gender.getText().toString();


                if (div.isEmpty() || div == null) {
                    Division.setError("Division of Grama Niladhari must be filled.");
                }
                if (age.isEmpty() || age == null) {
                    Age.setError("Age must be filled.");
                }
                if (occu.isEmpty() || occu == null) {
                    Occupation.setError("Occupation must be filled.");
                }
                if (gender.isEmpty() || gender == null) {
                    Gender.setError("Gender must be filled.");
                }else {
                    String aID = String.valueOf(ID);

                    String id = ADD_CENTER_PREFIX + ID;

                    ApplyVaccine applyVaccine = new ApplyVaccine(id, NIC, Does, Address, District, div, age, occu, gender);
                    reference.child(aID).setValue(applyVaccine);
                    Toast.makeText(ApplyCenterTwo.this, "Apply is added successfully.", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(ApplyCenterTwo.this, Customer_Navbar.class);
                    startActivity(intent);
                }
            }
        });
    }
}