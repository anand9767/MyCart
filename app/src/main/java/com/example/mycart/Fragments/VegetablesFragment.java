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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.mycart.Adapter.ItemAdapter;
import com.example.mycart.Model.Addresses;
import com.example.mycart.Model.AllItems;
import com.example.mycart.Model.Items;
import com.example.mycart.NetworkCall.Api;
import com.example.mycart.NetworkCall.ApiLinks;
import com.example.mycart.NetworkCall.RetrofitClient;
import com.example.mycart.R;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;
import com.example.mycart.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    @BindView(R.id.ProgressBar)
    ProgressBar progressBar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);


        if (CommonUtils.isOnline(getActivity())){
            getItems();
        }

        searchView.setOnQueryTextListener(this);

//        imageButtonFilter.setOnClickListener(view1 -> {
//
//            if (open){
//
//                recyclerView.findViewHolderForAdapterPosition(4);
//                Animation animationUtils = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_in_left);
//                animationUtils.reset();
//                filter_layout.clearAnimation();
//                filter_layout.startAnimation(animationUtils);
//                filter_layout.setVisibility(View.VISIBLE);
//                open = false;
//
//            }else {
//                Animation animationUtils = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_out_right);
//                animationUtils.reset();
//                filter_layout.clearAnimation();
//                filter_layout.startAnimation(animationUtils);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        filter_layout.setVisibility(View.GONE);
//                    }
//                },400);
//
//                open = true;
//            }
//
//
//        });




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

    private void getItems(){
        Api api = RetrofitClient.getRetrofitClient().create(Api.class);
        final Call<AllItems> call = api.getAllItems(ApiLinks.items_Url);

        call.enqueue(new Callback<AllItems>() {
            @Override
            public void onResponse(Call<AllItems> call, Response<AllItems> response) {
                if (response.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
//                    ArrayList<Items> items = new
                    itemsList = (ArrayList<Items>) response.body().getItems();
                    

                    int code = response.body().getCode();

                    if (code == 0){

                        itemAdapter = new ItemAdapter(itemsList,getActivity());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(itemAdapter);
                        CommonUtils.filterAccording(itemsList,itemAdapter,"vegetable");


                        insetIntoDB(itemsList);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllItems> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.e("Failed",t.getMessage().toString());
            }
        });
    }

    private void insetIntoDB(ArrayList<Items> items){

        try {
            SqlDataStore sqlDataStore = new SqlDataStore(getActivity());
            sqlDataStore.open();
            sqlDataStore.droptable(QueryClass.TABLE_MAIN_ITEMS,QueryClass.CREATE_ITEMSMAIN);
            for (int  i = 0;i<items.size();i++){

                String itemId = items.get(i).getItemId();
                String itemName = items.get(i).getItemName();
                String itemDesc = items.get(i).getItemDes();
                String itemPrice = items.get(i).getItemPrice();
                String itemTotalPrice = items.get(i).getTotalPrice();
                String qty = items.get(i).getQuantity();
                String priceDesc = items.get(i).getItemPriceDesc();
                String itemType =  items.get(i).getType();
                String subType = items.get(i).getSubType();
                String image = items.get(i).getImage();

                ContentValues cv = new ContentValues();
                cv.put(QueryClass.ITEM_MAIN_ID,itemId);
                cv.put(QueryClass.ITEM_MAIN_NAME,itemName);
                cv.put(QueryClass.ITEM_MAIN_DESC,itemDesc);
                cv.put(QueryClass.ITEM_MAIN_PRICE,itemPrice);
                cv.put(QueryClass.ITEM_MAIN_QTY,qty);
                cv.put(QueryClass.ITEM_MAIN_TOTAL_PRICE,itemTotalPrice);
                cv.put(QueryClass.ITEM_MAIN_MEASURE,priceDesc);
                cv.put(QueryClass.ITEM_MAIN_IMAGE,image);
                cv.put(QueryClass.ITEM_MAIN_TYPE,itemType);
                cv.put(QueryClass.ITEM_MAIN_SUBTYPE,subType);

                sqlDataStore.insert(QueryClass.TABLE_MAIN_ITEMS,cv);
                cv.clear();

            }
            sqlDataStore.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Log.e("Items","Successfully inserted");

        }

    }

}


//convert array in string
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("1234");
//        arrayList.add("4424");
//        arrayList.add("242");
//        String s = android.text.TextUtils.join(",",arrayList);
