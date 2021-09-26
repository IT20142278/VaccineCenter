package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaccinecenter.model.AddCenters;
import com.example.vaccinecenter.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

public class MyProfile extends AppCompatActivity {

    public static final String ADD_USER_PREFIX = "VCU-00";
    TextView myEmail, userEmail;
    EditText userName, phoneNo, address;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    Button button, deleteButton;
    ProgressDialog progressDialog;
    int ID = 0;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myEmail = findViewById(R.id.email);
        userEmail = findViewById(R.id.textEmail);
        button = findViewById(R.id.submitButton);
        userName = findViewById(R.id.username);
        phoneNo = findViewById(R.id.phonetext);
        address = findViewById(R.id.homeText);
        deleteButton = findViewById(R.id.deleteButton);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email").toString();
//        String uid = intent.getStringExtra("uid").toString();

        myEmail.setText(email);
        userEmail.setText(email);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        fb = (FloatingActionButton) findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, Customer_Navbar.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("userDetails");
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String userPhone = phoneNo.getText().toString();
                String userAddress = address.getText().toString();

                if (user.isEmpty() || user == null) {
                    TastyToast.makeText(getApplicationContext(), "User Name can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                }

                if (userPhone.isEmpty() || userPhone == null) {
                    TastyToast.makeText(getApplicationContext(), "Phone number can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                }
                if (userAddress.isEmpty() || userAddress == null) {
                    TastyToast.makeText(getApplicationContext(), "Address can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                } else {

                    addUserDetails();
                }

            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(MyProfile.this);
                dialog.setTitle("Are you sure, do you want to delete this account? ");
                dialog.setMessage("Deleting this " + email + " account is removed permanently.");

                progressDialog.setMessage("Please Wait While Registration Complete.");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    progressDialog.dismiss();
                                    TastyToast.makeText(getApplicationContext(), "Account deleted Successfully.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                } else {

                                    progressDialog.dismiss();
                                    TastyToast.makeText(getApplicationContext(), "" + task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                }
                            }
                        });
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        progressDialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

    }

    private void addUserDetails() {

        Intent intent = getIntent();
        String email = intent.getStringExtra("email").toString();
//        String uid = intent.getStringExtra("uid").toString();

        String user = userName.getText().toString();
        String userPhone = phoneNo.getText().toString();
        String userAddress = address.getText().toString();

        if (!userPhone.matches("\\d{10}")) {
            phoneNo.setError("Phone number must be a 10 digits.");
        } else {

            String id = ADD_USER_PREFIX + ID;
            System.out.println("id" + ID);
            Profile profile = new Profile(id, email, user, userPhone, userAddress);
            reference.child(id).setValue(profile);
            TastyToast.makeText(getApplicationContext(), "Details Added Successfully.", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

            Intent intent1 = new Intent(MyProfile.this, MyProfile.class);
            startActivity(intent1);
        }

    }
}