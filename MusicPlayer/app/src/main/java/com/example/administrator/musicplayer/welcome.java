package com.example.administrator.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;



public class welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(welcome.this,MainActivity.class));
                finish();

            }
        }, 3000);
    }
}
