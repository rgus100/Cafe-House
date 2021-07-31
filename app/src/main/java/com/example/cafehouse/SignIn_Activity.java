package com.example.cafehouse;

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

public class SignIn_Activity extends AppCompatActivity {

    EditText userID;
    EditText passwordID;
    Button btn_login;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        userID= findViewById(R.id.userID);
        passwordID=findViewById(R.id.passwordID);
        btn_login = findViewById(R.id.btn_login);
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = userID.getText().toString();
                String Password = passwordID.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();
                
                if(Email.isEmpty()){
                    userID.setError("Please enter your email ID");
                    userID.requestFocus();
                }else if(Password.isEmpty()){
                    passwordID.setError("Please enter your password");
                    passwordID.requestFocus();
                }else if(!Email.isEmpty() && !Password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignIn_Activity.this, "Login successful :)", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignIn_Activity.this, homeActivity.class));
                            }else{
                                Toast.makeText(SignIn_Activity.this, "Login failed, You entered wrong details :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}