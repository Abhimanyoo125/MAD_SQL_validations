package com.example.mad_sql_validations;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, mobileEditText, emailEditText;
    private Button submitButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize EditTexts and Button
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        mobileEditText = findViewById(R.id.editTextMobile);
        emailEditText = findViewById(R.id.editTextEmail);
        submitButton = findViewById(R.id.buttonSubmit);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set onClick listener for Submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                // Perform validations
                if (firstName.isEmpty()) {
                    firstNameEditText.setError("First name is required");
                    firstNameEditText.requestFocus();
                } else if (lastName.isEmpty()) {
                    lastNameEditText.setError("Last name is required");
                    lastNameEditText.requestFocus();
                } else if (mobile.isEmpty() || mobile.length() != 10) {
                    mobileEditText.setError("Enter a valid 10-digit mobile number");
                    mobileEditText.requestFocus();
                } else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Enter a valid email");
                    emailEditText.requestFocus();
                } else {
                    // Insert data into SQLite database
                    boolean isInserted = dbHelper.insertData(firstName, lastName, mobile, email);
                    if (isInserted) {
                        Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Data insertion failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Clear all fields after submission
    private void clearFields() {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        mobileEditText.setText("");
        emailEditText.setText("");
    }
}
