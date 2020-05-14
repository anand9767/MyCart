package com.example.mycart.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycart.Model.CartItems;
import com.example.mycart.R;

import java.util.ArrayList;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private  Clicklistener clicklistener;
    private ArrayList<CartItems> cartItemsArrayList;
    private Context context;

    public CartItemsAdapter(ArrayList<CartItems> cartItemsArrayList, Context context,Clicklistener clicklistener) {
        this.cartItemsArrayList = cartItemsArrayList;
        this.context = context;
        this.clicklistener = clicklistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyler_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final CartItems cartItems = cartItemsArrayList.get(position);
        holder.itemName.setText(cartItems.getItemName());
        holder.itemQty.setText(cartItems.getItemQty());
        holder.itemPrice.setText(cartItems.getItemTotal());
        holder.itemPrice.setTag(holder.itemPrice.getText().toString());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clicklistener.deleteClick(cartItems.getItemId());
                cartItemsArrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),cartItemsArrayList.size());
            }
        });



    }

    @Override
    public int getItemCount() {
        return cartItemsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName,itemQty,itemPrice;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameCart);
            itemQty = itemView.findViewById(R.id.itemQtyCart);
            itemPrice = itemView.findViewById(R.id.itemTotalPriceCart);
            deleteButton = itemView.findViewById(R.id.deleteButtonCart);
        }
    }

    public interface Clicklistener{
        void deleteClick(String id);

    }
}
