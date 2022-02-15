package com.nahyun.helloplant;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitPostData {

    @SerializedName("newPlant")
    @Expose
    private NewPlant newPlant;

    public NewPlant getNewPlant() {
        return newPlant;
    }

    public void setNewPlant(NewPlant newPlant) {
        this.newPlant = newPlant;
    }
}

