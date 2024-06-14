package com.example.barbercustomer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class OwnerMain extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageViewUploaded;
    private Button buttonUploadImages;
    private EditText editTextServices, editTextAddress, editTextContact;
    private Button buttonSave,btnview,btnlogout;

    private Uri imageUri;
    private boolean isEditingMode = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);

        imageViewUploaded = findViewById(R.id.imageViewUploaded);
        buttonUploadImages = findViewById(R.id.buttonUploadImages);
        editTextServices = findViewById(R.id.editTextServices);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextContact = findViewById(R.id.editTextContact);
        buttonSave = findViewById(R.id.buttonSave);
        btnview= findViewById(R.id.buttonview);
        bottomNavigationView = findViewById(R.id.bottomnavigatorowner);

        btnlogout= findViewById(R.id.logoutmainButton);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                btnlogout.setBackgroundColor(Color.BLUE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnlogout.setBackgroundColor(Color.GREEN);
                    }
                },1000);

                Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerMain.this,OwnerBookings.class));
                //finish();
            }
        });

        buttonUploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditingMode) {
                    saveDetails();
                } else {
                    enableEditingMode();
                }
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.addinfo);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bookings) {
                    startActivity(new Intent(getApplicationContext(), OwnerBookings.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.addinfo) {
                    return true;
                } else if (itemId == R.id.call) {
                    startActivity(new Intent(getApplicationContext(), OwnerCall.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    Toast.makeText(OwnerMain.this, "Invalid navigation item selected!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewUploaded.setImageURI(imageUri);
            imageViewUploaded.setVisibility(View.VISIBLE);
        }
    }

    private void saveDetails() {
        String services = editTextServices.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String contact = editTextContact.getText().toString().trim();

        // Disable editing in EditText fields
        editTextServices.setEnabled(false);
        editTextAddress.setEnabled(false);
        editTextContact.setEnabled(false);

        // Change the text of the button
        buttonSave.setText("Edit");

        // Update the editing mode state
        isEditingMode = false;

        // For demonstration purpose, displaying a toast message
        Toast.makeText(this, "Details saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void enableEditingMode() {
        // Enable editing in EditText fields
        editTextServices.setEnabled(true);
        editTextAddress.setEnabled(true);
        editTextContact.setEnabled(true);

        // Change the text of the button
        buttonSave.setText("Save");

        // Update the editing mode state
        isEditingMode = true;
    }
}
