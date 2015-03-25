package com.springapp.mvc.PageObjects;

/**
 * Created by jordanwanlass on 3/23/15.
 */
public class PageObject {
    private Integer PageSize;
    private Integer CurrentPage;
    private Integer PageCount;

    public PageObject(Integer pageSize, Integer currentPage, Integer pageCount) {
        PageSize = pageSize;
        CurrentPage = currentPage;
        PageCount = pageCount;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        CurrentPage = currentPage;
    }

    public Integer getPageCount() {
        return PageCount;
    }

    public void setPageCount(Integer pageCount) {
        PageCount = pageCount;
    }
}
