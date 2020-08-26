package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;
import com.example.mycart.Utils.CommonUtils;

public class MainActivity extends AppCompatActivity {

    SharedPreferences mySharedPreferences;
    private String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTable();

        mySharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        CommonUtils.deleteCartTable(this,QueryClass.TABLE_ITEMSCART);
        CommonUtils.deleteCartTable(this,QueryClass.TABLE_MAIN_ITEMS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mySharedPreferences.getString("login","").equals("1")){
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }

                finish();
            }
        },1000);


    }


    private void createTable(){
        try{
            SqlDataStore sqlDataStore = new SqlDataStore(this);
            sqlDataStore.open();
            sqlDataStore.createtable(QueryClass.TABLE_ITEMSCART,QueryClass.CREATE_ITEMSCART);
            sqlDataStore.createtable(QueryClass.TABLE_MAIN_ITEMS,QueryClass.CREATE_ITEMSMAIN);

            sqlDataStore.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Log.e("Log","TABLES CREATED");
        }
    }

    // TODO: 2020-05-10 For smartphone apps, please set a link to https://icons8.com in the About dialog or settings.
    //
    //Also, please credit our work in your App Store or Google Play description (something like "Icons by Icons8" is fine). 
}
