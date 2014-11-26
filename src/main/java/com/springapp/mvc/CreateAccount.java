package com.springapp.mvc;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAccount {

    @NotNull
    @Size(min=4, max=30)
    private String username;

    @NotNull
    @Size(min=6, max=30)
    private String password;

    @NotNull
    @Size(min=1,max=20)
    private String firstName;

    @NotNull
    @Size(min=1,max=20)
    private String lastName;

    @Email
    @NotNull
    private String email;

    public CreateAccount() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
    }

    public String getName() {
        return this.username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "Create Account (username: " + this.username + ", Password: " + this.password + ")";
    }

}
