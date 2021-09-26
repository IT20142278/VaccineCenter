package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class Admin_NavBar extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nav_bar);

        materialToolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.admin_drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

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
                // Navbar Symptom Section
                if(id==R.id.admin_addSymptom){
                    Intent nav_addSymptom = new Intent(Admin_NavBar.this, AddSymptoms.class);
                    startActivity(nav_addSymptom);
                }
                if(id==R.id.admin_allSymptoms){
                    Intent nav_AllSymptoms = new Intent(Admin_NavBar.this, SymptomList.class);
                    startActivity(nav_AllSymptoms);
                }
                
                switch (id) {
                    case R.id.admin_navbar: {
                        Intent intent = new Intent(Admin_NavBar.this, Admin_NavBar.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.admin_customer_addCenters: {
                        Intent intent1 = new Intent(Admin_NavBar.this, AddVaccineCenter.class);
                        startActivity(intent1);
                        break;
                    }
                    case R.id.admin_all_centers:
                        Intent intent2 = new Intent(Admin_NavBar.this, AllAddCenters.class);
                        startActivity(intent2);
                    {
                        break;
                    }

                    case R.id.admin_log_out: {
                        Intent intent = new Intent(Admin_NavBar.this, Login.class);
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