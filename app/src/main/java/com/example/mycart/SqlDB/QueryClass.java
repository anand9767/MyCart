package com.example.mycart.SqlDB;

public class QueryClass {

    public static final String TABLE_ITEMSCART = "itemsCart";
    public static final String TABLE_MAIN_ITEMS ="items";

    //itemsCartTable
    private static final String ITEM_ROW_ID = "id";
    public static final String ITEM_ID = "itemId";
    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_QTY = "itemQty";
    public static final String TOTAL_PRICE = "totalPrice";

    //items table
    private static final String ITEM_ROW_ID_MAIN = "row_id";
    public static final String ITEM_MAIN_ID = "itemMainID";
    public static final String ITEM_MAIN_NAME = "itemMainName";
    public static final String ITEM_MAIN_DESC = "itemMainDesc";
    public static final String ITEM_MAIN_PRICE = "itemMainPrice";
    public static final String ITEM_MAIN_QTY = "itemMainQty";
    public static final String ITEM_MAIN_TOTAL_PRICE= "itemMainTotalPrice";
    public static final String ITEM_MAIN_MEASURE = "itemMainMeasure";
    public static final String ITEM_MAIN_IMAGE = "itemMainImage";
    public static final String ITEM_MAIN_TYPE = "itemMainType";
    public static final String ITEM_MAIN_SUBTYPE = "itemMainSubType";



    public static String CREATE_ITEMSMAIN = "CREATE TABLE "+TABLE_MAIN_ITEMS + "( " +
           ITEM_ROW_ID_MAIN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ITEM_MAIN_ID + " TEXT ," +
            ITEM_MAIN_NAME + " TEXT ," +
            ITEM_MAIN_DESC + " TEXT ," +
            ITEM_MAIN_PRICE + " TEXT ," +
            ITEM_MAIN_QTY + " TEXT ," +
            ITEM_MAIN_TOTAL_PRICE + " TEXT ," +
            ITEM_MAIN_MEASURE + " TEXT ," +
            ITEM_MAIN_IMAGE + " TEXT ," +
            ITEM_MAIN_TYPE + " TEXT ," +
            ITEM_MAIN_SUBTYPE + " TEXT ); ";

    public static String CREATE_ITEMSCART = "CREATE TABLE "+TABLE_ITEMSCART + "( " +
            ITEM_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ITEM_ID + " TEXT ," +
            ITEM_NAME + " TEXT ," +
            ITEM_QTY + " TEXT ," +
            TOTAL_PRICE + " TEXT ); ";


}
