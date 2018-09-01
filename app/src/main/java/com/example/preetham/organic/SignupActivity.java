package com.example.preetham.organic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends Activity {
EditText edittextemail, edittextpassword ,edittextuser,edittextcontact,edittextaddr;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    private FirebaseDatabase database1;
    private DatabaseReference myRef1;
    private FirebaseUser currentFirebaseUser;

    @Override
    public void onBackPressed() {

    }


    private void registeruser(){
       final String email= edittextemail.getText().toString().trim();
       String password= edittextpassword.getText().toString().trim();
        final String username= edittextuser.getText().toString().trim();
        final String contact= edittextcontact.getText().toString().trim();
        final String address= edittextaddr.getText().toString().trim();

        if (email.isEmpty()) {
            edittextemail.setError("Email is required");
            edittextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edittextemail.setError("Please enter a valid email");
            edittextemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edittextpassword.setError("Password is required");
            edittextpassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edittextpassword.setError("Minimum lenght of password should be 6");
            edittextpassword.requestFocus();
            return;
        }
progressDialog.setMessage("Registering User");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this,"User Successfully Registered",Toast.LENGTH_LONG).show();

                    database1 = FirebaseDatabase.getInstance();
                    myRef1 = database1.getReference("Users");
                    currentFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                    Log.d("uid","cuid"+currentFirebaseUser.getUid());

                    myRef1.child(currentFirebaseUser.getUid()).child("name").setValue(username);
                    myRef1.child(currentFirebaseUser.getUid()).child("number").setValue(contact);
                    myRef1.child(currentFirebaseUser.getUid()).child("address").setValue(address);
                    myRef1.child(currentFirebaseUser.getUid()).child("emailid").setValue(email);

                    Intent i =new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(i);

                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }}
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        edittextemail =(EditText)findViewById(R.id.email);
        edittextpassword =(EditText)findViewById(R.id.passwords);
        edittextuser =(EditText)findViewById(R.id.username);
        edittextcontact=(EditText)findViewById(R.id.contact);
        edittextaddr=(EditText)findViewById(R.id.address);

        Button signup=(Button)findViewById(R.id.signupbutton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });


        TextView login=(TextView)findViewById(R.id.loginnav);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
