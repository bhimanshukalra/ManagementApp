package com.example.bhimanshu_kalra.managementapp2;

/**
 * Created by bhimanshu_kalra on 16/4/18.
 */

public class UserDatabaseClass {

    private String user;
    private String pass;
    private String type;

    public UserDatabaseClass(){}

    public UserDatabaseClass(String user, String pass, String type)
    {
        this.user = user;
        this.pass = pass;
        this.type = type;
    }

    public String getUser() {   return user;    }
    public String getPass()  {   return  pass;    }
    public String getType() {   return type;     }
}
