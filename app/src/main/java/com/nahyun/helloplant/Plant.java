package com.nahyun.helloplant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plant {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("scientific_name")
    @Expose
    private String scientificName;
    @SerializedName("family_name")
    @Expose
    private String familyName;
    @SerializedName("korean_name")
    @Expose
    private String koreanName;
    @SerializedName("water_cycle")
    @Expose
    private String waterCycle;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("smell")
    @Expose
    private String smell;
    @SerializedName("growth_speed")
    @Expose
    private String growthSpeed;
    @SerializedName("proper_temperature")
    @Expose
    private String properTemperature;
    @SerializedName("pest")
    @Expose
    private String pest;
    @SerializedName("manage_level")
    @Expose
    private String manageLevel;
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

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public void setKoreanName(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getWaterCycle() {
        return waterCycle;
    }

    public void setWaterCycle(String waterCycle) {
        this.waterCycle = waterCycle;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getGrowthSpeed() {
        return growthSpeed;
    }

    public void setGrowthSpeed(String growthSpeed) {
        this.growthSpeed = growthSpeed;
    }

    public String getProperTemperature() {
        return properTemperature;
    }

    public void setProperTemperature(String properTemperature) {
        this.properTemperature = properTemperature;
    }

    public String getPest() {
        return pest;
    }

    public void setPest(String pest) {
        this.pest = pest;
    }

    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel;
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
