package com.example.preetham.organic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText edittextemail, edittextpassword;
    FirebaseUser currentFirebaseUser;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {

    }

    private void loginuser(){
        String email= edittextemail.getText().toString().trim();
        String password= edittextpassword.getText().toString().trim();
        currentFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();

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
        progressDialog.setMessage("Loading ....");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i= new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        edittextemail =(EditText)findViewById(R.id.usernamel);
        edittextpassword =(EditText)findViewById(R.id.passwordl);

        Button login=(Button)findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });

        TextView signup=(TextView)findViewById(R.id.signupnav);
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });



    }


}
