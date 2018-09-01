package com.example.preetham.organic;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VegeActivity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseUser currentFirebaseUser;
    FirebaseDatabase database1;

    TextView name_of_veg,infor,price_of_veg;

    LinearLayout layout;


ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vege);
//action bar color
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#43A047")));


        name_of_veg=(TextView)findViewById(R.id.name);

        price_of_veg = (TextView) findViewById(R.id.price);


        database1 = FirebaseDatabase.getInstance();

        //currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database1.getReference("Vegetables").child("carrot");
        // Toast.makeText(this,currentFirebaseUser.getUid(),Toast.LENGTH_SHORT).show();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                //String info = dataSnapshot.child("info").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();


                //Run this now okay
                // Log.i("nameaddrs",name);

                // name_of_veg=(EditText)findViewById(R.id.name);


                name_of_veg.setText(name);
                //infor.setText(info);
                price_of_veg.setText("Per KG:"+price);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            });

        layout=(LinearLayout)findViewById(R.id.carrotlayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent i=new Intent(VegeActivity.this,CarrotActivity.class);
startActivity(i);
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
