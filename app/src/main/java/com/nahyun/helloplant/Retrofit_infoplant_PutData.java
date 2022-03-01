package com.nahyun.helloplant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Retrofit_infoplant_PutData {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("infoPlant")
    @Expose
    private InfoPlant infoPlant;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InfoPlant getInfoPlant() {
        return infoPlant;
    }

    public void setInfoPlant(InfoPlant infoPlant) {
        this.infoPlant = infoPlant;
    }
}
