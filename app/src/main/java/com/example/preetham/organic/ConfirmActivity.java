package com.example.preetham.organic;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmActivity extends AppCompatActivity {

    Button nav_home,nav_cart;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#43A047")));

        nav_home=(Button)findViewById(R.id.to_home);
        nav_cart=(Button)findViewById(R.id.to_cart);

        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(ConfirmActivity.this,HomeActivity.class);
                startActivity(i1);
            }
        });

        nav_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(ConfirmActivity.this,CartActivity.class);
                startActivity(i2);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
