package com.example.mycart.SqlDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mycart.Model.CartItems;

import java.util.ArrayList;


public class SqlDataStore  {
    //
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cart.db";
    private DBHelper dbHelper;
    private final Context context;
    private SQLiteDatabase myDatabase;
    public static SQLiteDatabase myDatabasenew;

    private class DBHelper extends SQLiteOpenHelper{


        public DBHelper( Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE  " + QueryClass.TABLE_ITEMSCART);
            onCreate(sqLiteDatabase);
        }
    }
    public SqlDataStore(Context c){
        this.context = c;
    }

    public SqlDataStore open() throws SQLException{
        dbHelper = new DBHelper(context);
        myDatabase = dbHelper.getWritableDatabase();
        myDatabasenew = dbHelper.getWritableDatabase();
        return this;
    }
    public boolean isOpen(){
        return myDatabase.isOpen();
    }

    public void close(){
        dbHelper.close();
    }

    public long insert(String TABLE, ContentValues cv){
        return myDatabase.insert(TABLE,null,cv);
    }

    public long update(String TABLE,ContentValues cv,String where){
        return myDatabase.update(TABLE, cv, where, null);
    }

    //deleting
    public long delete(String TABLE, String where) {
        return myDatabase.delete(TABLE, where, null);
    }

    //read data
    public Cursor getData(String selectQuery){
        Cursor cursor = myDatabase.rawQuery(selectQuery, null);
        return cursor;
    }

    public void droptable(String TABLE_NAME, String Query) {
        try {
            Cursor cur;
            cur = myDatabase.rawQuery("select * from  " + TABLE_NAME, null);
            if (cur != null) {
                myDatabase.execSQL("DROP TABLE " + TABLE_NAME);
            }
            myDatabase.execSQL(Query);
        } catch (SQLiteException e) {
            //our table doesn't exist, so we'll create one or take an action.
            myDatabase.execSQL(Query);
        }
    }


    public void createtable(String TABLE_NAME, String Query) {
        try {
            Cursor cur;
            cur = myDatabase.rawQuery("select * from  " + TABLE_NAME, null);
            if (cur == null) {
                myDatabase.execSQL(Query);
                System.out.println(TABLE_NAME + "Table created" + cur.toString());
            }
        } catch (SQLiteException e) {
            //our table doesn't exist, so we'll create one or take an action.
            myDatabase.execSQL(Query);
        }
    }

    //-------------raw query on table---------------------------------------
    public void rawQuery(String Query) {
        myDatabase.execSQL(Query);
    }


   //get all user data
    public ArrayList<CartItems> getAllUser() {

        ArrayList<CartItems> itemDetails = new ArrayList<>();

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + QueryClass.TABLE_ITEMSCART;

        myDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = myDatabase.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    CartItems items = new CartItems();
                    items.setItemId(cursor.getString(cursor.getColumnIndex(QueryClass.ITEM_ID)));
                    items.setItemName(cursor.getString(cursor.getColumnIndex(QueryClass.ITEM_NAME)));
                    items.setItemQty(cursor.getString(cursor.getColumnIndex(QueryClass.ITEM_QTY)));
                    items.setItemTotal(cursor.getString(cursor.getColumnIndex(QueryClass.TOTAL_PRICE)));

                    itemDetails.add(items);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Sql", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return itemDetails;

    }


}
