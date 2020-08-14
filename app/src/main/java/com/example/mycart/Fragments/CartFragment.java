package com.example.mycart.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.Adapter.CartItemsAdapter;
import com.example.mycart.Adapter.ItemAdapter;
import com.example.mycart.FinalActivity;
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
    double sum = 0;

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

    //checkoutClick
    public void checkOut(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = LayoutInflater.from(this).inflate(R.layout.checkout_layout,null);
        builder.setView(view1);
        builder.setCancelable(false);

        TextView orderTotal = view1.findViewById(R.id.orderTotalTv);
        ImageView cancelImage = view1.findViewById(R.id.cancel);
        Button button = view1.findViewById(R.id.placeOrder);
        int a = (int) sum;
        button.setOnClickListener(view22 -> {
            Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
            startActivity(intent);
            finish();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        });
        orderTotal.setText(String.valueOf(a));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        cancelImage.setOnClickListener(view2 -> {
            alertDialog.dismiss();
        });
    }
}
