package com.example.bhimanshu_kalra.managementapp2;

/**
 * Created by bhimanshu_kalra on 16/4/18.
 */

public class DatabaseClass {

    private String name;
    private String url;

    public DatabaseClass(){}

    public DatabaseClass(String name, String url)
    {
        this.name = name;
        this.url = url;
    }

    public String getName() {   return name;    }
    public String getUrl()  {   return  url;    }

}
