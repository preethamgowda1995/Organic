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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarrotActivity extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseUser currentFirebaseUser;
    FirebaseDatabase database1;
    int i;
    String price,name;
    Integer total;


    TextView name_of_veg,infor,price_of_veg,count_kg,rate_per_kg;

    Button increase,decrease,add;

    Integer counter=1;
    String value;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrot);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#43A047")));
        add=(Button)findViewById(R.id.add_to_cart);
        name_of_veg=(TextView)findViewById(R.id.name);
        infor=(TextView)findViewById(R.id.info);
        price_of_veg = (TextView) findViewById(R.id.price);
        rate_per_kg=(TextView)findViewById(R.id.rate);

        database1 = FirebaseDatabase.getInstance();

        //currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database1.getReference("Vegetables").child("carrot");
        // Toast.makeText(this,currentFirebaseUser.getUid(),Toast.LENGTH_SHORT).show();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name=dataSnapshot.child("name").getValue().toString();
                String info = dataSnapshot.child("info").getValue().toString();
                price = dataSnapshot.child("price").getValue().toString();

                 i=Integer.parseInt(price);
                //rate_per_kg.setText(value);
                //price_n=Integer.parseInt(price);

                name_of_veg.setText(name);
                infor.setText(info);
                price_of_veg.setText("Per KG:"+price);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        increase=(Button)findViewById(R.id.plus);
        decrease=(Button)findViewById(R.id.minus);
        count_kg=(TextView)findViewById(R.id.count);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter=counter+1;
                count_kg.setText(counter.toString());
               total=counter*i;
                value=total.toString();
              rate_per_kg.setText(value);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter=counter-1;
                if(counter<1){
                    counter=1;
                }
                count_kg.setText(counter.toString());
                total=counter*i;
                value=total.toString();
                rate_per_kg.setText(value);
            }
        });

        //rate_per_kg=(TextView)findViewById(R.id.rate);
//Integer price_n=Integer.parseInt(price);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                myRef = database1.getReference("Users").child(currentFirebaseUser.getUid()).child("orders");

                myRef.child("carrot_123").child("name_of_vegetable").setValue(name);
                myRef.child("carrot_123").child("price_of_vegetable").setValue(price);
                myRef.child("carrot_123").child("quantity").setValue(counter);
                myRef.child("carrot_123").child("total_amount").setValue(rate_per_kg.getText().toString());
                Toast.makeText(CarrotActivity.this,"vegetable added to cart",Toast.LENGTH_SHORT).show();
                Intent i= new Intent(CarrotActivity.this,ConfirmActivity.class);
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
