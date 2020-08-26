package com.example.mycart.Adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ArrayList<Items> itemListFiltered;
    private Context context;
    private String itemId;
    private String itemNameStr,itemDesStr,itemImageStr,itemPriceStr,itemQtyStr,itemMainPriceStr,itemMeasureStr;
    private int updatedValue = 0,itemPriceInt = 0,selected_position = RecyclerView.NO_POSITION;

    public ItemAdapter(ArrayList<Items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
        itemListFiltered = new ArrayList<>(itemsList);
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
        Uri uri = Uri.parse(items.getImage());
        Picasso.get().load(uri).placeholder(R.drawable.placeholder).fit().into(holder.itemImage);

        holder.nextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemId = items.getItemId();
                openPopUp(itemId);
                Log.e("object id",items.getItemId());
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

        TextView itemName,itemDes,itemPrice,itemPriceTag,stepperValue,totalPrice;
//        ImageButton buttonPlus,buttonMinus;
        ImageView itemImage;
        LinearLayout nextImage;
//        Button btn;
//        LinearLayout llQty,llBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            itemName = itemView.findViewById(R.id.tvItemName);
            itemDes = itemView.findViewById(R.id.tvitemDes);
            itemPrice = itemView.findViewById(R.id.tvItemPrice);
            itemPriceTag = itemView.findViewById(R.id.tvPriceDesc);
            itemImage = itemView.findViewById(R.id.mainItemimage);
            nextImage = itemView.findViewById(R.id.nextClick);


//            itemName = itemView.findViewById(R.id.itemNameTv);
//            itemDes = itemView.findViewById(R.id.itemDescTv);
//            itemPrice = itemView.findViewById(R.id.itemPriceTv);
//            itemPriceTag = itemView.findViewById(R.id.itemQtyTv);
//            stepperValue = itemView.findViewById(R.id.stepperValue);
//            totalPrice = itemView.findViewById(R.id.itemTotalTv);
//            buttonPlus = itemView.findViewById(R.id.stepperPlus);
//            buttonMinus = itemView.findViewById(R.id.stepperMinus);
//            itemImage = itemView.findViewById(R.id.itemImage);
//            llQty = itemView.findViewById(R.id.linearLayoutQty);
//            llBtn = itemView.findViewById(R.id.linearLayoutbtn);
//            btn = itemView.findViewById(R.id.button);
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

    private void deleteFromDB(String id){

        SqlDataStore sd = new SqlDataStore(context);
        sd.open();
        String where = QueryClass.ITEM_ID + "="+ id;
        sd.delete(QueryClass.TABLE_ITEMSCART,where);
        sd.close();

    }

    private void openPopUp(String itemId){

        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_indiviual, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(true);

        TextView stepperValue,calPriceTv,itemPriceTv,itemName,itemDesc,priceDesc;
        ImageView mainItemImage;
        ImageButton stepperPlus,stepperMinus;

        stepperValue = view.findViewById(R.id.stepperValue);
        calPriceTv = view.findViewById(R.id.calculatedPrice);
        stepperPlus = view.findViewById(R.id.stepperPlus);
        stepperMinus = view.findViewById(R.id.stepperMinus);
        itemPriceTv = view.findViewById(R.id.itemPriceTv);
        itemName = view.findViewById(R.id.itemName);
        itemDesc = view.findViewById(R.id.itemDescription);
        priceDesc = view.findViewById(R.id.itemQtyTv);
        mainItemImage = view.findViewById(R.id.mainImage);

        getIndiviualItems(itemId);

        //set data
        itemName.setText(itemNameStr);
        itemDesc.setText(itemDesStr);
        Uri uri = Uri.parse(itemImageStr);
        Picasso.get().load(uri).placeholder(R.drawable.placeholder).into(mainItemImage);
        calPriceTv.setText(itemPriceStr);
        priceDesc.setText(itemMeasureStr);
        stepperValue.setText(itemQtyStr);
        itemPriceTv.setText(itemMainPriceStr);



        stepperPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemPriceInt = Integer.parseInt(itemPriceTv.getText().toString());
                String currentStepperValue = stepperValue.getText().toString();
                updatedValue = Integer.parseInt(currentStepperValue)+1;
                stepperValue.setText(String.valueOf(updatedValue));
                calPriceTv.setText(String.valueOf(itemPriceInt*updatedValue));
                if (String.valueOf(updatedValue).equals("1")){
                    insertIntoDb("101",itemName.getText().toString()+"("+itemPriceTv.getText().toString()+")",String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));
                }else {
                    updateIntoDB("101",itemName.getText().toString()+"("+itemPriceTv.getText().toString()+")",String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));
                }
                updateMainTable(itemId,String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));
            }
        });
        stepperMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             itemPriceInt = Integer.parseInt(itemPriceTv.getText().toString());
             String currentStepperValue = stepperValue.getText().toString();
             if (!currentStepperValue.equals("0")){
                updatedValue = Integer.parseInt(currentStepperValue)-1;
                 stepperValue.setText(String.valueOf(updatedValue));
                 calPriceTv.setText(String.valueOf(itemPriceInt*updatedValue));
            }
            if (String.valueOf(updatedValue).equals("0")){
                deleteFromDB("101");
            }else {
                updateIntoDB("101",itemName.getText().toString()+"("+itemPriceTv.getText().toString()+")",String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));

            }
                updateMainTable(itemId,String.valueOf(updatedValue),String.valueOf(itemPriceInt*updatedValue));

            }
        });


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getIndiviualItems(String itemId){

        SqlDataStore sqlDataStore = new SqlDataStore(context);
        sqlDataStore.open();
        String s = "Select "+ QueryClass.ITEM_MAIN_NAME + ","+QueryClass.ITEM_MAIN_DESC + ","+QueryClass.ITEM_MAIN_IMAGE+","+
                QueryClass.ITEM_MAIN_QTY + "," + QueryClass.ITEM_MAIN_MEASURE +","+QueryClass.ITEM_MAIN_TOTAL_PRICE +"," +
                QueryClass.ITEM_MAIN_PRICE +" from "+QueryClass.TABLE_MAIN_ITEMS + " where " +QueryClass.ITEM_MAIN_ID + " = "+itemId;

        Cursor cursor = sqlDataStore.getData(s);
        if (cursor.moveToFirst()){

            itemNameStr = cursor.getString(0);
            itemDesStr = cursor.getString(1);
            itemImageStr = cursor.getString(2);
            itemQtyStr = cursor.getString(3);
            itemMeasureStr = cursor.getString(4);
            itemPriceStr = cursor.getString(5);
            itemMainPriceStr = cursor.getString(6);

            Log.e("values",itemId);

        }
        cursor.close();
        sqlDataStore.close();
    }
    private void updateMainTable(String id,String qty,String calPrice){

        SqlDataStore sqlDataStore = new SqlDataStore(context);
        sqlDataStore.open();

        String where = QueryClass.ITEM_MAIN_ID + " = " + id;
        ContentValues contentValuesUpdate = new ContentValues();
        contentValuesUpdate.put(QueryClass.ITEM_MAIN_QTY,qty);
        contentValuesUpdate.put(QueryClass.ITEM_MAIN_TOTAL_PRICE,calPrice);
        sqlDataStore.update(QueryClass.TABLE_MAIN_ITEMS,contentValuesUpdate,where);
        sqlDataStore.close();
    }

}
