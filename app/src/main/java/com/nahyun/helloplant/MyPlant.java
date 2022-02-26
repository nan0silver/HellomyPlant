package com.nahyun.helloplant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPlant {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("scientific_name")
    @Expose
    private String scientificName;
    @SerializedName("water_cycle")
    @Expose
    private String waterCycle;
    @SerializedName("fertilizer_cycle")
    @Expose
    private String fertilizerCycle;
    @SerializedName("light")
    @Expose
    private String light;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getWaterCycle() {
        return waterCycle;
    }

    public void setWaterCycle(String waterCycle) {
        this.waterCycle = waterCycle;
    }

    public String getFertilizerCycle() {
        return fertilizerCycle;
    }

    public void setFertilizerCycle(String fertilizerCycle) {
        this.fertilizerCycle = fertilizerCycle;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
