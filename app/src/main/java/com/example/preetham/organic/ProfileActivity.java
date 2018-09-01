package com.example.preetham.organic;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    ActionBar actionBar;

    DatabaseReference myRef;
    FirebaseUser currentFirebaseUser;
    FirebaseDatabase database1;

    EditText user,addr,con,emailid;
    Button update,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#43A047")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        save=(Button)findViewById(R.id.savebutton);

        database1 = FirebaseDatabase.getInstance();

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database1.getReference("Users").child(currentFirebaseUser.getUid());
        //Toast.makeText(this,currentFirebaseUser.getUid(),Toast.LENGTH_SHORT).show();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              String address=dataSnapshot.child("address").getValue().toString();
              String name=dataSnapshot.child("name").getValue().toString();
              String contact=dataSnapshot.child("number").getValue().toString();
              String email=dataSnapshot.child("emailid").getValue().toString();

              //Run this now okay
             // Log.i("address,name",address+name);

              user=(EditText)findViewById(R.id.username);
              addr=(EditText)findViewById(R.id.address);
              con=(EditText)findViewById(R.id.contact);
              emailid=(EditText)findViewById(R.id.email);

              user.setText(name);
              addr.setText(address);
              emailid.setText(email);
              con.setText(contact);

              update=(Button)findViewById(R.id.updatebutton);
              update.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                        Intent i = new Intent(ProfileActivity.this,ProfileUpdateActivity.class);
                      startActivity(i);
                  }


              });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
