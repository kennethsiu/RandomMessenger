package com.omagle.omagle;

/**
 * Created by Anu on 11/7/2016.
 */

public class MyUser {
    String token;
    boolean matched;
    MyUser partner;

    public MyUser()
    {
        token ="";
        matched = false;
        partner = null;
    }

    public MyUser(String t)
    {
        token = t;
        matched = false;
        partner = null;
    }

    public String getToken()
    {
        return token;
    }

    public boolean getMatched()
    {
        return matched;
    }

    public MyUser getPartner()
    {
        return partner;
    }

    public void setToken(String t)
    {
        token = t;
    }

    public void setMatched(boolean m)
    {
        matched = m;
    }

    public void setPartner(MyUser p)
    {
        partner = p;
    }
}
