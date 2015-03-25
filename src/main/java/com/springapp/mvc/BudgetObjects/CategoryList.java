package com.springapp.mvc.BudgetObjects;

import java.util.List;

/**
 * When creating a budget, this gets a list of all the existing categories you can make a budget item for
 * Created by jordanwanlass on 1/23/15.
 */
public class CategoryList {
    private String categoriesForBudget;

    public String getCategoriesForBudget() {
        return categoriesForBudget;
    }

    public void setCategoriesForBudget(String categoriesForBudget) {
        this.categoriesForBudget = categoriesForBudget;
    }
}
