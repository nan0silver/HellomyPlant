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
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("maxPage")
    @Expose
    private Integer maxPage;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }
}
