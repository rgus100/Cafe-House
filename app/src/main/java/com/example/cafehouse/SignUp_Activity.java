package com.example.cafehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Activity extends AppCompatActivity {

    TextView alreadylogin;
    EditText username;
    EditText phone;
    EditText emailID;
    EditText passwordT;
    Button btn_register;
    FirebaseAuth firebaseAuth;

    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");

        alreadylogin = findViewById(R.id.alreadylogin);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        emailID = findViewById(R.id.emailID);
        passwordT = findViewById(R.id.password);
        btn_register=findViewById(R.id.btn_register);


        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        alreadylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_Activity.this,SignIn_Activity.class);
                startActivity(intent);
            }
        });
        
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String password = passwordT.getText().toString();
//                String name = username.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();


                if(email.isEmpty()){
                    emailID.setError("Please enter your email ID");
                    emailID.requestFocus();
                }else if(password.isEmpty()){
                    passwordT.setError("Enter your password");
                    passwordT.requestFocus();
                }else if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull  Task<AuthResult> task) {


                            if(!task.isSuccessful()){
                                Toast.makeText(SignUp_Activity.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent2 = new Intent(SignUp_Activity.this, homeActivity.class);
                                startActivity(intent2);

                                Toast.makeText(SignUp_Activity.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            Toast.makeText(this, "Hi user", Toast.LENGTH_SHORT).show();
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();

                Toast.makeText(this, "User email" + personEmail, Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(SignUp_Activity.this, homeActivity.class));
            finish();
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

            Log.d("signInResult:failed", e.toString());
        }
    }
}