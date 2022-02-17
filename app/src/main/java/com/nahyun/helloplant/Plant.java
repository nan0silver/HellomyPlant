package com.nahyun.helloplant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plant {

    @SerializedName("myPlant")
    @Expose
    private MyPlant myPlant;

    public MyPlant getMyPlant() {
        return myPlant;
    }

    public void setMyPlant(MyPlant myPlant) {
        this.myPlant = myPlant;
    }
}
