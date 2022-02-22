package com.nahyun.helloplant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitPutData {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("myPlant")
    @Expose
    private MyPlant myPlant;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyPlant getMyPlant() {
        return myPlant;
    }

    public void setMyPlant(MyPlant myPlant) {
        this.myPlant = myPlant;
    }
}
