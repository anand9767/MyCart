package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        },1000);


    }

    // TODO: 2020-05-10 For smartphone apps, please set a link to https://icons8.com in the About dialog or settings.
    //
    //Also, please credit our work in your App Store or Google Play description (something like "Icons by Icons8" is fine). 
}
