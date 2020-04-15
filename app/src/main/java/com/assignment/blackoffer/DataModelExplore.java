package com.assignment.blackoffer;

public class DataModelExplore {

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

    public DataModelExplore(int circleImage, String title, String subTitle, String description) {
        this.circleImage = circleImage;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
    }


    public DataModelExplore() {
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
