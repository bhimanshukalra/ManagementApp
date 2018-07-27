package com.example.bhimanshu_kalra.managementapp2;

/**
 * Created by bhimanshu_kalra on 15/4/18.
 */

public class listClass {

    private int mimageId;
    private String mTitle;

    public listClass (String title, int imageId)
    {

        mTitle=title;
        mimageId=imageId;
    }

    public int getMimageId() {return mimageId;}

    public String getmTitle() {return mTitle;}
}
