package com.example.vaccinecenter;

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

import com.example.vaccinecenter.model.ApplyVaccine;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplyCenterOne extends AppCompatActivity {
    Button buttnNext;
    ApplyVaccine applyVaccine;
    EditText NIC, Does, Address, District;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_center_one);

        NIC = findViewById(R.id.NICNumber);
        Does = findViewById(R.id.does);
        Address = findViewById(R.id.address);
        District = findViewById(R.id.district);

        buttnNext = findViewById(R.id.buttonNext);

        buttnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nic = NIC.getText().toString();
                String does = Does.getText().toString();
                String address = Address.getText().toString();
                String district = District.getText().toString();

                String does1 = "1";
                String does2 = "2";

                if (nic.isEmpty() || nic == null) {
                    NIC.setError("NIC must be filled.");
                }
                if (does.isEmpty() || does == null) {
                    Does.setError("Does must be filled.");
                }
                if (address.isEmpty() || address == null) {
                    Address.setError("Address must be filled.");
                }
                if (district.isEmpty() || district == null) {
                    District.setError("District must be filled.");
                }

                if((!does.equals(does1)) && (!does.equals(does2))  ){
                    Does.setError("Please enter valid does type. It must be 1 or 2.");
                }else {
                    Intent intent = new Intent(getApplicationContext(), ApplyCenterTwo.class);
                    intent.putExtra("nic", nic);
                    intent.putExtra("does", does);
                    intent.putExtra("address", address);
                    intent.putExtra("district", district);
                    startActivity(intent);
                }
            }
        });
    }


}