package com.springapp.mvc.ReceiptObjects;

public class Store {

    private Integer CompanyId;
    private Company Company;
    private Integer Id;

    public Integer getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Integer companyId) {
        CompanyId = companyId;
    }

    public Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        Company = company;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
