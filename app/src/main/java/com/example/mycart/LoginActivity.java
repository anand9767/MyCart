package com.example.mycart;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences mySharedPreference;
    private String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        newAccountTv.setPaintFlags(newAccountTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //make clickable
        loginButton.setOnClickListener(this);
        newAccountTv.setOnClickListener(this);



    }

    private void init(){
        mySharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
               finish();
               SharedPreferences.Editor editor = mySharedPreference.edit();
               editor.putString("login","1");
               editor.apply();

            }else if(etUser.getText().toString().equals("")){
                CommonUtils.showCustomDialog(this,"Alert !","Username cannot be empty",false);
            }else if (etPass.getText().toString().equals("")){
                CommonUtils.showCustomDialog(this,"Alert !","Password cannot be empty",false);
            }
            else {
                CommonUtils.showCustomDialog(this,"Alert !","Incorrect username or password ",false);
            }
        }
        else if (view == newAccountTv){

            startActivity(new Intent(getApplicationContext(),EnterOTPActivity.class));
        }

    }

}
