package com.example.mycart.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycart.Model.Items;
import com.example.mycart.R;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Items> itemsList;
    private ArrayList<Items> itemsListFull;
    Context context;
    int updatedValue = 0,itemPriceInt = 0;

    public ItemAdapter(ArrayList<Items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
        itemsListFull = new ArrayList<>(itemsList);
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder holder, int position) {

        final Items items = itemsList.get(position);
        holder.itemName.setText(items.getItemName());
        holder.itemDes.setText(items.getItemDes());
        holder.itemPrice.setText(items.getItemPrice());
        holder.itemPriceTag.setText(items.getItemPriceDesc());
        Picasso.get().load(items.getItemImage()).placeholder(R.drawable.placeholder).into(holder.itemImage);

        //stepper click handle
        holder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemPriceInt = Integer.parseInt(holder.itemPrice.getText().toString());
                String currentStepperValue = holder.stepperValue.getText().toString();
                updatedValue = Integer.parseInt(currentStepperValue)+1;
                holder.stepperValue.setText(String.valueOf(updatedValue));
                holder.totalPrice.setText(String.valueOf(itemPriceInt*updatedValue));
                if (String.valueOf(updatedValue).equals("1")){
                    insertIntoDb(items.getItemId(),items.getItemName()+"("+items.getItemPrice()+")",String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));
                }else {
                    updateIntoDB(items.getItemId(),items.getItemName()+"("+items.getItemPrice()+")",String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));
                }



            }
        });
        holder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemPriceInt = Integer.parseInt(holder.itemPrice.getText().toString());
                String currentStepperValue = holder.stepperValue.getText().toString();
                if (!currentStepperValue.equals("0")){
                    updatedValue = Integer.parseInt(currentStepperValue)-1;
                    holder.stepperValue.setText(String.valueOf(updatedValue));
                    holder.totalPrice.setText(String.valueOf(itemPriceInt*updatedValue));
                }
                if (String.valueOf(updatedValue).equals("0")){
                    deleteFromDB(items.getItemId());
                }else {
                    updateIntoDB(items.getItemId(),items.getItemName()+"("+items.getItemPrice()+")",String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public void updateList(ArrayList<Items> newList){

        itemsList = new ArrayList<>();
        itemsList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName,itemDes,itemPrice,itemPriceTag,stepperValue,totalPrice;
        ImageButton buttonPlus,buttonMinus;
        ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameTv);
            itemDes = itemView.findViewById(R.id.itemDescTv);
            itemPrice = itemView.findViewById(R.id.itemPriceTv);
            itemPriceTag = itemView.findViewById(R.id.itemQtyTv);
            stepperValue = itemView.findViewById(R.id.stepperValue);
            totalPrice = itemView.findViewById(R.id.itemTotalTv);
            buttonPlus = itemView.findViewById(R.id.stepperPlus);
            buttonMinus = itemView.findViewById(R.id.stepperMinus);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }

    public void insertIntoDb(String id,String name,String qty,String price){
        SqlDataStore sd = new SqlDataStore(context);
        sd.open();
        String s = "SELECT * from " + QueryClass.TABLE_ITEMSCART;
        Cursor cursor = sd.getData(s);
//        if(cursor.getCount() == 0){
            ContentValues cv = new ContentValues();
            cv.put(QueryClass.ITEM_ID,id);
            cv.put(QueryClass.ITEM_NAME,name);
            cv.put(QueryClass.ITEM_QTY,qty);
            cv.put(QueryClass.TOTAL_PRICE,price);
            sd.insert(QueryClass.TABLE_ITEMSCART,cv);
//        }else {
//            String where = QueryClass.ITEM_ID + "="+ id;
//            ContentValues cv_update = new ContentValues();
////            cv_update.put(QueryClass.ITEM_ID,id);
//            cv_update.put(QueryClass.ITEM_NAME,name);
//            cv_update.put(QueryClass.ITEM_QTY,qty);
//            cv_update.put(QueryClass.TOTAL_PRICE,price);
//            sd.update(QueryClass.TABLE_ITEMSCART,cv_update,where);
//
//        }
        sd.close();
    }

    private void updateIntoDB(String id,String name,String qty,String price){

            SqlDataStore sd = new SqlDataStore(context);
            sd.open();
            String where = QueryClass.ITEM_ID + "="+ id;
            ContentValues cv_update = new ContentValues();
//            cv_update.put(QueryClass.ITEM_ID,id);
            cv_update.put(QueryClass.ITEM_NAME,name);
            cv_update.put(QueryClass.ITEM_QTY,qty);
            cv_update.put(QueryClass.TOTAL_PRICE,price);
            sd.update(QueryClass.TABLE_ITEMSCART,cv_update,where);
            sd.close();
    }

    public void deleteFromDB(String id){

        SqlDataStore sd = new SqlDataStore(context);
        sd.open();
        String where = QueryClass.ITEM_ID + "="+ id;
        sd.delete(QueryClass.TABLE_ITEMSCART,where);
        sd.close();

    }





}
