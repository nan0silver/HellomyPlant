package com.nahyun.helloplant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class MyplantListData {

    private Bitmap myplant_list_image;
    private String myplant_list_name;
    private String myplant_list_scientific_name;
    private String myplant_list_water;
    private String myplant_list_fertilizer;
    private String myplant_list_id;
    private String myplant_list_light;
    private String myplant_list_createdAt;
    private String myplant_list_updatedAt;

    public MyplantListData(Bitmap image, String name, String scientific_name, String water, String fertilizer, String id, String light, String createdAt, String updatedAt) {

        this.myplant_list_image = image;
        this.myplant_list_name = name;
        this.myplant_list_scientific_name = scientific_name;
        this.myplant_list_water = water;
        this.myplant_list_fertilizer = fertilizer;
        this.myplant_list_id = id;
        this.myplant_list_light = light;
        this.myplant_list_createdAt = createdAt;
        this.myplant_list_updatedAt = updatedAt;
    }

    public Bitmap getMyplant_list_image() {return myplant_list_image;}

    public void setMyplant_list_image(Bitmap image) {this.myplant_list_image = image;}

    public String getMyplant_list_name() {
        return myplant_list_name;
    }

    public void setMyplant_list_name(String name) {
        this.myplant_list_name = name;
    }

    public String getMyplant_list_scientific_name() {
        return myplant_list_scientific_name;
    }

    public void setMyplant_list_scientific_name(String scientific_name) {
            this.myplant_list_scientific_name = scientific_name;
    }

    public String getMyplant_list_water() {return myplant_list_water;}

    public void setMyplant_list_water(String water) {this.myplant_list_water = water;}

    public String getMyplant_list_fertilizer() {return myplant_list_fertilizer;}

    public void setMyplant_list_fertilizer(String fertilizer) { this.myplant_list_fertilizer = fertilizer;}

    public String getMyplant_list_id() {return myplant_list_id;}

    public void setMyplant_list_id(String id) { this.myplant_list_id = id;}

    public String getMyplant_list_light() {return myplant_list_light;}

    public void setMyplant_list_light(String light) { this.myplant_list_id = light;}

    public String getMyplant_list_createdAt() {return myplant_list_createdAt;}

    public void setMyplant_list_createdAt(String createdAt) { this.myplant_list_createdAt = createdAt;}

    public String getMyplant_list_updatedAt() {return myplant_list_updatedAt;}

    public void setMyplant_list_updatedAt(String updatedAt) { this.myplant_list_updatedAt = updatedAt;}


}
