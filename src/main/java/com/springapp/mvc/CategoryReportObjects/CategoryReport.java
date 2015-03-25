package com.springapp.mvc.CategoryReportObjects;

import java.util.List;

/**
 * Created by jordanwanlass on 2/11/15.
 */
public class CategoryReport {
    private List<CategoryReportItem> CategoryReportItems;

    public List<CategoryReportItem> getCategoryReportItems() {
        return CategoryReportItems;
    }

    public void setCategoryReportItems(List<CategoryReportItem> categoryReportItems) {
        CategoryReportItems = categoryReportItems;
    }
}
