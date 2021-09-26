package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaccinecenter.model.AddCenters;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class AddVaccineCenter extends AppCompatActivity {

    public static final String ADD_CENTER_PREFIX = "VC-00";
    TextInputEditText cName, doctor, hours, address, phoneNo;
    CircularProgressButton AddCenters;
    int ID = 0;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine_center);

        cName = findViewById(R.id.textInputEdit2);
        doctor = findViewById(R.id.textInputEdit3);
        hours = findViewById(R.id.textInputEdit4);
        address = findViewById(R.id.textInputEdit5);
        phoneNo = findViewById(R.id.textInputEdit6);

        progressDialog = new ProgressDialog(this);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("centerDetails");
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

        fb = (FloatingActionButton) findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddVaccineCenter.this, Admin_NavBar.class);
                startActivity(intent);
            }
        });

    }

    public void onLoginClick(View view) {

        String centerName = cName.getText().toString();
        String centerDoctor = doctor.getText().toString();
        String centerHours = hours.getText().toString();
        String centerAddress = address.getText().toString();
        String centerPhone = phoneNo.getText().toString();

        if (centerName.isEmpty() || centerName == null) {
            cName.setError("Center Name must be filled.");
        }
        if (centerDoctor.isEmpty() || centerDoctor == null) {
            doctor.setError("Available Doctor must be filled.");
        }
        if (centerHours.isEmpty() || centerHours == null) {
            hours.setError("Working Hours must be filled.");
        }
        if (centerAddress.isEmpty() || centerAddress == null) {
            address.setError("Center Address must be filled.");
        }
        if (centerPhone.isEmpty() || centerPhone == null) {
            phoneNo.setError("Center Phone No must be filled.");
        } else {

            addCentersDetails();
        }

    }


    private void addCentersDetails() {

        String centerName = cName.getText().toString();
        String centerDoctor = doctor.getText().toString();
        String centerHours = hours.getText().toString();
        String centerAddress = address.getText().toString();
        String centerPhone = phoneNo.getText().toString();

        if (!centerHours.matches("^[0-9].*")) {
            hours.setError("Please Enter valid working hours");
        }
        if (!centerPhone.matches("\\d{10}")) {
            phoneNo.setError("Phone number must be a 10 digits.");
        } else {

            String cID = String.valueOf(ID);

            String id = ADD_CENTER_PREFIX + ID;
            System.out.println("id" + ID);
            AddCenters addCenters = new AddCenters(id, centerName, centerDoctor, centerHours, centerAddress, centerPhone);
            reference.child(cID).setValue(addCenters);
            Toast.makeText(this, "Center is added successfully.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddVaccineCenter.this, AddVaccineCenter.class);
            startActivity(intent);
        }

    }

}