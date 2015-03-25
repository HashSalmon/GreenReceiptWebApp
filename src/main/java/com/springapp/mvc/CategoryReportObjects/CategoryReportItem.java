package com.springapp.mvc.CategoryReportObjects;

/**
 * Created by jordanwanlass on 2/11/15.
 */
public class CategoryReportItem {
    private Integer CategoryId;
    private String CategoryName;
    private Double Total;

    public Integer getId() {
        return CategoryId;
    }

    public void setId(Integer id) {
        CategoryId = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }
}
