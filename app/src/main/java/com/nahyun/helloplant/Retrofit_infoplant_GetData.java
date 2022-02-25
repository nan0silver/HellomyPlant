package com.nahyun.helloplant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Retrofit_infoplant_GetData {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("infoPlantList")
    @Expose
    private List<InfoPlant> infoPlantList = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InfoPlant> getInfoPlantList() {
        return infoPlantList;
    }

    public void setInfoPlantList(List<InfoPlant> infoPlantList) {
        this.infoPlantList = infoPlantList;
    }
}
