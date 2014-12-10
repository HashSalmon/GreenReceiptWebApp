package com.springapp.mvc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("session")
public class Receipt {
    private String store;
    private List<ReceiptItem> items;
    private String total;
    private String returnDate;

    public Receipt() {
        //Default
    }

    public Receipt(String store, List<ReceiptItem> items, String total, String returnDate) {
        this.store = store;
        this.items = items;
        this.total = total;
        this.returnDate = returnDate;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItem> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
