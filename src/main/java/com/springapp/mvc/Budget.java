package com.springapp.mvc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class Budget {
    private List<BudgetItem> BudgetItems = null;
    private Integer Id;

    public List<BudgetItem> getBudgetItems() {
        return BudgetItems;
    }

    public void setBudgetItems(List<BudgetItem> budgetItems) {
        BudgetItems = budgetItems;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
