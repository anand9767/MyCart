package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.mycart.Fragments.Adapter.SampleFragmentPagerAdapter;
import com.example.mycart.Fragments.CurrentOrdersFragment;
import com.example.mycart.Fragments.GroceryFragment;
import com.example.mycart.Fragments.RecentOrdersFragment;
import com.example.mycart.Fragments.VegetablesFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewPager = findViewById(R.id.viewpagerProfile);
        tabLayout = findViewById(R.id.sliding_tabsProfile);

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




    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SampleFragmentPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
