package com.example.preetham.organic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_intro);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(IntroActivity.this,LoginActivity.class);
         startActivity(i);}
        },4000);

    }
}
