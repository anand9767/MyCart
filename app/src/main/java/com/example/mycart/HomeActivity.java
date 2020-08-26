package com.example.mycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mycart.Fragments.Adapter.SampleFragmentPagerAdapter;
import com.example.mycart.Fragments.CartFragment;
import com.example.mycart.Fragments.GroceryFragment;
import com.example.mycart.Fragments.VegetablesFragment;
import com.example.mycart.Model.Addresses;
import com.example.mycart.Model.Users;
import com.example.mycart.Model.addrsses;
import com.example.mycart.NetworkCall.Api;
import com.example.mycart.NetworkCall.ApiLinks;
import com.example.mycart.NetworkCall.RetrofitClient;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView imageView;
    Spinner addressSpinner;
    ProgressBar progressBar;
    SharedPreferences mySharedPreferences;
    private int[] tabIcons = {
            R.drawable.vegetablesicon8,
            R.drawable.groceryicons8,
    };
    ArrayList<String> addressArray = new ArrayList<>();
    private String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        mySharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//        Uri uri = Uri.parse("https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX2614542.jpg");
//        Picasso.get().load(uri).placeholder(R.drawable.placeholder).into(imageView);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //for layouts
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Log.e("selected tab",tabLayout.getSelectedTabPosition()+"");
//                if (tabLayout.getSelectedTabPosition() == 1){
//                    Uri uri = Uri.parse("https://i.dlpng.com/static/png/229706_preview.png");
//                    Picasso.get().load(uri).placeholder(R.drawable.placeholder).into(imageView);
////                    imageView.setImageURI(uri);
////                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.grocery_main));
//                }else {
//                    Uri uri = Uri.parse("https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX2614542.jpg");
//                    Picasso.get().load(uri).placeholder(R.drawable.placeholder).into(imageView);
////                    imageView.setImageURI(uri);
//
////                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.vegetable_main));
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        if (checkCartItems()){
            Toast.makeText(this, "U Have Items in Cart", Toast.LENGTH_LONG).show();
        }
        //getAddress
        fetchAddress();

        //set spinner items
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,R.layout.spinner_item,addressArray);
        aa.setDropDownViewResource(R.layout.spinner_item1);
        //Setting the ArrayAdapter data on the Spinner
        addressSpinner.setAdapter(aa);



        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("position",i +"");
                if (i==1){
                    fetchAddress();
                }
                else if (i == 2){
                    startActivity(new Intent(getApplicationContext(),AddAddress.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void init(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.sliding_tabs);
        imageView = findViewById(R.id.homeImageView);
        addressSpinner = findViewById(R.id.addressSpinner);
        progressBar = findViewById(R.id.addressProgressBar);
    }

    private void setupViewPager(ViewPager viewPager) {
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VegetablesFragment(), "Vegetables");
        adapter.addFragment(new GroceryFragment(), "Grocery");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.navigation_cart:
                    startActivity(new Intent(HomeActivity.this,CartFragment.class));
                    return true;

                case R.id.navigation_home:
//                    Toast.makeText(HomeActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_account:
                    startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                    Toast.makeText(HomeActivity.this, "Account Clicked", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };



    //check if items in cart
    private boolean checkCartItems() {
        boolean cartData = false;
        SqlDataStore sd = new SqlDataStore(HomeActivity.this);
        sd.open();
        String s = "SELECT * from " + QueryClass.TABLE_ITEMSCART;
        Cursor cursor = sd.getData(s);
        if (cursor.moveToFirst()) {
            cartData = true;
        }
        return cartData;
    }
    //fetch Address
    private void fetchAddress(){
        progressBar.setVisibility(View.VISIBLE);
        addressSpinner.setEnabled(false);
        addressArray.clear();
        addressArray.add("Select Address");
        addressArray.add("Refresh");
        addressArray.add("Add Address");
        getAddress();

    }

    private void getAddress(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",mySharedPreferences.getString("contactId",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Api api = RetrofitClient.getRetrofitClient().create(Api.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        final Call<Addresses> call = api.getAddress(ApiLinks.Address_Url,body);

        call.enqueue(new Callback<Addresses>() {
            @Override
            public void onResponse(Call<Addresses> call, Response<Addresses> response) {
                addressSpinner.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){

                    List<addrsses> list = response.body().getAddrsses();
                    String message = response.body().getMessage();
                    int code = response.body().getCode();

                    if (code == 0){
                        for (int i = 0;i<list.size();i++)
                        addressArray.add(list.get(i).getType());
                        HashSet<String> hashSet = new HashSet<String>();
                        hashSet.addAll(addressArray);
                        addressArray.clear();
                        addressArray.addAll(hashSet);

                    }

                }
            }

            @Override
            public void onFailure(Call<Addresses> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.e("Failed",t.getMessage().toString());
            }
        });
    }


}
