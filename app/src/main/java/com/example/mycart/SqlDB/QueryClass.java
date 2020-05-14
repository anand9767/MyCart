package com.example.mycart.SqlDB;

public class QueryClass {

    public static final String TABLE_ITEMSCART = "itemsCart";

    //itemsCartTable
    public static final String ITEM_ROW_ID = "id";
    public static final String ITEM_ID = "itemId";
    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_QTY = "itemQty";
    public static final String TOTAL_PRICE = "totalPrice";


    public static String CREATE_ITEMSCART = "CREATE TABLE "+TABLE_ITEMSCART + "( " +
           ITEM_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
           ITEM_ID + " TEXT ," +
           ITEM_NAME + " TEXT ," +
           ITEM_QTY + " TEXT ," +
           TOTAL_PRICE + " TEXT ); ";


}
