package com.example.preetham.organic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Place_Order_Activity extends AppCompatActivity {

    DatabaseReference myRef,myRef2;
    FirebaseUser currentFirebaseUser;
    FirebaseDatabase database1;

    TextView nm,addr,con,tot_amt;

    Button ctn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__order_);


        nm=(TextView)findViewById(R.id.name);
        addr=(TextView)findViewById(R.id.address);
        con=(TextView)findViewById(R.id.contact);
        tot_amt=(TextView)findViewById(R.id.tot_amount);

        database1 = FirebaseDatabase.getInstance();
        currentFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        myRef = database1.getReference("Users").child(currentFirebaseUser.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address=dataSnapshot.child("address").getValue().toString();
                String name=dataSnapshot.child("name").getValue().toString();
                String contact=dataSnapshot.child("number").getValue().toString();
                String email=dataSnapshot.child("emailid").getValue().toString();

                nm.setText("Delivererd in the name of:"+name);
                addr.setText("Address"+address);
                con.setText("Contact"+contact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef2 = database1.getReference("Users").child(currentFirebaseUser.getUid()).child("orders").child("carrot_123");
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tot=dataSnapshot.child("total_amount").getValue().toString();
                tot_amt.setText("Total Payable Amount"+tot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ctn=(Button)findViewById(R.id.nav_home);
        ctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Place_Order_Activity.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }
}
