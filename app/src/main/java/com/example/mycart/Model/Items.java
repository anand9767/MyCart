package com.example.mycart.Model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("itemId")
    private String itemId;
    @SerializedName("itemName")
    private String itemName;
    @SerializedName("itemDesc")
    private String itemDes;
    @SerializedName("itemPrice")
    private String itemPrice;
    @SerializedName("itemPriceDesc")
    private String itemPriceDesc;
    @SerializedName("itemQty")
    private String quantity;
    @SerializedName("itemTotalPrice")
    private String totalPrice;
    @SerializedName("itemImage")
    private String image;
    @SerializedName("itemType")
    private String type;
    @SerializedName("itemSubType")
    private String subType;
//    Uri itemImage;


    public Items() {

    }

    public Items(String itemId, String itemName, String itemDes, String itemPrice, String itemPriceDesc, String quantity, String totalPrice, String image, String type, String subType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDes = itemDes;
        this.itemPrice = itemPrice;
        this.itemPriceDesc = itemPriceDesc;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.image = image;
        this.type = type;
        this.subType = subType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemPriceDesc() {
        return itemPriceDesc;
    }

    public void setItemPriceDesc(String itemPriceDesc) {
        this.itemPriceDesc = itemPriceDesc;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    //    public Items(String itemId,String itemName, String itemDes, String itemPrice, String itemPriceDesc, Uri itemImage) {
//        this.itemId = itemId;
//        this.itemName = itemName;
//        this.itemDes = itemDes;
//        this.itemPrice = itemPrice;
//        this.itemPriceDesc = itemPriceDesc;
//        this.itemImage = itemImage;
//    }
//
//    public String getItemId() {
//        return itemId;
//    }
//
//    public void setItemId(String itemId) {
//        this.itemId = itemId;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }
//
//    public String getItemDes() {
//        return itemDes;
//    }
//
//    public void setItemDes(String itemDes) {
//        this.itemDes = itemDes;
//    }
//
//    public String getItemPrice() {
//        return itemPrice;
//    }
//
//    public void setItemPrice(String itemPrice) {
//        this.itemPrice = itemPrice;
//    }
//
//    public String getItemPriceDesc() {
//        return itemPriceDesc;
//    }
//
//    public void setItemPriceDesc(String itemPriceDesc) {
//        this.itemPriceDesc = itemPriceDesc;
//    }
//
//    public Uri getItemImage() {
//        return itemImage;
//    }
//
//    public void setItemImage(Uri itemImage) {
//        this.itemImage = itemImage;
//    }
//
}
