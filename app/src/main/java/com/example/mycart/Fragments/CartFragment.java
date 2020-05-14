package com.example.mycart.Fragments;


import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.Adapter.CartItemsAdapter;
import com.example.mycart.Adapter.ItemAdapter;
import com.example.mycart.HomeActivity;
import com.example.mycart.Model.CartItems;
import com.example.mycart.R;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends AppCompatActivity implements CartItemsAdapter.Clicklistener {

    @BindView(R.id.cart_recyler)
    RecyclerView recyclerView;
    @BindView(R.id.cartTotal)
    TextView totalPriceTextView;
    @BindView(R.id.emptyMessageLayout)
    LinearLayout emptyLL;
    @BindView(R.id.buttonsLinearlayout)
    LinearLayout buttonsLinearlayout;

    ArrayList<CartItems> cartItemsArrayList = new ArrayList<>();
    CartItemsAdapter cartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart);
        ButterKnife.bind(this);

        SqlDataStore sd = new SqlDataStore(this);
        sd.open();
        cartItemsArrayList = sd.getAllUser();
        sd.close();
        cartAdapter = new CartItemsAdapter(cartItemsArrayList,getApplicationContext(),CartFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartAdapter);

        //set total cart value
        getTotalPrice();

        if (!checkCartItems()){
            emptyLL.setVisibility(View.VISIBLE);
            buttonsLinearlayout.setVisibility(View.GONE);
        }


    }

    public void backpressed(View view) {
        onBackPressed();
    }

    //get Total price
    private void getTotalPrice(){
        ArrayList<Integer> totalPriceList = new ArrayList<>();
        SqlDataStore sd = new SqlDataStore(this);
        sd.open();
        String s = "SELECT * from " + QueryClass.TABLE_ITEMSCART;
        Cursor cursor = sd.getData(s);
        if (cursor.moveToFirst()){

            do{
              totalPriceList.add(Integer.parseInt(cursor.getString(4)));

            }while (cursor.moveToNext());

        }
        Log.e("total price",totalPriceList.toString());
        cursor.close();
        sd.close();

        double sum = 0;
        for (int i = 0;i<totalPriceList.size();i++){
            sum += totalPriceList.get(i);
        }
        totalPriceTextView.setText(String.valueOf(sum));
    }


    @Override
    public void deleteClick(String itemId) {
        deleteFromDB(itemId);
        getTotalPrice();
        if (!checkCartItems()){
            emptyLL.setVisibility(View.VISIBLE);
            buttonsLinearlayout.setVisibility(View.GONE);
        }

    }
    public void deleteFromDB(String id){

        SqlDataStore sd = new SqlDataStore(getApplicationContext());
        sd.open();
        String where = QueryClass.ITEM_ID + "="+ id;
        sd.delete(QueryClass.TABLE_ITEMSCART,where);
        sd.close();

    }

    private boolean checkCartItems() {
        boolean cartData = false;
        SqlDataStore sd = new SqlDataStore(CartFragment.this);
        sd.open();
        String s = "SELECT * from " + QueryClass.TABLE_ITEMSCART;
        Cursor cursor = sd.getData(s);
        if (cursor.moveToFirst()) {
            cartData = true;
        }
        return cartData;
    }
}
