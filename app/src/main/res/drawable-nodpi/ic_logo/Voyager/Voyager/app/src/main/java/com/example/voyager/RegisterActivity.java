package com.example.voyager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, number, email, password;
    private Button back, register;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        back = findViewById(R.id.back);
        register = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    database = FirebaseDatabase.getInstance();
                                    myRef = database.getReference("User");
                                    String uploadID = myRef.push().getKey();
                                    myRef.child(uploadID).child("Name").setValue(name.getText().toString().trim());
                                    myRef.child(uploadID).child("Number").setValue(number.getText().toString().trim());
                                    myRef.child(uploadID).child("Email").setValue(email.getText().toString().trim());
                                    Intent intent = new Intent(getApplicationContext(),PhotoActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
