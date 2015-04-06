package com.springapp.mvc.CreditCardObjects;

/**
 * Created by jordanwanlass on 4/1/15.
 */
public class CreditCardObject {
    private String AccountId;
    private String LastFour;
    private Integer Id;

    public CreditCardObject(String accountId, String lastFour) {
        AccountId = accountId;
        LastFour = lastFour;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getLastFour() {
        return LastFour;
    }

    public void setLastFour(String lastFour) {
        LastFour = lastFour;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
