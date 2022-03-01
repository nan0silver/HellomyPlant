package com.nahyun.helloplant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetrofitGetData {

    @SerializedName("myPlantList")
    @Expose
    private List<MyPlant> myPlantList = null;

    public List<MyPlant> getMyPlantList() {
        return myPlantList;
    }

    public void setMyPlantList(List<MyPlant> myPlantList) {
        this.myPlantList = myPlantList;
    }
}
