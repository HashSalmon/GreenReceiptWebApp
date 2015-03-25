package com.springapp.mvc.TrendingReportObjects;

import java.util.List;

/**
 * Created by jordanwanlass on 3/4/15.
 */
public class TrendingReport {

    private List<TrendingReportItem> TrendingReportItems;

    public List<TrendingReportItem> getTrendingReportItems() {
        return TrendingReportItems;
    }

    public void setTrendingReportItems(List<TrendingReportItem> trendingReportItems) {
        TrendingReportItems = trendingReportItems;
    }
}
