package com.example.laptop.trackmypocket;

/**
 * Created by laptop on 05-07-2018.
 */
public class Item {

    String ListName;
    int ListImage;

    public Item(String birdName,int birdImage)
    {
        this.ListImage=birdImage;
        this.ListName=birdName;
    }
    public String getbirdName()
    {
        return ListName;
    }
    public int getbirdImage()
    {
        return ListImage;
    }
}
