package com.example.mycart.Model;

import android.net.Uri;

public class Items {

    String itemId,itemName,itemDes,itemPrice,itemPriceDesc;
    Uri itemImage;

    public Items(String itemId,String itemName, String itemDes, String itemPrice, String itemPriceDesc, Uri itemImage) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDes = itemDes;
        this.itemPrice = itemPrice;
        this.itemPriceDesc = itemPriceDesc;
        this.itemImage = itemImage;
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

    public Uri getItemImage() {
        return itemImage;
    }

    public void setItemImage(Uri itemImage) {
        this.itemImage = itemImage;
    }
}
