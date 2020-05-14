package com.example.mycart.Fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.mycart.Adapter.ItemAdapter;
import com.example.mycart.Model.Items;
import com.example.mycart.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VegetablesFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ArrayList<Items> itemsList = new ArrayList<>();
    @BindView
    (R.id.recylerView) RecyclerView recyclerView;
    @BindView(R.id.SearchView)
    SearchView searchView;
    private ItemAdapter itemAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        itemAdapter = new ItemAdapter(itemsList,getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
        prepareData();
        searchView.setOnQueryTextListener(this);



        return view;
    }

    private void prepareData(){

        Items items = new Items("101","Potato","Main Staple Vegetable","23","Rs/Kg",Uri.parse("https://www.isaaa.org/kc/cropbiotechupdate/files/images/1232019105233PM.jpg"));
        itemsList.add(items);

        items = new Items("102","Onion","Main Staple Vegetable","33","Rs/Kg",Uri.parse("https://images.financialexpress.com/2020/02/2-94.jpg?w=1200&h=800&imflag=true"));
        itemsList.add(items);
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
