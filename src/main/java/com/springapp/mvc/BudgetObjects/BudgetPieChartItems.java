package com.springapp.mvc.BudgetObjects;

/**
 * Created by jordanwanlass on 2/8/15.
 */
public class BudgetPieChartItems {
    private String category;
    private Double value;
    private String color;

    public BudgetPieChartItems(String category, Double value, String color) {
        this.category = category;
        this.value = value;
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
