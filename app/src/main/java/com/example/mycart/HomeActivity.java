package com.example.mycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mycart.Fragments.Adapter.SampleFragmentPagerAdapter;
import com.example.mycart.Fragments.CartFragment;
import com.example.mycart.Fragments.GroceryFragment;
import com.example.mycart.Fragments.VegetablesFragment;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.vegetablesicon8,
            R.drawable.groceryicons8,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //for layouts
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        if (checkCartItems()){
            Toast.makeText(this, "U Have Items in Cart", Toast.LENGTH_LONG).show();
        }

    }

    private void init(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.sliding_tabs);
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

}
