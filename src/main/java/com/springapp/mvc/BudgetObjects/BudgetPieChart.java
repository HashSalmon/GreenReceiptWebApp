package com.springapp.mvc.BudgetObjects;

import java.util.List;

/**
 * Created by jordanwanlass on 2/8/15.
 */
public class BudgetPieChart {
    private List<BudgetPieChartItems> pieChartItems;

    public List<BudgetPieChartItems> getPieChartItems() {
        return pieChartItems;
    }

    public void setPieChartItems(List<BudgetPieChartItems> pieChartItems) {
        this.pieChartItems = pieChartItems;
    }
}
