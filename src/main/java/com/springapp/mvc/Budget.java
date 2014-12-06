package com.springapp.mvc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class Budget {
    private List<BudgetItem> budgetItems;

    public List<BudgetItem> getBudgetItems() {
        if(this.budgetItems == null) {
            BudgetItem item1 = new BudgetItem(300.00, "Food", "33.33%", "success", 100.00, 33);
            BudgetItem item2 = new BudgetItem(150.00, "Clothing", "50.00%", "success", 75.00, 50);
            BudgetItem item3 = new BudgetItem(250.00, "Gas", "80.00%", "warning", 200.00, 80);
            BudgetItem item4 = new BudgetItem(150.00, "Dining", "100.00%", "warning", 150.00, 100);
            BudgetItem item5 = new BudgetItem(100.00, "Entertainment", "48.00%", "success", 48.00, 48);
            BudgetItem item6 = new BudgetItem(250.00, "Electronics", "200.00%", "danger", 500.00, 200);
            this.budgetItems = new ArrayList<BudgetItem>();
            this.budgetItems.add(item1);
            this.budgetItems.add(item2);
            this.budgetItems.add(item3);
            this.budgetItems.add(item4);
            this.budgetItems.add(item5);
            this.budgetItems.add(item6);
        }
        return budgetItems;
    }

    public void setBudgetItems(List<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }
}
