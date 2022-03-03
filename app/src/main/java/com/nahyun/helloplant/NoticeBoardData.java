package com.nahyun.helloplant;

import android.graphics.Bitmap;

public class NoticeBoardData {

    private Bitmap noticeboard_image;
    private String noticeboard_name;
    private String noticeboard_family_name;
    private String noticeboard_korean_name;
    private String noticeboard_water_cycle;
    private String noticeboard_height;
    private String noticeboard_place;
    private String noticeboard_smell;
    private String noticeboard_growth_speed;
    private String noticeboard_proper_temperature;
    private String noticeboard_pest;
    private String noticeboard_manage_level;
    private String noticeboard_light;

    public NoticeBoardData(Bitmap image, String name, String family_name, String korean_name, String water_cycle,
                           String height, String place, String smell, String growth_speed,
                           String proper_temperature, String pest, String manage_level, String light) {
        this.noticeboard_image = image;
        this.noticeboard_name = name;
        this.noticeboard_family_name = family_name;
        this.noticeboard_korean_name = korean_name;
        this.noticeboard_water_cycle = water_cycle;
        this.noticeboard_height = height;
        this.noticeboard_place = place;
        this.noticeboard_smell = smell;
        this.noticeboard_growth_speed = growth_speed;
        this.noticeboard_proper_temperature = proper_temperature;
        this.noticeboard_pest = pest;
        this.noticeboard_manage_level = manage_level;
        this.noticeboard_light = light;
    }

    public Bitmap getNoticeboard_image() {
        return noticeboard_image;
    }

    public void setNoticeboard_image(Bitmap image) {
        this.noticeboard_image = image;
    }

    public String getNoticeboard_name() {
        return noticeboard_name;
    }

    public void setNoticeboard_name(String name) {
        this.noticeboard_name = name;
    }

    public String getNoticeboard_family_name() {
        return noticeboard_family_name;
    }

    public void setNoticeboard_family_name(String family_name) {
        this.noticeboard_family_name = family_name;
    }

    public String getNoticeboard_korean_name() {
        return noticeboard_korean_name;
    }

    public void setNoticeboard_korean_name(String korean_name) {
        this.noticeboard_korean_name = korean_name;
    }

    public String getNoticeboard_water_cycle() {
        return noticeboard_water_cycle;
    }

    public void setNoticeboard_water_cycle(String water_cycle) {
        this.noticeboard_water_cycle = water_cycle;
    }

    public String getNoticeboard_height() {
        return noticeboard_height;
    }

    public void setNoticeboard_height(String height) {
        this.noticeboard_height = height;
    }

    public String getNoticeboard_place() {
        return noticeboard_place;
    }

    public void setNoticeboard_place(String place) {
        this.noticeboard_place = place;
    }

    public String getNoticeboard_smell() {
        return noticeboard_smell;
    }

    public void setNoticeboard_smell(String smell) {
        this.noticeboard_smell = smell;
    }

    public String getNoticeboard_growth_speed() {
        return noticeboard_growth_speed;
    }

    public void setNoticeboard_growth_speed(String growth_speed) {
        this.noticeboard_growth_speed = growth_speed;
    }

    public String getNoticeboard_proper_temperature() {
        return noticeboard_proper_temperature;
    }

    public void setNoticeboard_proper_temperature(String proper_temperature) {
        this.noticeboard_proper_temperature = proper_temperature;
    }

    public String getNoticeboard_pest() {
        return noticeboard_pest;
    }

    public void setNoticeboard_pest(String pest) {
        this.noticeboard_pest = pest;
    }

    public String getNoticeboard_manage_level() {
        return noticeboard_manage_level;
    }

    public void setNoticeboard_manage_level(String manage_level) {
        this.noticeboard_manage_level = manage_level;
    }

    public String getNoticeboard_light() {
        return noticeboard_light;
    }

    public void setNoticeboard_light(String light) {
        this.noticeboard_light = light;
    }
}
