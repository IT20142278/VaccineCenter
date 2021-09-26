package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vaccinecenter.model.Symptom;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddSymptoms extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    public static final String ADD_SYMPTOM_PREFIX = "syid";
    TextInputEditText sName, description, rarity, effectedHours, percentage, url;
    int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptoms);

        sName = findViewById(R.id.input_symptom_nameEdit);
        description = findViewById(R.id.input_descriptionEdit);
        rarity = findViewById(R.id.input_rarityEdit);
        effectedHours = findViewById(R.id.input_effected_hoursEdit);
        percentage = findViewById(R.id.input_percentageEdit);
        url = findViewById(R.id.input_urlEdit);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("symptomDetails");
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
    }

    public void onAddClick(View view) {

        String symptomName = sName.getText().toString();
        String sDescription = description.getText().toString();
        String sRarity = rarity.getText().toString();
        String sEffectedHours = effectedHours.getText().toString();
        String sPercentage = percentage.getText().toString();
        String sUrl = url.getText().toString();

        if(symptomName.isEmpty() || symptomName == null){
            sName.setError("Symptom name must be filled");
        }
        if(sDescription.isEmpty() || sDescription == null){
            description.setError("Description must be filled");
        }
        if(sRarity.isEmpty() || sRarity == null){
            rarity.setError("Select Rarity");
        }
        if(sEffectedHours.isEmpty() || sEffectedHours == null){
            effectedHours.setError("Enter Effected Hours");
        }
        if(sPercentage.isEmpty() || sPercentage == null){
            percentage.setError("Enter Percentage");
        }
        if(sUrl.isEmpty() || sUrl == null){
            url.setError("Enter Percentage");
        }
        else{
            addSymptomData();
        }

    }

    public void addSymptomData(){

        String symptomId;
        String symptomName = sName.getText().toString();
        String sDescription = description.getText().toString();
        String sRarity = rarity.getText().toString();
        String sEffectedHours = effectedHours.getText().toString();
        String sPercentage = percentage.getText().toString();
        String sUrl = url.getText().toString();


        //Val

        if(symptomName.isEmpty() || symptomName == null){
            sName.setError("Symptom name must be filled");
        }

        if(!sEffectedHours.matches("^[0-9].*")){
            effectedHours.setError("Enter hours in numerics");
        }

        if(sRarity.isEmpty() || sRarity == null){
            rarity.setError("Select Rarity");
        }

        if(!sDescription.matches("^[0-9A-Za-z!@\\.;:'\"?-]{1,1000}$")){
            description.setError("Description should not exceed 1000 characters");
        }

        if(!sPercentage.matches("^\\b(?<!\\.)(?!0+(?:\\.0+)?%)(?:\\d|[1-9]\\d|100)(?:(?<!100)\\.\\d+)?%")){
            percentage.setError("Enter a valid percentage eg: 50%");
        }

        if(sUrl.isEmpty() || sUrl == null) {
            url.setError("Enter Percentage");
        }

        else{
            String sId = String.valueOf(ID);
            String id = ADD_SYMPTOM_PREFIX + ID;
            symptomId = id;

            Symptom addSymptoms = new Symptom(symptomId, symptomName, sDescription, sRarity, sEffectedHours, sPercentage, sUrl);
            reference.child(sId).setValue(addSymptoms);

            Toast.makeText(this, "Symptom added :"+id, Toast.LENGTH_SHORT).show();

            Intent addingSymp = new Intent(AddSymptoms.this, AddSymptoms.class);
            startActivity(addingSymp);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        callIntent.setClass(AddSymptoms.this,SymptomList.class);
        startActivity(callIntent);
    }
}