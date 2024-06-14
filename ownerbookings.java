package com.example.barbercustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerBookings extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;

    private BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OwnerBookings.this, OwnerMain.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_bookings);
        recyclerView = findViewById(R.id.recyclerviewbookings);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        // Read data from Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                adapter.notifyDataSetChanged(); // Notify adapter of data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(OwnerBookings.this, "Failed to read data from database!", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView = findViewById(R.id.bottomnavigatorowner);

        // Set up bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.bookings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bookings) {
                    return true;
                } else if (itemId == R.id.addinfo) {
                    startActivity(new Intent(getApplicationContext(), OwnerMain.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.call) {
                    startActivity(new Intent(getApplicationContext(), OwnerCall.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    Toast.makeText(OwnerBookings.this, "Invalid navigation item selected!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
