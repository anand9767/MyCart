package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView newAccountTv;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        newAccountTv.setPaintFlags(newAccountTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //make clickable
        loginButton.setOnClickListener(this);


    }

    private void init(){
        newAccountTv = findViewById(R.id.newAccountTv);
        loginButton = findViewById(R.id.loginButton);


    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }

    }
}
