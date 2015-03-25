package com.springapp.mvc.ReceiptObjects;

public class ReceiptItem {
    private String name;
    private String ItemName;
    private String price;
    private String Price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }
}
