package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Customer_Navbar extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView setName;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_navbar);

        materialToolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.customer_drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        setName = findViewById(R.id.header_name);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email").toString();
        //System.out.println(email);


        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.customer_navbar: {
                        Intent intent = new Intent(Customer_Navbar.this, Customer_Navbar.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.apply_centers: {
                        Intent intent = new Intent(Customer_Navbar.this, ApplyCenterOne.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.all_centers: {
                        Intent intent = new Intent(Customer_Navbar.this, AllCentersCustomer.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.all_apply_vaccine: {
                        Intent intent = new Intent(Customer_Navbar.this, AllApplyVaccineCenters.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.my_profile: {
                        Intent intent = new Intent(Customer_Navbar.this, MyProfile.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        break;
                    }
                    case R.id.log_out: {
                        Intent intent = new Intent(Customer_Navbar.this, Login.class);
                        startActivity(intent);
                        break;
                    }

                    default:
                        return true;
                }
                return true;
            }
        });

    }
}