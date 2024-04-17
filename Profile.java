package com.example.barbercustomer;

import static com.bumptech.glide.Glide.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.barbercustomer.Book;
import com.example.barbercustomer.MainActivity;
import com.example.barbercustomer.MyBookings;
import com.example.barbercustomer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class Profile extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final String PREF_PROFILE_IMAGE_URI = "profile_image_uri";
    private static final String PREF_NAME = "name";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PHONE = "phone";

    private ImageView profileImage;
    private EditText nameEditText, emailEditText, phoneEditText;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottomnavigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        profileImage = findViewById(R.id.profileImage);
        nameEditText = findViewById(R.id.profilenameEditText);
        emailEditText = findViewById(R.id.profileemailEditText);
        phoneEditText = findViewById(R.id.profilephoneEditText);

        Button changePhotoButton = findViewById(R.id.changePhotoButton);
        Button saveButton = findViewById(R.id.saveButton);

        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        loadProfile(); // Load profile from SharedPreferences

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.profile) {
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
                    startActivity(new Intent(getApplicationContext(), MyBookings.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    Toast.makeText(Profile.this, "Invalid navigation item selected!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        // Set circular background for profile image
        profileImage.setBackgroundResource(R.drawable.circle_background);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Load the image using Glide and apply circular transformation
                with(this)
                        .load(bitmap)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImage);
                saveImageToSharedPreferences(bitmap); // Save profile image to SharedPreferences
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProfile() {
        // Get entered details
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        // Save the entered details to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_NAME, name);
        editor.putString(PREF_EMAIL, email);
        editor.putString(PREF_PHONE, phone);
        editor.apply();

        // Update the UI with the entered details
        updateProfileDetails(name, email, phone);

        // Hide the save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setVisibility(View.GONE);
        // Display a Toast message indicating the profile has been saved
        Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show();
    }

    private void saveImageToSharedPreferences(Bitmap bitmap) {
        // Convert Bitmap to Uri
        Uri imageUri = getImageUriFromBitmap(bitmap);

        // Save the image URI to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_PROFILE_IMAGE_URI, imageUri.toString());
        editor.apply();
    }

    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Profile Image", null);
        return Uri.parse(path);
    }

    private void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String name = preferences.getString(PREF_NAME, "");
        String email = preferences.getString(PREF_EMAIL, "");
        String phone = preferences.getString(PREF_PHONE, "");

        // Set the loaded profile details
        nameEditText.setText(name);
        emailEditText.setText(email);
        phoneEditText.setText(phone);

        // Load and set profile image
        String imageUriString = preferences.getString(PREF_PROFILE_IMAGE_URI, null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            // Load the image using Glide and apply circular transformation
            with(this)
                    .load(imageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage);
        }
    }

    private void updateProfileDetails(String name, String email, String phone) {

        // Set the loaded profile details
        nameEditText.setText(name);
        emailEditText.setText(email);
        phoneEditText.setText(phone);

        // Make EditText fields non-editable
        nameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        phoneEditText.setEnabled(false);
    }
}
