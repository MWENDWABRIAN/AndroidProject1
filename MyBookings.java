package com.example.barbercustomer;

import static android.Manifest.permission_group.SMS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyBookings extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
   private EditText editTextPhone, editTextMessage;
   private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        editTextPhone = findViewById(R.id.edttphone);
        editTextMessage = findViewById(R.id.edtMessage);
        sendBtn = findViewById(R.id.btnsend);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        sendSMS();
                    }else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }

            }
        });

        bottomNavigationView = findViewById(R.id.bottomnavigator);
        bottomNavigationView.setSelectedItemId(R.id.bookings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.book) {
                    startActivity(new Intent(getApplicationContext(), Book.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.bookings) {
                    return true;
                } else {
                    Toast.makeText(MyBookings.this, "Invalid navigation item selected!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void sendSMS() {
        String phoneNo = editTextPhone.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(MyBookings.this, "Message sent successfully", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MyBookings.this, "Failed to send message", Toast.LENGTH_LONG).show();
        }
    }

}
