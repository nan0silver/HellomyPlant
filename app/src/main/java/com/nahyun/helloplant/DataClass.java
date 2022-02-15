package com.nahyun.helloplant;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


/*  "image": "testplant3_iamge",
    "nickname": "testplant3_nick",
    "scientific_name": "testplant3_scientific",
    "water_cycle": "3",
    "fertilizer_cycle": "33",
    "_id": "6204ac0f0d616c09cc7f716e",
    "createdAt": "2022-02-10T06:09:19.239Z",
    "updatedAt": "2022-02-10T06:09:19.239Z",*/

public class DataClass {

    @SerializedName("image")
    private String image;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("scientific_name")
    private String scientific_name;

    @SerializedName("water_cycle")
    private String water_cycle;

    @SerializedName("fertilizer_cycle")
    private String fertilizer_cycle;

    @SerializedName("_id")
    private String _id;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @Override
    public String toString() {
        return "newPlant{ " +
                "image = " + image + " nickname = " + nickname + " scientific_name = "
                + scientific_name + " water_cycle = " + water_cycle + " fertilizer_cycle = "
                + fertilizer_cycle + " _id + " + _id + " createdAt = " + createdAt + " updatedAt = "
                + updatedAt + " }";
    }
}