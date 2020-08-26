package com.example.mycart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Addresses {

    @SerializedName("addrsses")
    private List<addrsses> addrsses;

    private String message;
    private int code;

    public Addresses(List<com.example.mycart.Model.addrsses> addrsses, String message, int code) {
        this.addrsses = addrsses;
        this.message = message;
        this.code = code;
    }

    public List<com.example.mycart.Model.addrsses> getAddrsses() {
        return addrsses;
    }

    public void setAddrsses(List<com.example.mycart.Model.addrsses> addrsses) {
        this.addrsses = addrsses;
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
