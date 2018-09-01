package com.example.preetham.organic;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity {

    DatabaseReference myRef,myRef2;
    FirebaseUser currentFirebaseUser;
    FirebaseDatabase database1;

    TextView ord_id,nm,quant,tot_price,out_tot;
    Button place;

    String ordid,totalAmount;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#43A047")));

        ord_id=(TextView)findViewById(R.id.order_id);
        nm=(TextView)findViewById(R.id.name);
        quant=(TextView)findViewById(R.id.quantity);
        tot_price=(TextView)findViewById(R.id.total_price);
        place=(Button) findViewById(R.id.place_order);

        database1 = FirebaseDatabase.getInstance();
        currentFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        myRef2 = database1.getReference("Users").child(currentFirebaseUser.getUid()).child("orders");

       myRef2.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ordid=dataSnapshot.child("carrot_123").getKey().toString();
                ord_id.setText(ordid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef2 = database1.getReference("Users").child(currentFirebaseUser.getUid()).child("orders").child("carrot_123");

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name_vege=dataSnapshot.child("name_of_vegetable").getValue().toString();
                String qnty= dataSnapshot.child("quantity").getValue().toString();
                totalAmount= dataSnapshot.child("total_amount").getValue().toString();

                nm.setText(name_vege);
                quant.setText("Quantity="+qnty);
                tot_price.setText("Total Amount="+totalAmount);
                out_tot=(TextView)findViewById(R.id.outstanding_total);
                out_tot.setText("Outstanding Total="+totalAmount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


place.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(CartActivity.this,Place_Order_Activity.class);
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
