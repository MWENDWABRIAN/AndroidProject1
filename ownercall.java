package com.example.barbercustomer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class OwnerCall extends AppCompatActivity {
    EditText phoneNo;
    FloatingActionButton callbtn;
    static int PERMISSION_CODE=100;
    
    private BottomNavigationView bottomNavigationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_call);
        phoneNo= findViewById(R.id.edittextphone);
        callbtn= findViewById(R.id.callbutton);

        if (ContextCompat.checkSelfPermission(OwnerCall.this,
                Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(OwnerCall.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneno=phoneNo.getText().toString();
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phoneno));
                startActivity(intent);
            }
        });

        // Initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomnavigatorowner);
        bottomNavigationView.setSelectedItemId(R.id.call);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bookings) {
                    startActivity(new Intent(getApplicationContext(), OwnerBookings.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.addinfo) {
                    startActivity(new Intent(getApplicationContext(), OwnerMain.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.call) {
                    return true;
                } else {
                    Toast.makeText(OwnerCall.this, "Invalid navigation item selected!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

}
