package com.springapp.mvc;

import javax.validation.constraints.NotNull;
import java.text.NumberFormat;

public class BudgetItem {
    private String category;
    private Double amountUsed;

    @NotNull
    private Double amountAllowed;

    public BudgetItem(double amountAllowed, String category, double amountUsed) {
        this.amountAllowed = amountAllowed;
        this.category = category;
        this.amountUsed = amountUsed;
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

    public String getPercentUsedString() {
        if(this.amountAllowed != null && this.amountUsed != null) {
            return NumberFormat.getPercentInstance().format(this.getPercentUsed());
        }
        return null;
    }

    public Double getPercentUsed() {
        if(this.amountAllowed != null && this.amountUsed != null) {
            return this.amountUsed/this.amountAllowed;
        }
        return null;
    }

    public String getStatus() {
        Integer percentUsed = this.getValue();
        if(percentUsed != null) {
            if(percentUsed >= 80 && percentUsed <= 100) {
                return "warning";
            } else if(percentUsed < 80) {
                return "success";
            } else {
                return "danger";
            }
        } else {
            return null;
        }
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

    public Integer getValue() {
        if(this.getPercentUsed() != null) {
            Double percentUsed = this.getPercentUsed() * 100;
            return percentUsed.intValue();
        } else {
            return null;
        }
    }

    public String getAmountAllowedCurrency() {
        return NumberFormat.getCurrencyInstance().format(this.amountAllowed);
    }

    public String getAmountUsedCurrency() {
        return NumberFormat.getCurrencyInstance().format(this.amountUsed);
    }
}
