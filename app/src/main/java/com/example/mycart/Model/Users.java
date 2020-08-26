package com.example.mycart.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Users {

    @SerializedName("userdata")
    private List<userdata> userdata;

    private String message;
    private int code;


    public Users(List<com.example.mycart.Model.userdata> userdata, String message, int code) {
        this.userdata = userdata;
        this.message = message;
        this.code = code;
    }

    public List<com.example.mycart.Model.userdata> getUserdata() {
        return userdata;
    }

    public void setUserdata(List<com.example.mycart.Model.userdata> userdata) {
        this.userdata = userdata;
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
