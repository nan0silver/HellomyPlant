package com.nahyun.helloplant;

import android.graphics.Bitmap;

public class NoticeBoardData {

    private Bitmap noticeboard_image;
    private String noticeboard_name;

    public NoticeBoardData(Bitmap image, String name) {
        this.noticeboard_image = image;
        this.noticeboard_name = name;
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
}
