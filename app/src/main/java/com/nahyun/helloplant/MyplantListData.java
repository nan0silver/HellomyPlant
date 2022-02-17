package com.nahyun.helloplant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class MyplantListData {

    private Bitmap myplant_list_image;
    private String myplant_list_name;
    private String myplant_list_water;
    private String myplant_list_fertilizer;

    public MyplantListData(Bitmap image, String name, String water, String fertilizer) {


        this.myplant_list_image = image;
        this.myplant_list_name = name;
        this.myplant_list_water = water;
        this.myplant_list_fertilizer = fertilizer;
    }

    public Bitmap getMyplant_list_image() {return myplant_list_image;}

    public void setMyplant_list_image(Bitmap image) {this.myplant_list_image = image;}

    public String getMyplant_list_name() {
        return myplant_list_name;
    }

    public void setMyplant_list_name(String name) {
        this.myplant_list_name = name;
    }

    public String getMyplant_list_water() {return myplant_list_water;}

    public void setMyplant_list_water(String water) {this.myplant_list_water = water;}

    public String getMyplant_list_fertilizer() {return myplant_list_fertilizer;}

    public void setMyplant_list_fertilizer(String fertilizer) { this.myplant_list_fertilizer = fertilizer;}


}
