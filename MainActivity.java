package com.example.barbercustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView tview;
    FirebaseUser user;
    FirebaseAuth auth;
BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        tview= findViewById(R.id.txtwelcome);

        if (user== null){
            Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            tview.setText("WELCOME  " +user.getEmail());
        }

        bottomNavigationView= findViewById(R.id.bottomnavigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if (itemId == R.id.home) {
                    return true;
                }else if (itemId == R.id.book) {
                    startActivity(new Intent(getApplicationContext(), Book.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.bookings) {
                    startActivity(new Intent(getApplicationContext(), MyBookings.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    Toast.makeText(MainActivity.this, "Invalid navigation item selected!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

}
