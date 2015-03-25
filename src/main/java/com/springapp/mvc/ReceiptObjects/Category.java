package com.springapp.mvc.ReceiptObjects;

/**
 * Created by jordanwanlass on 1/23/15.
 */
public class Category {
    private String Name;
    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Category(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
