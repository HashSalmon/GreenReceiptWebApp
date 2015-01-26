package com.springapp.mvc;

/**
 * Created by jordanwanlass on 1/23/15.
 */
public class Category {
    private String Name;

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
