package com.example.barbercustomer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class OwnerMain extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageViewUploaded;
    private Button buttonUploadImages;
    private EditText editTextServices, editTextAddress, editTextContact;
    private Button buttonSave;

    private Uri imageUri;

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

        buttonUploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
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

        // Make the "Save" button invisible
        buttonSave.setVisibility(View.INVISIBLE);

        // For demonstration purpose, displaying a toast message
        Toast.makeText(this, "Details saved successfully!", Toast.LENGTH_SHORT).show();
    }
}
