package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mycart.Fragments.Adapter.SampleFragmentPagerAdapter;
import com.example.mycart.Fragments.CurrentOrdersFragment;
import com.example.mycart.Fragments.GroceryFragment;
import com.example.mycart.Fragments.RecentOrdersFragment;
import com.example.mycart.Fragments.VegetablesFragment;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.Utils.CommonUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    SharedPreferences mySharedPreferences;
    TextView name,number;
    private String MyPREFERENCES = "MyPrefs";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mySharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        viewPager = findViewById(R.id.viewpagerProfile);
        tabLayout = findViewById(R.id.sliding_tabsProfile);
        name= findViewById(R.id.name);
        number = findViewById(R.id.number);


        //setProfile name
        name.setText(mySharedPreferences.getString("fName","")+" "+mySharedPreferences.getString("lName",""));
        number.setText(mySharedPreferences.getString("contactId",""));

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        com.example.mycart.Fragments.Adapter.SampleFragmentPagerAdapter adapter = new com.example.mycart.Fragments.Adapter.SampleFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CurrentOrdersFragment(), "Current Orders");
        adapter.addFragment(new RecentOrdersFragment(), "Recent Orders");
        viewPager.setAdapter(adapter);
    }

    public void backpressed(View view) {
        onBackPressed();
    }

    public void LogOut(View view) {
//        CommonUtils.showCustomDialog(this,"Alert !","Are You Sure You Want to Log out",true);

        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("login","0");
        editor.apply();
        Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
        CommonUtils.deleteCartTable(this, QueryClass.TABLE_ITEMSCART);

    }

    public void addressClick(View view) {

        startActivity(new Intent(getApplicationContext(),AddAddress.class));
    }



}
