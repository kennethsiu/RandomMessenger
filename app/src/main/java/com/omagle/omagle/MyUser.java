package com.omagle.omagle;

/**
 * Created by Anu on 11/7/2016.
 */

public class MyUser {
    String token;
    boolean matched;
    String partner;
    String avatar;

    public MyUser()
    {
        token ="";
        matched = false;
        partner = "Default partner";
        avatar = "UCSD 1";
    }

    public MyUser(String t)
    {
        token = t;
        matched = false;
        partner = "Default partner";
        avatar = "UCSD 1";
    }

    public String getToken()
    {
        return token;
    }

    public boolean getMatched()
    {
        return matched;
    }

    public String getPartner()
    {
        return partner;
    }

    public String getAvatar() { return avatar; }

    public void setToken(String t)
    {
        token = t;
    }

    public void setMatched(boolean m)
    {
        matched = m;
    }

    public void setPartner(String p)
    {
        partner = p;
    }

    public void setAvatar(String a) { avatar = a; }
}
