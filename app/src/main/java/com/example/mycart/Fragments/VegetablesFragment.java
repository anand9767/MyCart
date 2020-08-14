package com.example.mycart.Fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VegetablesFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ArrayList<Items> itemsList = new ArrayList<>();
    private ArrayList<Items> tempItemsList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private boolean open = true;
    @BindView
    (R.id.recylerView) RecyclerView recyclerView;
    @BindView(R.id.SearchView)
    SearchView searchView;
    @BindView(R.id.filter_click)
    ImageButton imageButtonFilter;
    @BindView(R.id.filter_layout)
    RelativeLayout  filter_layout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        SqlDataStore sd = new SqlDataStore(getActivity());
        sd.open();
        tempItemsList = sd.getAllItems();
        sd.close();

        for (Items item:tempItemsList){
            String itemName = item.getType() ;
            if (itemName.contains("Vegetable")){
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

//
//    private void prepareData(){
//
//        Items items = new Items("101","Potato","Main Staple Vegetable","23","Rs/Kg",Uri.parse("https://www.isaaa.org/kc/cropbiotechupdate/files/images/1232019105233PM.jpg"));
//        itemsList.add(items);
//
//        items = new Items("102","Onion","2nd Main Staple Vegetable","33","Rs/Kg",Uri.parse("https://images.financialexpress.com/2020/02/2-94.jpg?w=1200&h=800&imflag=true"));
//        itemsList.add(items);
//
//        items = new Items("103","Tomato","The tomato is the edible","25","Rs/Kg",Uri.parse("https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/AN_images/tomatoes-1296x728-feature.jpg?w=1155&h=1528"));
//        itemsList.add(items);
//
//        items = new Items("104","Spinach","Spinach is a leafy green flowering plan","10","Rs/Bunch",Uri.parse("https://post.greatist.com/wp-content/uploads/sites/3/2020/02/270609_2200-1200x628.jpg"));
//        itemsList.add(items);
//
//        items = new Items("105","Cauliflower","Cauliflower is one of several vegetables in the species Brassica oleracea","30","Rs/Kg",Uri.parse("https://minimalistbaker.com/wp-content/uploads/2015/09/How-to-Make-Cauliflower-Rice-vegan-glutenfree.jpg"));
//        itemsList.add(items);
//
//        items = new Items("106","Carrot","The carrot is a root vegetable","40","Rs/Kg",Uri.parse("https://foodandnutrition.org/wp-content/uploads/Savor-Carrots-780x520.jpg"));
//        itemsList.add(items);
//    }


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
