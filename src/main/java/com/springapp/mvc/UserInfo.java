package com.springapp.mvc;

import javax.security.auth.Subject;
import java.security.Principal;

public class UserInfo implements Principal {
    private String userName;
    private String access_token;


    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
