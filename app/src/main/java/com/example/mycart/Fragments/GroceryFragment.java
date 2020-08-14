package com.example.mycart.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.mycart.Adapter.ItemAdapter;
import com.example.mycart.Model.Items;
import com.example.mycart.R;
import com.example.mycart.SqlDB.SqlDataStore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroceryFragment extends Fragment implements SearchView.OnQueryTextListener {


    private ArrayList<Items> itemsList = new ArrayList<>();
    private ArrayList<Items> tempItemsList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private boolean open = true;
    @BindView(R.id.recylerViewGrocery)
    RecyclerView recyclerView;
    @BindView(R.id.SearchViewGrocery)
    SearchView searchView;
    @BindView(R.id.filter_click)
    ImageButton imageButtonFilter;
    @BindView(R.id.filter_layout)
    RelativeLayout filter_layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grocery, container, false);
        ButterKnife.bind(this,view);

        SqlDataStore sd = new SqlDataStore(getActivity());
        sd.open();
        tempItemsList = sd.getAllItems();
        sd.close();

        for (Items item:tempItemsList){
            String itemName = item.getType() ;
            if (itemName.contains("Grocery")){
                itemsList.add(item);
            }
        }

        itemAdapter = new ItemAdapter(itemsList,getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
        searchView.setOnQueryTextListener(this);

        imageButtonFilter.setOnClickListener(view1 -> {

            if (open){

                recyclerView.findViewHolderForAdapterPosition(4);
                Animation animationUtils = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_in_left);
                animationUtils.reset();
                filter_layout.clearAnimation();
                filter_layout.startAnimation(animationUtils);
                filter_layout.setVisibility(View.VISIBLE);
                open = false;

            }else {
                Animation animationUtils = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_out_right);
                animationUtils.reset();
                filter_layout.clearAnimation();
                filter_layout.startAnimation(animationUtils);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        filter_layout.setVisibility(View.GONE);
                    }
                },400);

                open = true;
            }


        });

        return view;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String userInput = s.toLowerCase();
        ArrayList<Items> newList = new ArrayList<>();
        for (Items item:itemsList){
            String itemName = item.getItemName().toLowerCase() ;
            if (itemName.contains(userInput)){
                newList.add(item);
            }
        }
        itemAdapter.updateList(newList);
        return false;
    }

}
