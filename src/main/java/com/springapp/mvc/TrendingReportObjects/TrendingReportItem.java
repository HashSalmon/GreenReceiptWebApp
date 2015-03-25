package com.springapp.mvc.TrendingReportObjects;

/**
 * Created by jordanwanlass on 3/4/15.
 */
public class TrendingReportItem {

    private String Year;
    private String Month;
    private Double Total;
    private boolean IsProjected;

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public boolean isProjected() {
        return IsProjected;
    }

    public void setProjected(boolean isProjected) {
        IsProjected = isProjected;
    }
}
