package com.himalaya.contacts;

/**
 * Created by user on 3/24/2018.
 */

public class Contact {
    String id;
    String name;
    String phonenumber;
    String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Contact(String id, String name, String phonenumber, String email)
    {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
    }
    public Contact( String name, String phonenumber, String email)
    {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
    }
    public Contact(String name, String phonenumber)
    {
        this.name = name;
        this.phonenumber = phonenumber;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }


}
