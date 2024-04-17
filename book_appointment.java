package com.example.barbercustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

public class BookAppointment extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText bdate;
    DatePickerDialog datePickerDialog;
    Button btnTime;
    Button btnBook;
    TextView tvservices;
    TextInputEditText etname, etaddress, etcontact;
    int hour, minute;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        bdate = findViewById(R.id.datebook);
        btnTime = findViewById(R.id.buttontime);
        btnBook = findViewById(R.id.buttonbook);
        tvservices= findViewById(R.id.tvviewservices);
        etname= findViewById(R.id.fullnamebook);
        etaddress= findViewById(R.id.addressbook);
        etcontact= findViewById(R.id.contactbook);

        tvservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookAppointment.this, Services.class);
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etname.getText().toString();
                String address = etaddress.getText().toString();
                String contact = etcontact.getText().toString();
                String date = bdate.getText().toString();


                // Check if any field is empty
                if (fullName.isEmpty() || address.isEmpty() || contact.isEmpty() || date.isEmpty()) {
                    // Display an error message indicating all fields are required
                    Toast.makeText(BookAppointment.this, "Please fill in all fields and select date/time", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(BookAppointment.this, Book.class);
                    Toast.makeText(BookAppointment.this, "Booked Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(BookAppointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        bdate.setText(dayOfMonth + "/" + (month + 1 + "/" + year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(v);
            }
        });
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                btnTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}
