package com.springapp.mvc.BudgetObjects;

import com.springapp.mvc.ReceiptObjects.Category;

import java.text.NumberFormat;

public class BudgetItem {
    private Category Category;
    private Double AmountUsed;
    private Double AmountAllowed;
    private Integer BudgetId;
    private Integer Id;

    public BudgetItem(double AmountAllowed, Category Category, double AmountUsed) {
        this.AmountAllowed = AmountAllowed;
        this.Category = Category;
        this.AmountUsed = AmountUsed;
    }

    public BudgetItem(Double amountAllowed, Category category, Integer budgetId) {
        this.AmountAllowed = amountAllowed;
        this.Category = category;
        this.BudgetId = budgetId;
    }

    public BudgetItem() {
        //default
    }

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category category) {
        Category = category;
    }

    public String getPercentUsedString() {
        if(this.AmountAllowed != null && this.AmountUsed != null) {
            return NumberFormat.getPercentInstance().format(this.getPercentUsed());
        }
        return null;
    }

    public Double getPercentUsed() {
        if(this.AmountAllowed != null && this.AmountUsed != null) {
            return this.AmountUsed/this.AmountAllowed;
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
            return "success";
        }
    }

    public double getAmountUsed() {
        return AmountUsed;
    }

    public void setAmountUsed(double AmountUsed) {
        this.AmountUsed = AmountUsed;
    }

    public Double getAmountAllowed() {
        return AmountAllowed;
    }

    public void setAmountAllowed(Double AmountAllowed) {
        this.AmountAllowed = AmountAllowed;
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
        return NumberFormat.getCurrencyInstance().format(this.AmountAllowed);
    }

    public String getAmountUsedCurrency() {
        return NumberFormat.getCurrencyInstance().format(this.AmountUsed);
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getBudgetId() {
        return BudgetId;
    }

    public void setBudgetId(Integer budgetId) {
        BudgetId = budgetId;
    }
}
