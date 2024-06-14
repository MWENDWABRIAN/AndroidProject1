package com.example.barbercustomer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class BookAppointment extends AppCompatActivity {
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String ampm;
    EditText bdate;
    DatePickerDialog datePickerDialog;
    Button btnTime;
    Button btnBook;
    TextView tvservices;
    TextInputEditText etname, etaddress, etcontact, ettime;
    DatabaseReference databaseUsers;
    int hour, minute;
    private static final int PERMISSION_REQUEST_SEND_SMS = 123;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        bdate = findViewById(R.id.datebook);
        ettime = findViewById(R.id.edtchoosetime);
        btnBook = findViewById(R.id.buttonbook);
        tvservices = findViewById(R.id.tvviewservices);
        etname = findViewById(R.id.fullnamebook);
        etaddress = findViewById(R.id.addressbook);
        etcontact = findViewById(R.id.contactbook);
        databaseUsers = FirebaseDatabase.getInstance().getReference();

        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(BookAppointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            ampm = "PM";
                        } else {
                            ampm = "AM";
                        }
                        ettime.setText(String.format("%02d:%02d", hourOfDay, minute) + ampm);
                        ettime.setText(hourOfDay + ":" + minute + ampm);

                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });

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
                String fullName = etname.getText().toString().trim();
                String address = etaddress.getText().toString().trim();
                String contact = etcontact.getText().toString().trim();
                String date = bdate.getText().toString().trim();
                String time = ettime.getText().toString().trim();

                // Check if any field is empty
                if (fullName.isEmpty() || address.isEmpty() || contact.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    // Display an error message indicating all fields are required
                    Toast.makeText(BookAppointment.this, "Please fill in all fields and select date/time", Toast.LENGTH_SHORT).show();
                    return; // Return to stop further processing
                }

                // Check if the contact number is exactly 10 digits long and contains only digits
                if (!TextUtils.isDigitsOnly(contact) || contact.length() != 10) {
                    Toast.makeText(BookAppointment.this, "Please enter a valid 10-digit contact number", Toast.LENGTH_SHORT).show();
                    etcontact.setError("Please enter a valid 10-digit contact number");
                    return; // Return to stop further processing
                }

                // If all validations pass, proceed with booking and send SMS
                InsertData(fullName, address, contact, date, time);
            }

            private void InsertData(String name, String address, String contact, String date, String time) {
                String id = databaseUsers.push().getKey();
                User user = new User(name, address, contact, date, time);

                databaseUsers.child("users").child(id).setValue(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Booking successful, send SMS
                                    sendSMS(contact, date, time);
                                    Toast.makeText(BookAppointment.this, "Booked Successfully", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

            private void sendSMS(String contact, String date, String time) {
                String message = "Your appointment is scheduled on " + date + " at " + time + ".";
                if (ContextCompat.checkSelfPermission(BookAppointment.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(contact, null, message, null, null);
                    Toast.makeText(BookAppointment.this, "Message sent successfully.Check your inbox", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(BookAppointment.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
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
