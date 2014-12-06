package com.springapp.mvc;

import java.text.NumberFormat;

public class BudgetItem {
    private String category;
    private String percentUsed;
    private String status;
    private double amountUsed;
    private double amountAllowed;
    private int value;

    public BudgetItem(double amountAllowed, String category, String percentUsed, String status, double amountUsed, int value) {
        this.amountAllowed = amountAllowed;
        this.category = category;
        this.percentUsed = percentUsed;
        this.status = status;
        this.amountUsed = amountUsed;
        this.value = value;
    }

    public BudgetItem() {
        //default
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPercentUsed() {
        return percentUsed;
    }

    public void setPercentUsed(String percentUsed) {
        this.percentUsed = percentUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(double amountUsed) {
        this.amountUsed = amountUsed;
    }

    public double getAmountAllowed() {
        return amountAllowed;
    }

    public void setAmountAllowed(double amountAllowed) {
        this.amountAllowed = amountAllowed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getAmountAllowedCurrency() {
        return NumberFormat.getCurrencyInstance().format(this.amountAllowed);
    }

    public String getAmountUsedCurrency() {
        return NumberFormat.getCurrencyInstance().format(this.amountUsed);
    }
}
