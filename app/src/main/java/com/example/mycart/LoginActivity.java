package com.example.mycart;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.Model.Users;
import com.example.mycart.Model.userdata;
import com.example.mycart.NetworkCall.Api;
import com.example.mycart.NetworkCall.ApiLinks;
import com.example.mycart.NetworkCall.RetrofitClient;
import com.example.mycart.Utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView newAccountTv,resetPassTv;
    Button loginButton;
    EditText etUser,etPass;
    ProgressBar progressBar;
    SharedPreferences mySharedPreference;
    private String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        //underline text
        newAccountTv.setPaintFlags(newAccountTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        resetPassTv.setPaintFlags(resetPassTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //make clickable
        loginButton.setOnClickListener(this);
        newAccountTv.setOnClickListener(this);
        resetPassTv.setOnClickListener(this);


    }

    private void init(){
        mySharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        newAccountTv = findViewById(R.id.newAccountTv);
        resetPassTv = findViewById(R.id.passTv);
        loginButton = findViewById(R.id.loginButton);
        etUser = findViewById(R.id.etUserName);
        etPass = findViewById(R.id.etPassword);
        progressBar= findViewById(R.id.progressBar);


    }
    @Override
    public void onClick(View view) {
        if (view == loginButton){
            if(etUser.getText().toString().equals("")){
                CommonUtils.showCustomDialog(this,"Alert !","Username cannot be empty",false);
            }else if (etPass.getText().toString().equals("")){
                CommonUtils.showCustomDialog(this,"Alert !","Password cannot be empty",false);
            }
            else {
                login(etUser.getText().toString(),etPass.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
            }
        }
        else if (view == newAccountTv){

            Intent intent = new Intent(getApplicationContext(),EnterOTPActivity.class);
            intent.putExtra("code","1");
            startActivity(intent);

        }else if (view == resetPassTv){

            Intent intent = new Intent(getApplicationContext(),EnterOTPActivity.class);
            intent.putExtra("code","2");
            startActivity(intent);

        }

    }

    private void login(String username,String password){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",username);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //"https://newprojectjs.herokuapp.com/users/login"
        Api api = RetrofitClient.getRetrofitClient().create(Api.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        final Call<Users> call = api.getLogin(ApiLinks.Login_Url,body);


        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    List<userdata> users = response.body().getUserdata();
                    String message = response.body().getMessage();
                    int code = response.body().getCode();

                    if (code == 0){
                        loginAndSaveData(users);
                    }
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.e("Failed",t.getMessage().toString());
            }
        });

    }

    private void loginAndSaveData(List<userdata> users){
               startActivity(new Intent(LoginActivity.this,HomeActivity.class));
               finish();
               SharedPreferences.Editor editor = mySharedPreference.edit();
               editor.putString("login","1");
               editor.putString("fName",users.get(0).getfName());
               editor.putString("lName",users.get(0).getlName());
               editor.putString("contactId",users.get(0).getUserId());
               editor.putString("gender",users.get(0).getGender());
               editor.apply();

    }

}
