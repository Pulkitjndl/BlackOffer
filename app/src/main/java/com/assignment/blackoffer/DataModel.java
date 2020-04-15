package com.assignment.blackoffer;

import java.util.List;
import java.util.Locale;

public class DataModel {
    private int circleImage;
    private String title;
    private String subTitle;
    private String description;

    public int getCircleImage() {
        return circleImage;
    }

    public void setCircleImage(int circleImage) {
        this.circleImage = circleImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataModel(int circleImage, String title, String subTitle, String description) {
        this.circleImage = circleImage;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
    }

    public DataModel(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public DataModel() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

}