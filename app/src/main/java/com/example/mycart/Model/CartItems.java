package com.example.mycart.Model;

public class CartItems {
    String itemId,itemName,itemQty,ItemTotal;

    public CartItems() {
    }

    public CartItems(String itemId, String itemName, String itemQty, String itemTotal) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQty = itemQty;
        ItemTotal = itemTotal;
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

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemTotal() {
        return ItemTotal;
    }

    public void setItemTotal(String itemTotal) {
        ItemTotal = itemTotal;
    }
}
