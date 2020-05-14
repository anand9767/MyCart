package com.example.mycart;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.Utils.CommonUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView newAccountTv;
    Button loginButton;
    EditText etUser,etPass;

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
        etUser = findViewById(R.id.etUserName);
        etPass = findViewById(R.id.etPassword);


    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            if (etUser.getText().toString().equals("7972716830") && etPass.getText().toString().equals("123456")){
               startActivity(new Intent(LoginActivity.this,HomeActivity.class));

            }else if(etUser.getText().toString().equals("")){
                CommonUtils.showCustomDialog(this,"Alert !","Username cannot be empty");
            }else if (etPass.getText().toString().equals("")){
                CommonUtils.showCustomDialog(this,"Alert !","Password cannot be empty");
            }
            else {
                CommonUtils.showCustomDialog(this,"Alert !","Incorrect username or password ");
            }
        }
    }

}
