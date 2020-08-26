package com.example.mycart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllItems {

    @SerializedName("Items")
    private ArrayList<Items> Items;

    private String message;
    private int code;

    public AllItems(ArrayList<com.example.mycart.Model.Items> items, String message, int code) {
        Items = items;
        this.message = message;
        this.code = code;
    }

    public List<com.example.mycart.Model.Items> getItems() {
        return Items;
    }

    public void setItems(ArrayList<com.example.mycart.Model.Items> items) {
        Items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
